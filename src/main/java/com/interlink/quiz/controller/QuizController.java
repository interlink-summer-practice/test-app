package com.interlink.quiz.controller;

import com.interlink.quiz.auth.security.JwtTokenProvider;
import com.interlink.quiz.csv.CsvParserService;
import com.interlink.quiz.object.CuratorQuiz;
import com.interlink.quiz.object.dto.FilteredQuizDto;
import com.interlink.quiz.object.dto.QuizAnswerDto;
import com.interlink.quiz.object.dto.QuizDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.service.QuestionService;
import com.interlink.quiz.service.QuizAnswerService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

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
    public QuizDto getQuestions(@RequestBody FilteredQuizDto filteredQuizDto,
                                @RequestHeader(value = "auth-token", required = false) String token,
                                HttpSession httpSession) {

        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }

        return questionService.getQuestions(
                filteredQuizDto.getTopics(),
                userId,
                httpSession,
                filteredQuizDto.getDifficulty()
        );
    }

    @PostMapping("/quiz/{url}")
    public ResponseEntity<?> getQuestionsToGroup(@RequestBody @PathVariable String url,
                                                 @RequestHeader(value = "auth-token", required = false) String token,
                                                 HttpSession httpSession) throws IOException {

        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        byte[] decode = Base64.getDecoder().decode(url);
        String test = new String(decode);
        CuratorQuiz curatorQuiz = new ObjectMapper().readValue(test, CuratorQuiz.class);

        return new ResponseEntity<>(questionService.getQuestionsToGroup(curatorQuiz, userId, httpSession), HttpStatus.OK);
    }

    @PostMapping("/quiz-answer")
    public ResponseEntity saveQuizAnswer(@RequestBody QuizAnswerDto quizAnswerDto) {
        quizAnswerService.saveQuizAnswer(quizAnswerDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/import")
    public ResponseEntity saveQuizFromCsvFile(@RequestBody byte[] file) {
        csvParserService.parseCsvFileToDataBase(file);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/quiz-answer")
    public void updateQuizSessionAndAnswers(@RequestBody QuizSessionDto quizSessionDto,
                                            @RequestHeader(value = "auth-token", required = false) String token) {
        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }

        questionService.updateResultsOfPassedQuiz(quizSessionDto, userId);
    }
}
