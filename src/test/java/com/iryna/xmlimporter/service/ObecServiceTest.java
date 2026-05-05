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

    public static final int KOD_OBCE = 573060;
    @Mock
    private ObecRepository obecRepository;

    @InjectMocks
    private ObecService obecService;

    @Test
    void shouldThrowExceptionWhenObecWithSameKodAlreadyExists() {
        Obec existingObec = new Obec();
        existingObec.setKodObce(KOD_OBCE);
        existingObec.setNazevObce("Kopidlno");

        when(obecRepository.findByKodObce(KOD_OBCE))
                .thenReturn(Optional.of(existingObec));

        assertThrows(
                InvalidObceDataException.class,() -> obecService.createAndSaveObce("Kopidlno", "573060")
        );

        verify(obecRepository, times(1)).findByKodObce(KOD_OBCE);
        verify(obecRepository, never()).save(any(Obec.class));
    }

    @Test
    void shouldSaveObecWhenKodDoesNotExist() throws Exception {
        when(obecRepository.findByKodObce(KOD_OBCE))
                .thenReturn(Optional.empty());

        obecService.createAndSaveObce("Kopidlno", "573060");

        verify(obecRepository, times(1)).findByKodObce(KOD_OBCE);
        verify(obecRepository, times(1)).save(any(Obec.class));
    }
}