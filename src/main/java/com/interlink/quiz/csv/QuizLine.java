package com.interlink.quiz.csv;

import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class QuizLine {

    @CsvBindByName(column = "Name")
    private String question;

    @CsvBindByName(column = "Відповіді")
    private String answers;

    @CsvBindByName(column = "Категорія")
    private String topic;

    @CsvBindByName(column = "Правильна відповідь")
    private String rightAnswer;

    @CsvBindByName(column = "Рівень")
    private String difficulty;

    private int mark;
    private List<String> answersList;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
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

    public List<String> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(List<String> answersList) {
        this.answersList = answersList;
    }
}
