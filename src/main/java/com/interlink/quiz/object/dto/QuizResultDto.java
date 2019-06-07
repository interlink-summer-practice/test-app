package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.Topic;

import java.util.List;

public class QuizResultDto {

    private int quizSessionId;
    private String firstName;
    private String lastName;
    private String date;
    private List<Topic> topics;
    private Long countOfQuestions;
    private Long countOfCorrectAnswers;
    private double percentOfPassingQuiz;
    private String difficulty;

    public int getQuizSessionId() {
        return quizSessionId;
    }

    public void setQuizSessionId(int quizSessionId) {
        this.quizSessionId = quizSessionId;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public Long getCountOfQuestions() {
        return countOfQuestions;
    }

    public void setCountOfQuestions(Long countOfQuestions) {
        this.countOfQuestions = countOfQuestions;
    }

    public Long getCountOfCorrectAnswers() {
        return countOfCorrectAnswers;
    }

    public void setCountOfCorrectAnswers(Long countOfCorrectAnswers) {
        this.countOfCorrectAnswers = countOfCorrectAnswers;
    }

    public double getPercentOfPassingQuiz() {
        return percentOfPassingQuiz;
    }

    public void setPercentOfPassingQuiz(double percentOfPassingQuiz) {
        this.percentOfPassingQuiz = percentOfPassingQuiz;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
