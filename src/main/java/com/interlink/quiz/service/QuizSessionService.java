package com.interlink.quiz.service;

import com.interlink.quiz.repository.QuizSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizSessionService {

    private final QuizSessionRepository quizSessionRepository;

    @Autowired
    public QuizSessionService(QuizSessionRepository quizSessionRepository) {
        this.quizSessionRepository = quizSessionRepository;
    }
}
