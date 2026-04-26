package com.iryna.xmlimporter.service;

import com.iryna.xmlimporter.exceptions.InvalidObceDataException;
import com.iryna.xmlimporter.model.Obec;
import com.iryna.xmlimporter.repository.ObecRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObecServiceTest {

    @Mock
    private ObecRepository obecRepository;

    @InjectMocks
    private ObecService obecService;

    @Test
    void shouldThrowExceptionWhenObecWithSameKodAlreadyExists() {
        Obec existingObec = new Obec();
        existingObec.setKodObce(573060);
        existingObec.setNazevObce("Kopidlno");

        when(obecRepository.findByKodObce(573060))
                .thenReturn(Optional.of(existingObec));

        assertThrows(
                InvalidObceDataException.class,() -> obecService.createAndSaveObce("Kopidlno", "573060")
        );

        verify(obecRepository, times(1)).findByKodObce(573060);
        verify(obecRepository, never()).save(any(Obec.class));
    }

    @Test
    void shouldSaveObecWhenKodDoesNotExist() throws Exception {
        when(obecRepository.findByKodObce(573060))
                .thenReturn(Optional.empty());

        obecService.createAndSaveObce("Kopidlno", "573060");

        verify(obecRepository, times(1)).findByKodObce(573060);
        verify(obecRepository, times(1)).save(any(Obec.class));
    }
}