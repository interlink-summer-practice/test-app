package com.interlink.quiz.object;

import javax.persistence.*;

@Entity
@Table(name = "user_results")
public class UserResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "quiz_session_id")
    private QuizSession quizSession;

    @Column
    private int result;

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
