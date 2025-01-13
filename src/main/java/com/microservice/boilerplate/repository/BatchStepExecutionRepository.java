package com.microservice.boilerplate.repository;

import com.microservice.boilerplate.model.BatchStepExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchStepExecutionRepository extends JpaRepository<BatchStepExecution, Long> {}
