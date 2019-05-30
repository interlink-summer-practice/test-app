package com.interlink.quiz.controller;

import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.service.QuizAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizResultController {

    private final QuizAnswerService quizAnswerService;

    @Autowired
    public QuizResultController(QuizAnswerService quizAnswerService) {
        this.quizAnswerService = quizAnswerService;
    }


    @GetMapping("/result")
    public List<QuizResult> getQuizResult(@RequestBody QuizSession quizSession) {
        return quizAnswerService.getQuizAnswersBySession(quizSession);
    }
}
