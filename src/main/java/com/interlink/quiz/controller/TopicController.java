package com.interlink.quiz.controller;

import com.interlink.quiz.object.Topic;
import com.interlink.quiz.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public List<Topic> getTopics() {
        return topicService.getTopics();
    }
}
