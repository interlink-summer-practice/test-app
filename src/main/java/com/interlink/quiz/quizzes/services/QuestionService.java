package com.interlink.quiz.quizzes.services;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.quizzes.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void saveQuestion(Question question) {
        questionRepository.saveQuestion(question);
    }

    public List<Question> getQuestionFromTopic(String nameOfTopic) {
        return questionRepository.getQuestionFromTopic(nameOfTopic);
    }

    public Question getQuestionByName(String nameOfQuestion) {
        return questionRepository.getQuestionByName(nameOfQuestion);
    }
}