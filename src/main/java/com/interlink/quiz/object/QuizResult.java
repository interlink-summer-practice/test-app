package com.interlink.quiz.object;

import java.util.List;

public class QuizResult {

    private int mark;
    private long countOfQuestion;
    private long countOfCorrectAnswers;
    private double percentOfPassingQuiz;
    private List<TopicResult> topicResults;

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public long getCountOfQuestion() {
        return countOfQuestion;
    }

    public void setCountOfQuestion(long countOfQuestion) {
        this.countOfQuestion = countOfQuestion;
    }

    public long getCountOfCorrectAnswers() {
        return countOfCorrectAnswers;
    }

    public void setCountOfCorrectAnswers(long countOfCorrectAnswers) {
        this.countOfCorrectAnswers = countOfCorrectAnswers;
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
