package com.interlink.quiz.controller;

import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.User;
import com.interlink.quiz.object.dto.QuizResultDto;
import com.interlink.quiz.service.QuizResultService;
import com.interlink.quiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizResultController {

    private final QuizResultService quizResultService;
    private final UserService userService;

    @Autowired
    public QuizResultController(QuizResultService quizResultService,
                                UserService userService) {
        this.quizResultService = quizResultService;
        this.userService = userService;
    }

    @GetMapping("/result")
    public List<QuizResult> getQuizResult(@RequestBody QuizSession quizSession) {
        return quizResultService.getQuizResult(quizSession);
    }

    @GetMapping("/result/history")
    public List<QuizResultDto> getTestHistory(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return quizResultService.getHistoryOfQuizzesByUser(user);
    }
}