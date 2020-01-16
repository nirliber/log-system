package com.taptica.demo.services.impl;

import com.taptica.demo.exceptions.ReportServiceException;
import com.taptica.demo.exceptions.ResourceNotFoundException;
import com.taptica.demo.model.Report;
import com.taptica.demo.repositories.ReportRepository;
import com.taptica.demo.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Report ret = reportRepository.save(report);
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
        Report updated = reportRepository.save(report);
        return updated;
    }

    @Override
    public List<Report> getAll() {
        List<Report> result = reportRepository.findAll();
        return result;
    }
}
