package com.interlink.quiz.controller;

import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.TopicResult;
import com.interlink.quiz.object.User;
import com.interlink.quiz.object.dto.QuizResultDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.service.QuizResultService;
import com.interlink.quiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizResultController {

    private final QuizResultService quizResultService;

    @Autowired
    public QuizResultController(QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }

    @PostMapping("/result")
    public QuizResult getQuizResult(@RequestBody QuizSessionDto quizSessionDto,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        return quizResultService.getQuizResult(quizSessionDto, userDetails);
    }

    @GetMapping("/result/history")
    public List<QuizResultDto> getTestHistory(@AuthenticationPrincipal UserDetails userDetails) {

        return quizResultService.getHistoryOfQuizzesByUser(userDetails);
    }
}