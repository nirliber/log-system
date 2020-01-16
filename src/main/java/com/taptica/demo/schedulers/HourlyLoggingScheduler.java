package com.taptica.demo.schedulers;


import com.taptica.demo.LogSystemApplication;
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

    //Each minute
    @Scheduled(cron = "0 * * * * *")
    public void runner() throws Exception {
        run();
    }

    @Override
    public void run(String... args) throws Exception {
        int hour = Instant.now().atZone(ZoneOffset.UTC).getHour();
        logger.info("The hour is now {}", hour);
        List<Report> newReports = reportService.getByHour(hour);
        logger.info("There are {} new reports", newReports.size());
        for (Report report : newReports) {
            logger.info("Sending message: {} to queue", report);
            rabbitTemplate.convertAndSend(LogSystemApplication.topicExchangeName, "foo.bar.baz", report);
        }
    }
}
