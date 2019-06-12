package com.interlink.quiz.object;

public class TopicResult implements Comparable<TopicResult>{

    private Topic topic;
    private long numberOfQuestions;
    private long numberOfCorrectAnswers;
    private double result;

    @Override
    public int compareTo(TopicResult o) {
        return Double.compare(this.getResult(), o.getResult());
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public long getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(long numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public long getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public void setNumberOfCorrectAnswers(long numberOfCorrectAnswers) {
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}