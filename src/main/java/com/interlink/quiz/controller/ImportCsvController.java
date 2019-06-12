package com.interlink.quiz.controller;

import com.interlink.quiz.csv.CsvParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportCsvController {

    private final CsvParserService csvParserService;

    @Autowired
    public ImportCsvController(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    @PostMapping("/import")
    public ResponseEntity saveQuizFromCsvFile(@RequestBody byte[] file) {
        csvParserService.parseCsvFileToDataBase(file);

        return new ResponseEntity(HttpStatus.OK);
    }
}
