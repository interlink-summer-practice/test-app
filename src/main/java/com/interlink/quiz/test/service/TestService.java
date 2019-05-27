package com.interlink.quiz.test.service;

import com.interlink.quiz.object.Answer;
import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.test.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public void saveTopic(Topic topic) {
        testRepository.saveTopic(topic);
    }

    public void saveQuestion(Question question) {
        testRepository.saveQuestion(question);
    }

    public void saveAnswer(Answer answer) {
        testRepository.saveAnswer(answer);
    }

    public List listTopics() {
        return testRepository.listTopics();
    }

    public List<Topic> getTopic(String nameOfTopic) {
        return testRepository.getTopic(nameOfTopic);
    }
}