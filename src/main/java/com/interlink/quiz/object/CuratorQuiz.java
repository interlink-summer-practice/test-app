package com.interlink.quiz.object;

public class CuratorQuiz {

    private Topic[] topics;
    private String[] difficulties;
    private String group;

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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
