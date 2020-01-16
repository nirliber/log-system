package com.taptica.demo.loggers;

import com.taptica.demo.model.Report;
import org.springframework.stereotype.Component;

@Component
public class LogWriter {


    public void receiveMessage(Report message) {
        System.out.println("Received <" + message + ">");
    }


}
