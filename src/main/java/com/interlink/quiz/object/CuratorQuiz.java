package com.interlink.quiz.object;

public class CuratorQuiz {

    private Topic[] topics;
    private String[] difficulty;
    private String group;

    public Topic[] getTopics() {
        return topics;
    }

    public void setTopics(Topic[] topics) {
        this.topics = topics;
    }

    public String[] getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String[] difficulty) {
        this.difficulty = difficulty;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
