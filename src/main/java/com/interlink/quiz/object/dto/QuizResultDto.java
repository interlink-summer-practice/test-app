package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.TopicResult;
import com.interlink.quiz.object.QuizSession;

import java.util.List;

public class QuizResultDto {

    private QuizSession quizSession;
    private QuizResult quizResult;

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    public QuizResult getQuizResult() {
        return quizResult;
    }

    public void setQuizResult(QuizResult quizResult) {
        this.quizResult = quizResult;
    }
}
