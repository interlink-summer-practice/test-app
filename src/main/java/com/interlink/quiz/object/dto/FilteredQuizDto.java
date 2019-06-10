package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.Topic;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilteredQuizDto that = (FilteredQuizDto) o;
        return Arrays.equals(topics, that.topics) && Objects.equals(difficulty, that.difficulty);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(difficulty);
        result = 31 * result + Arrays.hashCode(topics);
        return result;
    }
}
