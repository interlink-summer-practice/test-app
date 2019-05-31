package com.interlink.quiz.controller;

import com.interlink.quiz.csv.CsvParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class QuizController {

    private final CsvParserService csvParserService;

    @Autowired
    public QuizController(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    @GetMapping("/import")
    public void saveQuizFromCsvFile() {
        File file = new File("C:\\test.csv");
        csvParserService.parseCsvFileToDataBase(file);
    }
}
