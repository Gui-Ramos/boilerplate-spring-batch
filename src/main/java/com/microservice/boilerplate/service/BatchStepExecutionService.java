package com.microservice.boilerplate.service;

import com.microservice.boilerplate.model.BatchStepExecution;
import com.microservice.boilerplate.repository.BatchStepExecutionRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchStepExecutionService {

    @Autowired
    BatchStepExecutionRepository repository;

    public Optional<BatchStepExecution> findById(Long id) {
        return repository.findById(id);
    }
}
