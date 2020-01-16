package com.taptica.demo.repositories;

import com.taptica.demo.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, String> {


}
