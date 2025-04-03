package com.tradeReportingEngine.Event;

import com.tradeReportingEngine.CheckMatchingEventsUtils;
import com.tradeReportingEngine.Config.EventsConfig;
import com.tradeReportingEngine.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = {Application.class, EventsConfig.class})
public class EventServiceTest {

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    List<Event> fullEventList;

    @Autowired
    List<Event> filteredEventList;

    @Test
    public void testGetFilteredEvents(){
        eventRepository.saveAll(fullEventList);
        List<Event> actualEventList = eventService.getFilteredEvents();
        Assertions.assertTrue(CheckMatchingEventsUtils.twoEventListsHaveSameValues(actualEventList,filteredEventList));
    }

}
