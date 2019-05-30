package com.interlink.quiz.service;

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

    public List<QuizResult> getQuizAnswersBySession(QuizSession quizSession) {
        return quizAnswerRepository.getQuizAnswersBySession(quizSession)
                .stream()
                .peek(qr -> qr.setResult(
                        qr.getNumberOfCorrectAnswers() * 100.0 / qr.getNumberOfAnswers()))
                .collect(Collectors.toList());
    }

    public QuizResult getPercentRightQuizAnswer(QuizSession quizSession) {
        QuizResult quizResult = quizAnswerRepository.getPercentRightQuizAnswer(quizSession);
        double number = quizResult.getNumberOfCorrectAnswers() * 100.0 / quizResult.getNumberOfAnswers();
        quizResult.setResult(number);
        return quizAnswerRepository.getPercentRightQuizAnswer(quizSession);
    }
}
