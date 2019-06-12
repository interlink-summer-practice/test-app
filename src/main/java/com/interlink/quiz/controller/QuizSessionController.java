package com.interlink.quiz.controller;

import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizSessionController {

    private final QuestionService questionService;

    @Autowired
    public QuizSessionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/quiz-session")
    public void addQuestionsInQuizSession(@RequestBody QuizSessionDto quizSessionDto) {
        questionService.addQuestionsInQuizSession(quizSessionDto);
    }
}
