package com.tradeReportingEngine;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ImportEventsService {

    private static final String BUYER_PARTY_EXPRESSION = "//buyerPartyReference/@href";
    private static final String SELLER_PARTY_EXPRESSION = "//sellerPartyReference/@href";
    private static final String PREMIUM_AMOUNT_EXPRESSION = "//paymentAmount/amount";
    private static final String PREMIUM_CURRENCY_EXPRESSION = "//paymentAmount/currency";

    @Autowired
    EventRepository eventRepository;

    @SneakyThrows
    @EventListener
    public void setUp(ContextRefreshedEvent event) {
        // Get folder path from resources
        ClassLoader classLoader = ImportEventsService.class.getClassLoader();
        Path folderPath = Paths.get(Objects.requireNonNull(classLoader.getResource("eventXML")).toURI());

        // Iterate through each file in the folder
        Stream<Path> paths = Files.list(folderPath);
            paths.filter(Files::isRegularFile) // Only select files, not directories
                    .forEach(this::processXMLFile); // Process each file
    }

    @SneakyThrows
    private void processXMLFile(Path filePath) {
        // Load XML file
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(filePath.toFile());

        // Create XPath instance
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();

        Event event = Event.builder()
                .buyerParty(get(xpath,doc,BUYER_PARTY_EXPRESSION))
                .sellerParty(get(xpath,doc,SELLER_PARTY_EXPRESSION))
                .premium_amount(Double.parseDouble(get(xpath,doc,PREMIUM_AMOUNT_EXPRESSION)))
                .premium_currency(get(xpath,doc,PREMIUM_CURRENCY_EXPRESSION))
                .build();

        eventRepository.save(event);

    }

    @SneakyThrows
    private String get(XPath xpath, Document doc, String expression){
        // Define an XPath expression
        XPathExpression compiledExpression = xpath.compile(expression);

        // Evaluate the expression
        return (String) compiledExpression.evaluate(doc, XPathConstants.STRING);
    }
}
