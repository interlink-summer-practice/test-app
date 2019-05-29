package com.interlink.quiz.service;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestionsByTopics(Topic[] topicsArray) {
        List<Topic> topics = Arrays.stream(topicsArray).collect(Collectors.toList());
        List<Question> questions = new ArrayList<>();
        for (Topic topic : topics) {
            questions.addAll(questionRepository.getQuestionsByTopic(topic));
        }
        return questions;
    }
}
