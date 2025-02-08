package com.microservice.boilerplate.job.processSchedule;

import com.microservice.boilerplate.config.JobCompletionNotificationListener;
import com.microservice.boilerplate.config.ParallelItemCountListener;
import com.microservice.boilerplate.dto.AgendamentoDTO;
import com.microservice.boilerplate.service.AgendamentoService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class ProcessScheduleConfig {

    @Autowired
    AgendamentoService service;

    @Bean
    public Job processSchedule(
            JobRepository jobRepository, Step scheduleStep, JobCompletionNotificationListener listener) {

        return new JobBuilder("processAgendamentosJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(scheduleStep)
                .build();
    }

    @Bean
    public ItemReader<AgendamentoDTO> reader() {
        return new ScheduleItemReader();
    }

    @Bean
    public ItemWriter<AgendamentoDTO> writer() {
        return new ScheduleItemWriter();
    }

    @Bean
    ItemProcessor<AgendamentoDTO, AgendamentoDTO> processor() {
        return new ScheduleItemProcessor();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("agendamento");
    }

    @Bean
    public Step scheduleStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ParallelItemCountListener parallelItemCountListener) {

        return new StepBuilder("scheduleStep", jobRepository)
                .<AgendamentoDTO, AgendamentoDTO>chunk(20, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .listener(parallelItemCountListener)
                .taskExecutor(taskExecutor())
                .allowStartIfComplete(true)
                .build();
    }

    private int determineChunkSizeDynamically() {
        int totalItems = (int) service.count();
        int processors = Runtime.getRuntime().availableProcessors();

        // Defina o tamanho do chunk dinamicamente
        int chunkSize = Math.max(1, totalItems / (processors * 2));
        return Math.min(chunkSize, 100); // Limite o tamanho máximo
    }
}
