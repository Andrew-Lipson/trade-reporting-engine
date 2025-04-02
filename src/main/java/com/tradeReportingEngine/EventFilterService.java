package com.tradeReportingEngine;

import com.tradeReportingEngine.Event.Event;
import com.tradeReportingEngine.Event.EventRepository;
import com.tradeReportingEngine.Event.EventSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class EventFilterService {

    @Autowired
    EventRepository eventRepository;

    public List<Event> getFilteredEvents(){

        Specification<Event> spec = Specification.where(
                Specification.where(EventSpecifications.filterSellerParty("EMU_BANK")).and(EventSpecifications.filterPremiumCurrency("AUD"))
        ).or(Specification.where(EventSpecifications.filterSellerParty("BISON_BANK")).and(EventSpecifications.filterPremiumCurrency("USD")));

        List<Event> events = eventRepository.findAll(spec);

        return events.stream()
                .filter(event -> !anagramChecker(event.getBuyerParty(), event.getSellerParty()))
                .toList();
    }

    private boolean anagramChecker(String str1, String str2){
        if (str1.length() != str2.length()) return false;

        HashMap<Character,Integer> freqMap = new HashMap<>();

        for(char c : str1.toCharArray()){
            int occurrence =  freqMap.getOrDefault(c,0);
            freqMap.put(c,occurrence+1);
        }

        for(char c : str1.toCharArray()){
            int occurrence =  freqMap.getOrDefault(c,0);
            if (occurrence<0) return false;
            freqMap.put(c,occurrence-1);
        }

        return true;
    }
}
