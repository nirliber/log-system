package com.taptica.demo.exceptions;

public class LoggingException extends ReportServiceException {

    public LoggingException() {
    }

    public LoggingException(String message) {
        super(message);
    }

    public LoggingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoggingException(Throwable cause) {
        super(cause);
    }
}
