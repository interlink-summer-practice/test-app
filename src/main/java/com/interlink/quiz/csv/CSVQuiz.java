package com.interlink.quiz.csv;

import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class CSVQuiz {

    @CsvBindByName(column = "Test")
    private String quizName;

    @CsvBindByName(column = "Topic")
    private String topicName;

    @CsvBindByName(column = "quest_name")
    private String questionName;

    @CsvBindByName(column = "difficulty")
    private String difficulty;

    @CsvBindByName(column = "mark")
    private int mark;

    @CsvBindByName(column = "var_1")
    private String firstAnswer;

    @CsvBindByName(column = "var_2")
    private String secondAnswer;

    @CsvBindByName(column = "var_3")
    private String thirdAnswer;

    @CsvBindByName(column = "var_4")
    private String fourthAnswer;

    @CsvBindByName(column = "answer")
    private String rightAnswer;

    private List<String> answers;

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
