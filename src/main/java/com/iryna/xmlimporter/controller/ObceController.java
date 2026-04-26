package com.iryna.xmlimporter.controller;


import com.iryna.xmlimporter.dto.CastObceDto;
import com.iryna.xmlimporter.dto.ObecDto;
import com.iryna.xmlimporter.exceptions.InvalidObceDataException;
import com.iryna.xmlimporter.service.CastObceService;
import com.iryna.xmlimporter.service.Downloader;
import com.iryna.xmlimporter.service.ObecService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/obce")
@AllArgsConstructor
public class ObceController {

    private ObecService obecService;
    private CastObceService castObceService;
    private Downloader downloader;

    @PostMapping("/start")
    public ResponseEntity startDownloading(@RequestParam String urlString ) {
        try {
            downloader.downloadZipFile(urlString);
            return ResponseEntity.ok("Parsed success");
        } catch (IOException | XMLStreamException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InvalidObceDataException e) {
            return ResponseEntity.badRequest().body("Invalid Obce Data Inside XML File");
        }
    }

    @GetMapping("/allObci")
    public ResponseEntity<List<ObecDto>> getAllObci(){
        return ResponseEntity.ok(obecService.getAllObci());
    }

    @DeleteMapping("/deleteObceById")
    public ResponseEntity deleteAllObce(@RequestParam String kod) {
        try {
            obecService.deleteObceById(kod);
            return ResponseEntity.ok("Deleted success");
        } catch (InvalidObceDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/allCastObce")
    public ResponseEntity<List<CastObceDto>> getAllCastObce(){
        return ResponseEntity.ok(castObceService.getAllCastiObci());
    }

    @DeleteMapping("/deleteCastObceByKod")
    public ResponseEntity<String> deleteCastObceByKod(@RequestParam String kod) {
        try {
            castObceService.deleteCastObceByKod(kod);
            return ResponseEntity.ok("Deleted success");
        } catch (InvalidObceDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}