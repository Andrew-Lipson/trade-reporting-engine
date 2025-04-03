package com.tradeReportingEngine;

import com.tradeReportingEngine.Config.EventsConfig;
import com.tradeReportingEngine.Event.Event;
import com.tradeReportingEngine.Event.EventService;
import com.tradeReportingEngine.Import.ImportEventsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = Controller.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
@Import(EventsConfig.class)
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ImportEventsService importEventsService;

    @MockBean
    EventService eventService;

    @Autowired
    List<Event> filteredEventList;

    @Test
    @SneakyThrows
    public void testGetFilteredEvents(){
        when(eventService.getFilteredEvents()).thenReturn(filteredEventList);

        mockMvc.perform(MockMvcRequestBuilders.get("/filteredEvents"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.events",hasSize(filteredEventList.size())));
    }

    @Test
    @SneakyThrows
    public void testGetLoadEvents(){
        mockMvc.perform(MockMvcRequestBuilders.get("/loadEvents"))
                .andExpect(status().isOk());

        verify(importEventsService,times(1)).importEvents();
    }
}
