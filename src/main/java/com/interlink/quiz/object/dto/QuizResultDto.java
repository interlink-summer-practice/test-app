package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.QuizSession;

import java.util.List;

public class QuizResultDto {

    private QuizSession quizSession;
    private List<QuizResult> results;

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    public List<QuizResult> getResults() {
        return results;
    }

    public void setResults(List<QuizResult> results) {
        this.results = results;
    }
}