package com.interlink.quiz.controller;

import com.interlink.quiz.auth.security.JwtTokenProvider;
import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.dto.QuizResultDto;
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
                                    @RequestHeader("auth-token") String token) {
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);
        return quizResultService.getQuizResult(quizSessionDto, userId);
    }

    @GetMapping("/account")
    public List<QuizResultDto> getTestHistory(@RequestHeader("auth-token") String token) {
        return quizResultService.getHistoryOfQuizzesByUser(jwtTokenProvider.getUserIdFromJWT(token));
    }
}