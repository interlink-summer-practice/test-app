package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.QuizSession;

import java.util.List;

public class QuestionsDto {

    private List<Question> questions;
    private QuizSession quizSession;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }
}
