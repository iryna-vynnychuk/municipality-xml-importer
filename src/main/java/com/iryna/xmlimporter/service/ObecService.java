package com.iryna.xmlimporter.service;

import com.iryna.xmlimporter.dto.ObecDto;
import com.iryna.xmlimporter.exceptions.InvalidObceDataException;
import com.iryna.xmlimporter.model.Obec;
import com.iryna.xmlimporter.repository.ObecRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObecService {

    private ObecRepository obecRepository;

    public void createAndSaveObce(String nazev, String kodObce) throws InvalidObceDataException {
        Integer parsedKodObce = Integer.valueOf(kodObce);
        if (obecRepository.findByKodObce(parsedKodObce).isPresent()) {
            throw new InvalidObceDataException(
                    "Obec with kodObce=" + parsedKodObce + " already exists");
        }
        Obec obec = new Obec();
        obec.setKodObce(Integer.valueOf(kodObce));
        obec.setNazevObce(nazev);
        obecRepository.save(obec);
    }

    public Obec getObecById(String kod) throws InvalidObceDataException {
        Integer parsedKodObce = Integer.valueOf(kod);
        return obecRepository.findByKodObce(parsedKodObce)
                .orElseThrow(() -> new InvalidObceDataException(
                        "Obec with kodObce=" + parsedKodObce + " not found"));
    }

    public List<ObecDto> getAllObci() {
        return obecRepository.findAll()
                .stream()
                .map(obec -> new ObecDto(
                        obec.getNazevObce(),
                        obec.getKodObce()
                ))
                .toList();
    }

    public void deleteObceById(String kod) throws InvalidObceDataException {
        Obec obecById = getObecById(kod);
        obecRepository.delete(obecById);
    }
}
