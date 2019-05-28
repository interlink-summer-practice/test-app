package com.interlink.quiz.object;

import javax.persistence.*;

@Entity
@Table(name = "quiz_session")
public class QuizSession {

    @Id
    @Column(name = "session_id")
    private String sessionId;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuizSession quizSession = (QuizSession) obj;
        return sessionId.equals(quizSession.getSessionId());
    }

    @Override
    public int hashCode() {
        int result = sessionId.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
