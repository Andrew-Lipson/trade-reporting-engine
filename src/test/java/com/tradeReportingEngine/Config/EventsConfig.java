package com.tradeReportingEngine.Config;

import com.tradeReportingEngine.Event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class EventsConfig {

    @Bean
    public List<Event> fullEventList(){
        return List.of(
                new Event(null,"KANMU_EB","EMU_BANK",300.00,"AUD"),
                new Event(null,"EMU_BANK","BISON_BANK",500.00,"USD"),
                new Event(null,"EMU_BANK","BISON_BANK",600.00,"USD"),
                new Event(null,"EMU_BANK","BISON_BANK",150.00,"AUD"),
                new Event(null,"EMU_BANK","EMU_BANK",300.00,"AUD"),
                new Event(null,"RIGHT_BANK","EMU_BANK",100.00,"HKD"),
                new Event(null,"LEFT_BANK","EMU_BANK",200.00,"AUD"),
                new Event(null,"LEFT_BANK","EMU_BANK",100.00,"AUD")
        );
    }

    @Bean
    public List<Event> filteredEventList(@Autowired List<Event> fullEventList){
        ArrayList<Event> eventArrayList = new ArrayList<>(fullEventList);
        eventArrayList.remove(5);
        eventArrayList.remove(4);
        eventArrayList.remove(3);
        eventArrayList.remove(0);
        return eventArrayList;
    }



}
