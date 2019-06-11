package com.interlink.quiz.object.dto;

import java.util.List;

public class GroupResultDto {

    private String groupName;
    private List<MemberResultDto> results;

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
