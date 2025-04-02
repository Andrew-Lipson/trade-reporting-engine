package com.tradeReportingEngine.Event;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class EventSpecifications {

    public static Specification<Event> filterSellerParty(String sellerParty){
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
            cb.equal(root.get("sellerParty"),sellerParty);
    }

    public static Specification<Event> filterPremiumCurrency(String premiumCurrency){
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("premiumCurrency"),premiumCurrency);
    }
}
