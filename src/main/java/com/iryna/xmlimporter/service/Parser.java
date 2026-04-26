package com.iryna.xmlimporter.service;

import com.iryna.xmlimporter.exceptions.InvalidObceDataException;
import com.iryna.xmlimporter.model.Obec;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

@Service
@AllArgsConstructor
public class Parser {

    public static final String KOD = "Kod";
    public static final String OBEC = "Obec";
    public static final String CAST_OBCE = "CastObce";
    public static final String CASTI_OBCE = "CastiObci";
    public static final String NAZEV = "Nazev";


    private ObecService obecService;
    private CastObceService castObceService;

    public void parse (InputStream input) throws XMLStreamException, InvalidObceDataException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(input);

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isEndElement()
                    && CASTI_OBCE.equals(event.asEndElement().getName().getLocalPart())) {
                break;
            }

            if (!event.isStartElement()) {
                continue;
            }
            StartElement start = event.asStartElement();
            String elementName = start.getName().getLocalPart();

            switch (elementName) {
                case OBEC, CAST_OBCE:
                    parseAndCreate(reader, elementName);
                    break;
                default:
                    break;
            }
        }
    }

    private void parseAndCreate(XMLEventReader reader, String elementName) throws XMLStreamException, InvalidObceDataException {
        String kod = null;
        String nazev = null;
        String kodObce = null;

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isEndElement()
                    && elementName.equals(event.asEndElement().getName().getLocalPart())) {
                break;
            }

            if (!event.isStartElement()) {
                continue;
            }

            String name = event.asStartElement().getName().getLocalPart();

            switch (name) {
                case KOD:
                    if (kod == null) {
                        kod = readElementText(reader);
                    }
                    break;
                case NAZEV:
                    nazev = readElementText(reader);
                    break;
                case OBEC:
                    kodObce = readNestedObecKod(reader);
                    break;
                default:
                    break;
            }
        }

        if (OBEC.equals(elementName)) {
            obecService.createAndSaveObce(nazev,kod);
        } else {
            Obec obec = obecService.getObecById(kodObce);
            castObceService.createAndSaveCastObce(nazev, kod, obec);
        }
    }

    private static String readElementText(XMLEventReader reader) throws XMLStreamException {
        XMLEvent event = reader.nextEvent();
        return event.isCharacters() ? event.asCharacters().getData().trim() : null;
    }

    private static String readNestedObecKod(XMLEventReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isEndElement()
                    && OBEC.equals(event.asEndElement().getName().getLocalPart())) {
                return null;
            }

            if (!event.isStartElement()) {
                continue;
            }

            String name = event.asStartElement().getName().getLocalPart();

            if (KOD.equals(name)) {
                return readElementText(reader);
            }
        }

        return null;
    }

}
