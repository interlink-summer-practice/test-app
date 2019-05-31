package com.interlink.quiz.service;

import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.repository.QuizAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizAnswerService {

    private final QuizAnswerRepository quizAnswerRepository;

    @Autowired
    public QuizAnswerService(QuizAnswerRepository quizAnswerRepository) {
        this.quizAnswerRepository = quizAnswerRepository;
    }

    public void saveQuizAnswer(QuizAnswer quizAnswer) {
        quizAnswerRepository.saveQuizAnswer(quizAnswer);
    }
}
