package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.QuizSession;

import java.util.List;

public class QuizDto {

    private List<QuestionDto> questions;
    private QuizSession quizSession;

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }
}
