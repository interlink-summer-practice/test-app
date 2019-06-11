package com.interlink.quiz.object;

public class CuratorQuiz {

    private Topic[] topics;
    private String[] difficulties;
    private Long groupId;

    public Topic[] getTopics() {
        return topics;
    }

    public void setTopics(Topic[] topics) {
        this.topics = topics;
    }

    public String[] getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(String[] difficulties) {
        this.difficulties = difficulties;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
