package com.interlink.quiz.controller;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.service.QuestionService;
import com.interlink.quiz.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizController {

    private final TopicService topicService;
    private final QuestionService questionService;

    @Autowired
    public QuizController(TopicService topicService,
                          QuestionService questionService) {
        this.topicService = topicService;
        this.questionService = questionService;
    }

    @GetMapping("/get_topics")
    public List<Topic> getTopics() {
        return topicService.getTopics();
    }

    @GetMapping("/get_questions")
    public List<Question> getQuestions(@RequestParam Topic[] topics) {
        return questionService.getQuestionsByTopics(topics);
    }
}