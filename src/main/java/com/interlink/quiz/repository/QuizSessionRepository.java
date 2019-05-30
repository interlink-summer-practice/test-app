package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizSessionRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public QuizSessionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createQuizSession(QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(quizSession);
        transaction.commit();
    }

    public List<QuizSession> getQuizSessionBySessionId(String sessionId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from QuizSession where session_id = :session_id", QuizSession.class)
                .setParameter("session_id", sessionId)
                .list();
    }
}
