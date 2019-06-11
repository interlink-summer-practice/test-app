package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.TopicResult;

import java.util.List;

public class AccountDto {

    private String firstName;
    private String lastName;
    private List<QuizResultDto> results;
    private List<TopicResult> statisticByTopicResults;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<QuizResultDto> getResults() {
        return results;
    }

    public void setResults(List<QuizResultDto> results) {
        this.results = results;
    }

    public List<TopicResult> getStatisticByTopicResults() {
        return statisticByTopicResults;
    }

    public void setStatisticByTopicResults(List<TopicResult> statisticByTopicResults) {
        this.statisticByTopicResults = statisticByTopicResults;
    }
}
