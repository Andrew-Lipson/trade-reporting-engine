package com.tradeReportingEngine.Event;

import com.tradeReportingEngine.CheckMatchingEventsUtils;
import com.tradeReportingEngine.Config.EventsConfig;
import com.tradeReportingEngine.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@DataJpaTest
@Import(EventsConfig.class)
@ContextConfiguration(classes = Application.class)
public class EventSpecificationsTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private List<Event> fullEventList;

    @ParameterizedTest
    @ValueSource(strings = {"EMU_BANK", "BISON_BANK","RIGHT_BANK","LEFT_BANK","KANMU_EB",""})
    public void testFilterSellerParty(String sellerParty){
       eventRepository.saveAll(fullEventList);
       List<Event> expectedEvents = fullEventList.stream().filter(event -> event.getSellerParty().equals(sellerParty)).toList();
       Specification<Event> spec = Specification.where(EventSpecifications.filterSellerParty(sellerParty));

        List<Event> actualEvents = eventRepository.findAll(spec);
        Assertions.assertTrue(CheckMatchingEventsUtils.twoEventListsHaveSameValues(expectedEvents,actualEvents));
    }

    @ParameterizedTest
    @ValueSource(strings = {"AUD","USD","HKD","YEN",""})
    public void testFilterPremiumCurrency(String premiumCurrency){
        eventRepository.saveAll(fullEventList);
        List<Event> expectedEvents = fullEventList.stream().filter(event -> event.getPremiumCurrency().equals(premiumCurrency)).toList();
        Specification<Event> spec = Specification.where(EventSpecifications.filterPremiumCurrency(premiumCurrency));

        List<Event> actualEvents = eventRepository.findAll(spec);
        Assertions.assertTrue(CheckMatchingEventsUtils.twoEventListsHaveSameValues(expectedEvents,actualEvents));
    }

}
