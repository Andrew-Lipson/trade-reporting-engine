package com.tradeReportingEngine;

import com.tradeReportingEngine.Event.EventService;
import com.tradeReportingEngine.Import.ImportEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private EventService eventFilterService;

    @Autowired
    private ImportEventsService importEventsService;

    @GetMapping("/filteredEvents")
    public Map<String, Object> getFilteredEvents(){
        var filteredEvents = eventFilterService.getFilteredEvents();
        Map<String,Object> response = new HashMap<>();
        response.put("events",filteredEvents);
        return response;
    }

    @GetMapping("/loadEvents")
    public void loadFilteredEvents(){
        importEventsService.importEvents();
    }
}
