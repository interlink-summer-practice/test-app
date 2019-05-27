package com.interlink.quiz.quizzes.services;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.quizzes.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TopicService topicService;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           TopicService topicService) {
        this.questionRepository = questionRepository;
        this.topicService = topicService;
    }

    public void saveQuestion(Question question) {
        questionRepository.saveQuestion(question);
    }

    public List<Question> getQuestionFromTopic(String nameOfTopic) {
        Integer topicId = topicService.getTopicByName(nameOfTopic).getId();
        return questionRepository.getQuestionFromTopic(topicId);
    }

    public Question getQuestionByName(String nameOfQuestion) {
        return questionRepository.getQuestionByName(nameOfQuestion);
    }
}