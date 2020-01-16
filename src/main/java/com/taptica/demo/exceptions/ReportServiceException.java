package com.taptica.demo.exceptions;

public class ReportServiceException extends Exception{

    public ReportServiceException() {
    }

    public ReportServiceException(String message) {
        super(message);
    }

    public ReportServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportServiceException(Throwable cause) {
        super(cause);
    }
}
