package com.microservice.boilerplate.job.processSchedule;

import com.microservice.boilerplate.dto.AgendamentoDTO;
import java.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;

public class ScheduleItemProcessor implements ItemProcessor<AgendamentoDTO, AgendamentoDTO> {

    private static final Logger log = LoggerFactory.getLogger(ScheduleItemProcessor.class);

    @Override
    public AgendamentoDTO process(@NonNull AgendamentoDTO item) throws Exception {
        item.setHoraFim(LocalTime.now());
        return item;
    }
}
