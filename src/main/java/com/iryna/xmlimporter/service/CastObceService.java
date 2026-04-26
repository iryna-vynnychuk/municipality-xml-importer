package com.iryna.xmlimporter.service;

import com.iryna.xmlimporter.dto.CastObceDto;
import com.iryna.xmlimporter.exceptions.InvalidObceDataException;
import com.iryna.xmlimporter.model.CastObce;
import com.iryna.xmlimporter.model.Obec;
import com.iryna.xmlimporter.repository.CastObceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CastObceService {

    private CastObceRepository castObceRepository;

    public void createAndSaveCastObce(String nazevCastiObce, String kodCastiObce, Obec obec) {
        CastObce castObce = new CastObce();
        castObce.setKodCastiObce(Integer.valueOf(kodCastiObce));
        castObce.setNazevCastiObce(nazevCastiObce);
        castObce.setObec(obec);
        castObceRepository.save(castObce);
    }

    public List<CastObceDto> getAllCastiObci() {
        return castObceRepository.findAll()
                .stream()
                .map(castObce -> new CastObceDto(
                        castObce.getNazevCastiObce(),
                        castObce.getKodCastiObce(),
                        castObce.getObec().getKodObce()
                ))
                .toList();
    }

    public void deleteCastObceByKod(String kod) throws InvalidObceDataException {
        Integer parsedKod = Integer.valueOf(kod);

        CastObce castObce = castObceRepository.findByKodCastiObce(parsedKod)
                .orElseThrow(() -> new InvalidObceDataException(
                        "CastObce not found with kod=" + parsedKod));

        castObceRepository.delete(castObce);
    }
}
