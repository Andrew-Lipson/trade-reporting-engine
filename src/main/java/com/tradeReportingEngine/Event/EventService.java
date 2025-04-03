package com.tradeReportingEngine.Event;

import com.tradeReportingEngine.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<Event> getFilteredEvents(){

        Specification<Event> spec = Specification.where(
                Specification.where(EventSpecifications.filterSellerParty("EMU_BANK")).and(EventSpecifications.filterPremiumCurrency("AUD"))
        ).or(Specification.where(EventSpecifications.filterSellerParty("BISON_BANK")).and(EventSpecifications.filterPremiumCurrency("USD")));

        List<Event> events = eventRepository.findAll(spec);

        return events.stream()
                .filter(event -> !Utils.isAnAnagram(event.getBuyerParty(), event.getSellerParty()))
                .toList();
    }
}
