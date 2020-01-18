package com.taptica.demo.loggers;

import com.taptica.demo.exceptions.LoggingException;
import com.taptica.demo.model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;

import static com.taptica.demo.LogSystemApplication.DATE_FORMAT;
import static com.taptica.demo.LogSystemApplication.LOG_FILE;

@Component
public class LogWriter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void receiveMessage(Report message) throws LoggingException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true));
        ) {
            String date = Instant.now().atZone(ZoneOffset.UTC).format(DATE_FORMAT);
            String str = String.format("Id = {%-24s} Name = {%-100s} time = {%-25s} %n", message.getId(), message.getName(), date);
            writer.write(str);
        } catch (IOException e) {
            logger.error("Error with logging: " + e.getMessage());
            throw new LoggingException("Error with logging: " + e.getMessage());
        }
    }


}
