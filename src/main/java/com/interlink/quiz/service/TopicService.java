package com.interlink.quiz.service;

import com.interlink.quiz.object.Topic;
import com.interlink.quiz.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void saveTopic(Topic topic) {
        topicRepository.saveTopic(topic);
    }

    public List<Topic> getTopics() {
        return topicRepository.getTopics();
    }

    public Topic getTopicByName(String nameOfTopic) {
        return topicRepository.getTopicByName(nameOfTopic);
    }
}
