package com.tradeReportingEngine.Import;

import com.tradeReportingEngine.Event.Event;
import com.tradeReportingEngine.Event.EventRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.tradeReportingEngine.Import.XPathConstants.*;

@Service
public class ImportEventsService {

    @Autowired
    EventRepository eventRepository;

    @SneakyThrows
    public void importEvents() {

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
                .premiumAmount(Double.parseDouble(get(xpath,doc,PREMIUM_AMOUNT_EXPRESSION)))
                .premiumCurrency(get(xpath,doc,PREMIUM_CURRENCY_EXPRESSION))
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
