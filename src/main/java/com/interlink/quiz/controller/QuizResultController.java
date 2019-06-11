package com.interlink.quiz.controller;

import com.interlink.quiz.auth.security.JwtTokenProvider;
import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.TopicResult;
import com.interlink.quiz.object.dto.AccountDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizResultController {

    private final QuizResultService quizResultService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public QuizResultController(QuizResultService quizResultService,
                                JwtTokenProvider jwtTokenProvider) {
        this.quizResultService = quizResultService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/result")
    public QuizResult getQuizResult(@RequestBody QuizSessionDto quizSessionDto,
                                    @RequestHeader(value = "auth-token", required = false) String token) {

        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }

        return quizResultService.getQuizResult(quizSessionDto, userId);
    }

    @GetMapping("/account")
    public AccountDto getTestHistory(@RequestHeader(value = "auth-token", required = false) String token) {

        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }

        return quizResultService.getHistoryOfQuizzesByUser(userId);
    }

    @GetMapping("/account/statistic")
    public List<TopicResult> getStatisticByTopics(@RequestHeader(value = "auth-token", required = false) String token) {

        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }

        return quizResultService.getTopicsResultByUser(userId);
    }
}