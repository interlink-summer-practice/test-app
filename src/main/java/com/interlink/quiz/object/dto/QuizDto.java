package com.interlink.quiz.object.dto;

import com.interlink.quiz.object.QuizSession;

import java.util.List;

public class QuizDto {

    private List<QuestionDto> questions;
    private QuizSession quizSession;
    private Long countOfPassedQuestions;
    private int countOfQuestionsInQuiz;
    private boolean passed;
    private boolean existNewQuestions;

    public int getCountOfQuestionsInQuiz() {
        return countOfQuestionsInQuiz;
    }

    public void setCountOfQuestionsInQuiz(int countOfQuestionsInQuiz) {
        this.countOfQuestionsInQuiz = countOfQuestionsInQuiz;
    }

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

    public boolean isExistNewQuestions() {
        return existNewQuestions;
    }

    public void setExistNewQuestions(boolean existNewQuestions) {
        this.existNewQuestions = existNewQuestions;
    }
}
