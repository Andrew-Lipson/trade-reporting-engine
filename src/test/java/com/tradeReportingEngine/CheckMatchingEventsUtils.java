package com.tradeReportingEngine;

import com.tradeReportingEngine.Event.Event;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;
import java.util.Map;

public class CheckMatchingEventsUtils {

    public static boolean twoEventListsHaveSameValues(List<Event> expected, List<Event> actual){
        if (expected.size() != actual.size()) return false;
        boolean result =  actual.stream()
            .allMatch(actualEvent -> expected.stream()
                .anyMatch(expectedEvent -> twoEventsHaveSameValue(expectedEvent,actualEvent))
            );
        if (!result) return false;
        return expected.stream()
            .allMatch(actualEvent -> actual.stream()
                .anyMatch(expectedEvent -> twoEventsHaveSameValue(expectedEvent,actualEvent))
            );
    }

    @SneakyThrows
    public static boolean twoEventsHaveSameValue(Event expected, Event actual){
        Map<String, String> expectedProperties = BeanUtils.describe(expected);
        Map<String, String> actualProperties = BeanUtils.describe(actual);
        return expectedProperties.equals(actualProperties);
    }
}
