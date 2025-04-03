package com.tradeReportingEngine.Import;

import com.tradeReportingEngine.CheckMatchingEventsUtils;
import com.tradeReportingEngine.Config.EventsConfig;
import com.tradeReportingEngine.Application;
import com.tradeReportingEngine.Event.Event;
import com.tradeReportingEngine.Event.EventRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {Application.class, EventsConfig.class})
public class ImportEventServiceTest {

    @MockBean
    EventRepository eventRepository;

    @Autowired
    ImportEventsService importEventsService;

    @Autowired
    private List<Event> fullEventList;

    @Test
    void testImportingAllEvents() {
        importEventsService.importEvents();
        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository, times(8)).save(captor.capture());

        List<Event> actualEvents = captor.getAllValues();

        Assertions.assertTrue(CheckMatchingEventsUtils.twoEventListsHaveSameValues(actualEvents,fullEventList));
    }

}
