package com.taptica.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ReportValidationException extends ReportServiceException {
    public ReportValidationException() {
    }

    public ReportValidationException(String message) {
        super(message);
    }

    public ReportValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportValidationException(Throwable cause) {
        super(cause);
    }
}
