package com.interlink.quiz.object;

import java.util.List;

public class QuizResult {

    private int mark;
    private double percentOfPassingQuiz;
    private List<TopicResult> topicResults;

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public double getPercentOfPassingQuiz() {
        return percentOfPassingQuiz;
    }

    public void setPercentOfPassingQuiz(double percentOfPassingQuiz) {
        this.percentOfPassingQuiz = percentOfPassingQuiz;
    }

    public List<TopicResult> getTopicResults() {
        return topicResults;
    }

    public void setTopicResults(List<TopicResult> topicResults) {
        this.topicResults = topicResults;
    }
}
