package com.microservice.boilerplate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "batch_step_execution")
@Getter
@Setter
public class BatchStepExecution {

    @Id
    @Column(name = "step_execution_id")
    private Long id;

    private Long jobExecutionId;
    private String stepName;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Long readCount;
    private Long writeCount;
    private Long readSkipCount;
    private Long writeSkipCount;
    private Long processSkipCount;
}
