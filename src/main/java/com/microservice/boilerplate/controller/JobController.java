package com.microservice.boilerplate.controller;

import com.microservice.boilerplate.model.BatchStepExecution;
import com.microservice.boilerplate.service.BatchStepExecutionService;
import java.util.Optional;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamento")
public class JobController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job processScheduleJob;

    @Autowired
    BatchStepExecutionService service;

    @GetMapping("/jobLauncher.html")
    public void handle() throws Exception {
        jobLauncher.run(processScheduleJob, new JobParameters());
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<BatchStepExecution> findById(@PathVariable("id") Long id) {
        Optional<BatchStepExecution> optBatchStepExec = service.findById(id);

        return optBatchStepExec.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound()
                .build());
    }
}
