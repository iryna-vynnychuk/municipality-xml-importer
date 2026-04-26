package com.iryna.xmlimporter.service;

import com.iryna.xmlimporter.exceptions.InvalidObceDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@AllArgsConstructor
public class Downloader {

    private Parser parser;

    public void downloadZipFile(String urlString) throws IOException, XMLStreamException, InvalidObceDataException {

        if (urlString.isEmpty()) {
            urlString = "https://raw.githubusercontent.com/iryna-vynnychuk/xml-data/main/data.xml.zip";
        }

            URL url = new URL(urlString);
            try (InputStream inputStream = url.openStream();
                 ZipInputStream zipStream = new ZipInputStream(inputStream)) {

                ZipEntry entry;

                while ((entry = zipStream.getNextEntry()) != null) {
                    if (!entry.isDirectory() && entry.getName().endsWith(".xml")) {
                        parser.parse(zipStream);
                        break;
                    }
                }
            }
    }

}
