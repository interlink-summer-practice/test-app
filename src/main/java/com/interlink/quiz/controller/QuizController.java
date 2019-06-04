package com.interlink.quiz.controller;

import com.interlink.quiz.csv.CsvParserService;
import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.object.dto.QuestionsDto;
import com.interlink.quiz.service.QuestionService;
import com.interlink.quiz.service.QuizAnswerService;
import com.interlink.quiz.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;

@RestController
public class QuizController {

    private final QuestionService questionService;
    private final QuizAnswerService quizAnswerService;
    private final CsvParserService csvParserService;

    @Autowired
    public QuizController(QuestionService questionService,
                          QuizAnswerService quizAnswerService,
                          CsvParserService csvParserService) {
        this.questionService = questionService;
        this.quizAnswerService = quizAnswerService;
        this.csvParserService = csvParserService;
    }

    @GetMapping("/questions")
    public QuestionsDto getQuestions(@RequestParam Topic[] topics,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     HttpSession httpSession) {

        return questionService.getQuestions(topics, userDetails, httpSession);
    }

    @PostMapping("/quiz-answer")
    public String saveQuizAnswer(@RequestParam QuizAnswer quizAnswer) {
        quizAnswerService.saveQuizAnswer(quizAnswer);

        return "OK";
    }

    @GetMapping("/import")
    public void saveQuizFromCsvFile(@RequestBody File file) {
        csvParserService.parseCsvFileToDataBase(file);
    }
}
