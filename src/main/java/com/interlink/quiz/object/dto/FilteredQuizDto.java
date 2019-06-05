package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.Topic;

public class FilteredQuizDto {

    private Topic[] topics;
    private String difficulty;

    public Topic[] getTopics() {
        return topics;
    }

    public void setTopics(Topic[] topics) {
        this.topics = topics;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
