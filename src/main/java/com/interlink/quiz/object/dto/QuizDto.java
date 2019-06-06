package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.QuizSession;

import javax.xml.transform.sax.SAXResult;
import java.util.List;

public class QuizDto {

    private List<QuestionDto> questions;
    private QuizSession quizSession;
    private boolean passed;
    private Long countOfPassedQuestions;

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

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Long getCountOfPassedQuestions() {
        return countOfPassedQuestions;
    }

    public void setCountOfPassedQuestions(Long countOfPassedQuestions) {
        this.countOfPassedQuestions = countOfPassedQuestions;
    }
}
