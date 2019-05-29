package com.interlink.quiz.object;

public class QuizResult {

    private Topic topic;
    private long numberOfAnswers;
    private long numberOfCorrectAnswers;
    private double result;

    public QuizResult() { }

    public QuizResult(Topic topic, long numberOfAnswers, long numberOfCorrectAnswers) {
        this.topic = topic;
        this.numberOfAnswers = numberOfAnswers;
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public long getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(long numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
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
