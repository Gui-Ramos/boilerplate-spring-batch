package com.microservice.boilerplate.job.processSchedule;

import com.microservice.boilerplate.dto.AgendamentoDTO;
import com.microservice.boilerplate.service.AgendamentoService;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleItemWriter implements ItemWriter<AgendamentoDTO> {

    @Autowired
    private AgendamentoService service;

    @Override
    public void write(Chunk<? extends AgendamentoDTO> chunk) throws Exception {
        chunk.forEach(item -> {
            service.saveAgendamento(item);
        });
    }
}
