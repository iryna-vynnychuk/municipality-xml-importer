package com.iryna.xmlimporter.service;

import com.iryna.xmlimporter.model.Obec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParserTest {

    @Mock
    private ObecService obecService;

    @Mock
    private CastObceService castObceService;

    @InjectMocks
    private Parser parser;

    @Test
    void shouldParseRealXmlFile() throws Exception {
        InputStream input = getClass().getClassLoader().getResourceAsStream("testfile.xml");
        assertNotNull(input, "testfile.xml not found in src/test/resources");

        Obec mockObec = new Obec();
        mockObec.setKodObce(573060);

        when(obecService.getObecById(anyString())).thenReturn(mockObec);

        parser.parse(input);

        verify(obecService, times(1))
                .createAndSaveObce(anyString(), anyString());

        verify(castObceService, atLeast(5))
                .createAndSaveCastObce(anyString(), anyString(), eq(mockObec));
    }
}
