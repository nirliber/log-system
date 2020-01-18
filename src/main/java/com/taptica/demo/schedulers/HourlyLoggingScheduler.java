package com.taptica.demo.schedulers;


import com.taptica.demo.exceptions.ReportServiceException;
import com.taptica.demo.loggers.LogWriter;
import com.taptica.demo.model.Report;
import com.taptica.demo.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import static com.taptica.demo.LogSystemApplication.*;

@Component
public class HourlyLoggingScheduler implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReportService reportService;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public HourlyLoggingScheduler(ReportService reportService, RabbitTemplate rabbitTemplate, LogWriter logWriter) {
        this.reportService = reportService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(cron = CRON_SCHEDULER_STRING)
    public void runner() throws Exception {
        run();
    }

    @Override
    public void run(String... args) throws Exception {
        int hour = Instant.now().atZone(ZoneOffset.UTC).getHour();
        logger.info("The hour is now {}", hour);
        updateReportsQueue(hour);
    }

    public void updateReportsQueue(int hour) throws ReportServiceException {
        List<Report> currentHourReports = reportService.getByHour(hour);
        logger.info("There are {} reports for this hour", currentHourReports.size());
        List<Report> reportsToSend = currentHourReports.stream()
                .filter(report -> !report.isSentToQueue())
                .collect(Collectors.toList());
        logger.info("There are {} new reports to send", reportsToSend.size());
        for (Report report : reportsToSend) {
            logger.info("Sending message: {} to queue", report);
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, ROUTING_KEY, report);
            report.setSentToQueue(true);
            reportService.update(report.getId(), report);
        }
    }
}
