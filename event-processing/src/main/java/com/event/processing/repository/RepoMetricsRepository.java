package com.event.processing.repository;

import com.event.processing.common.entity.RepoMetrics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepoMetricsRepository extends MongoRepository<RepoMetrics, String> {

    long count();
}
