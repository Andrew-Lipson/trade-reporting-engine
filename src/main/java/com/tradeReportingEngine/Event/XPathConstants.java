package com.tradeReportingEngine.Event;

public class XPathConstants {

    private XPathConstants() {
    }

    public static final String BUYER_PARTY_EXPRESSION = "//buyerPartyReference/@href";
    public static final String SELLER_PARTY_EXPRESSION = "//sellerPartyReference/@href";
    public static final String PREMIUM_AMOUNT_EXPRESSION = "//paymentAmount/amount";
    public static final String PREMIUM_CURRENCY_EXPRESSION = "//paymentAmount/currency";

}
