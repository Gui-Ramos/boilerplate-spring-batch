package com.microservice.boilerplate.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ParallelItemCountListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(ParallelItemCountListener.class);

    private long itemCount = 0;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        itemCount = 0; // Reinicia a contagem antes de cada step
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
            itemCount = stepExecution.getReadCount(); // Atualiza com os itens lidos
            log.info("Itens processados no step: {}", itemCount);
        } else {
            log.info("Step não completado com sucesso: {}", stepExecution.getStatus());
        }
        return null;
    }

    public long getItemCount() {
        return itemCount;
    }
}
