package com.interlink.quiz.object.dto;

import java.util.List;

public class GroupResultDto {

    private String groupName;
    private String quizUrl;
    private List<MemberResultDto> results;

    public String getQuizUrl() {
        return quizUrl;
    }

    public void setQuizUrl(String quizUrl) {
        this.quizUrl = quizUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<MemberResultDto> getResults() {
        return results;
    }

    public void setResults(List<MemberResultDto> results) {
        this.results = results;
    }
}
