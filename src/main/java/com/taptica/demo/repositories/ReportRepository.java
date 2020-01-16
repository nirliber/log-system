package com.taptica.demo.repositories;

import com.taptica.demo.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<Report, String> {

    List<Report> findByHour(Integer hour);

}
