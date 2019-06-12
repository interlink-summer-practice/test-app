package com.interlink.quiz.controller;

import com.interlink.quiz.auth.security.JwtTokenProvider;
import com.interlink.quiz.object.dto.QuizAnswerDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.service.QuestionService;
import com.interlink.quiz.service.QuizAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizAnswerController {

    private final QuizAnswerService quizAnswerService;
    private final QuestionService questionService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public QuizAnswerController(QuizAnswerService quizAnswerService,
                                QuestionService questionService,
                                JwtTokenProvider jwtTokenProvider) {
        this.quizAnswerService = quizAnswerService;
        this.questionService = questionService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/quiz-answer")
    public ResponseEntity saveQuizAnswer(@RequestBody QuizAnswerDto quizAnswerDto) {
        quizAnswerService.saveQuizAnswer(quizAnswerDto);

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
