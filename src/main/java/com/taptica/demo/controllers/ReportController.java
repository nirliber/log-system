package com.taptica.demo.controllers;

import com.taptica.demo.exceptions.ReportServiceException;
import com.taptica.demo.model.Report;
import com.taptica.demo.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Report create(@RequestBody Report input) throws ReportServiceException {
        logger.info("Create Report API with {}", input);
        Report report = reportService.create(input);
        return report;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Report getById(@PathVariable String id) throws ReportServiceException {
        logger.info("Get Report API with Id: {}", id);
        Report report = reportService.getById(id);
        return report;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Report> getAll() throws ReportServiceException {
        logger.info("Get all Reports API");
        List<Report> reports = reportService.getAll();
        return reports;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Report update(@PathVariable String id, @RequestBody Report input) throws ReportServiceException {
        logger.info("Update Report API with Id and input: {} {}", id, input);
        Report ret = reportService.update(id, input);
        return ret;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) throws ReportServiceException {
        logger.info("Delete Report API with Id: {}", id);
        reportService.delete(id);
    }

}
