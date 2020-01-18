package com.taptica.demo.services.impl;

import com.taptica.demo.exceptions.ReportServiceException;
import com.taptica.demo.exceptions.ReportValidationException;
import com.taptica.demo.exceptions.ResourceNotFoundException;
import com.taptica.demo.model.Report;
import com.taptica.demo.repositories.ReportRepository;
import com.taptica.demo.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report create(Report report) throws ReportServiceException {
        Report ret;
        report.setId(null);
        try {
            ret = reportRepository.save(report);
        } catch (ConstraintViolationException e) {
            logger.error("Failed to create report: " + e.getMessage());
            throw new ReportValidationException(e.getMessage());
        }
        report.setSentToQueue(false);
        logger.info("Report Created {}", ret);
        return ret;
    }

    @Override
    public Report getById(String id) throws ReportServiceException {
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null) {
            logger.error("Report with id {} not found", id);
            throw new ResourceNotFoundException(String.format("Report with id {%s} not found", id));
        }

        logger.info("Report with id: {} was found", id);
        return report;
    }

    @Override
    public void delete(String id) throws ReportServiceException {
        boolean isExists = reportRepository.existsById(id);
        if (!isExists) {
            logger.error("Report with id {} not found", id);
            throw new ResourceNotFoundException(String.format("Report with id {%s} not found", id));
        }
        reportRepository.deleteById(id);
        logger.info("Report with id: {} was deleted", id);
    }

    @Override
    public Report update(String id, Report report) throws ReportServiceException {
        Report current = reportRepository.findById(id).orElse(null);
        if (current == null) {
            logger.error("Report with id {} not found", id);
            throw new ResourceNotFoundException(String.format("Report with id {%s} not found", id));
        }
        report.setId(id);
        Report updated;
        try {
            updated = reportRepository.save(report);
        } catch (ConstraintViolationException e) {
            logger.error("Failed to update report: " + e.getMessage());
            throw new ReportValidationException(e.getMessage());
        }
        logger.info("Report with id: {} was updated: {}", id, report);
        return updated;
    }

    @Override
    public List<Report> getAll() throws ReportServiceException {
        List<Report> result = reportRepository.findAll();
        logger.info("Found {} Reports", result.size());
        return result;
    }

    @Override
    public List<Report> getByHour(Integer hour) throws ReportServiceException {
        List<Report> result = reportRepository.findByHour(hour);
        logger.info("Found {} Reports for hour: {}", result.size(), hour);
        return result;
    }

}
