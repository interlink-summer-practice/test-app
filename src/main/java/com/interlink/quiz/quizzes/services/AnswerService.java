package com.interlink.quiz.quizzes.services;

import com.interlink.quiz.object.Answer;
import com.interlink.quiz.quizzes.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final TopicService topicService;
    private final QuestionService questionService;

    @Autowired
    public AnswerService(AnswerRepository answerRepository,
                         TopicService topicService,
                         QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.topicService = topicService;
        this.questionService = questionService;
    }

    public void saveAnswer(Answer answer) {
        answerRepository.saveAnswer(answer);
    }

    public List<Answer> getAnswerFromQuestion(String nameOfTopic, String nameOfQuestion) {
        Integer questionId = questionService.getQuestionByName(nameOfQuestion).getId();
        Integer topicId = topicService.getTopicByName(nameOfTopic).getId();
        return answerRepository.getAnswerFromQuestion(questionId, topicId);
    }
}