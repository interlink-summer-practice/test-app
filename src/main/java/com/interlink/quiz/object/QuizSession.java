package com.interlink.quiz.object;

import javax.persistence.*;

@Entity
@Table(name = "quiz_session")
public class QuizSession {

    @Id
    @Column(name = "session_id")
    private String sessionId;

    @Column
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
