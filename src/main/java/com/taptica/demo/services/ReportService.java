package com.taptica.demo.services;

import com.taptica.demo.exceptions.ReportServiceException;
import com.taptica.demo.model.Report;

import java.util.List;

public interface ReportService {

    Report create(Report report) throws ReportServiceException;

    Report getById(String id) throws ReportServiceException;

    void delete(String id) throws ReportServiceException;

    Report update(String id, Report report) throws ReportServiceException;

    List<Report> getAll() throws ReportServiceException;

    List<Report> getByHour(Integer hour) throws ReportServiceException;
}
