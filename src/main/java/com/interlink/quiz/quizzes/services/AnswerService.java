package com.interlink.quiz.quizzes.services;

import com.interlink.quiz.object.Answer;
import com.interlink.quiz.quizzes.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void saveAnswer(Answer answer) {
        answerRepository.saveAnswer(answer);
    }

    public List<Answer> getAnswerFromQuestion(String nameOfTopic, String nameOfQuestion) {
        return answerRepository.getAnswerFromQuestion(nameOfTopic, nameOfQuestion);
    }
}