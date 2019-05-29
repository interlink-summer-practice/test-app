package com.interlink.quiz.object;

import javax.persistence.*;

@Entity
@Table(name = "quiz_answers")
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private QuizSession quizSession;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}