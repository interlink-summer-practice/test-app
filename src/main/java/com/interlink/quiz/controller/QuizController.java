package com.interlink.quiz.controller;

import com.interlink.quiz.auth.security.JwtTokenProvider;
import com.interlink.quiz.csv.CsvParserService;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.object.dto.QuizAnswerDto;
import com.interlink.quiz.object.dto.QuizDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.service.QuestionService;
import com.interlink.quiz.service.QuizAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;

@RestController
public class QuizController {

    private final QuestionService questionService;
    private final QuizAnswerService quizAnswerService;
    private final CsvParserService csvParserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public QuizController(QuestionService questionService,
                          QuizAnswerService quizAnswerService,
                          CsvParserService csvParserService,
                          JwtTokenProvider jwtTokenProvider) {
        this.questionService = questionService;
        this.quizAnswerService = quizAnswerService;
        this.csvParserService = csvParserService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/questions")
    public QuizDto getQuestions(@RequestBody Topic[] topics,
                                @RequestHeader("auth-token") String token,
                                HttpSession httpSession) {
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);
        return questionService.getQuestions(topics, userId, httpSession);
    }

    @PostMapping("/quiz-answer")
    public ResponseEntity saveQuizAnswer(@RequestBody QuizAnswerDto quizAnswerDto) {
        quizAnswerService.saveQuizAnswer(quizAnswerDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/import")
    public void saveQuizFromCsvFile(@RequestBody File file) {
        csvParserService.parseCsvFileToDataBase(file);
    }

    @PutMapping("/quiz-answer")
    public void updateQuizSessionAndAnswers(@RequestBody QuizSessionDto quizSessionDto,
                                            @RequestHeader("auth-token") String token) {
        questionService.updateResultsOfPassedQuiz(quizSessionDto, token);
    }
}
