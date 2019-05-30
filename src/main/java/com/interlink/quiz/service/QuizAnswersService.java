package com.interlink.quiz.service;

import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.repository.QuizAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizAnswersService {

    private final QuizAnswersRepository quizAnswersRepository;

    @Autowired
    public QuizAnswersService(QuizAnswersRepository quizAnswersRepository) {
        this.quizAnswersRepository = quizAnswersRepository;
    }

    public void saveQuizAnswer(QuizAnswer quizAnswer) {
        quizAnswersRepository.saveQuizAnswer(quizAnswer);
    }
}
