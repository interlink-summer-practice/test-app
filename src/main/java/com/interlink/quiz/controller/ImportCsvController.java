package com.interlink.quiz.controller;

import com.interlink.quiz.csv.CsvParserService;
import com.interlink.quiz.object.User;
import com.interlink.quiz.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportCsvController {

    private final CsvParserService csvParserService;
    private final UserJpaRepository userJpaRepository;

    @Autowired
    public ImportCsvController(CsvParserService csvParserService,
                               UserJpaRepository userJpaRepository) {
        this.csvParserService = csvParserService;
        this.userJpaRepository = userJpaRepository;
    }

    @PostMapping("/import")
    public ResponseEntity saveQuizFromCsvFile(@RequestBody byte[] file) {
        csvParserService.parseCsvFileToDataBase(file);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/test")
    public User test() {
        int id = 1;
        return userJpaRepository.findById(id);
    }
}
