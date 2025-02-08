package com.microservice.boilerplate.job.processSchedule;

import com.microservice.boilerplate.dto.AgendamentoDTO;
import com.microservice.boilerplate.service.AgendamentoService;
import java.util.List;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ScheduleItemReader implements ItemReader<AgendamentoDTO> {

    @Autowired
    private AgendamentoService service;

    private List<AgendamentoDTO> agendamentos;
    private int nextIndex;

    @Override
    public AgendamentoDTO read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (agendamentos == null) {
            agendamentos = loadNotProcessingSchedules();
            nextIndex = 0;
        }

        if (nextIndex < agendamentos.size()) {
            return agendamentos.get(nextIndex++);
        } else {
            return null; // Indica que não há mais itens
        }
    }

    private List<AgendamentoDTO> loadNotProcessingSchedules() {
        return service.listarAgendamentos();
    }
}
