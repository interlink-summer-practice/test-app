package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.User;
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

    public void updateQuizSession(QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(quizSession);
        transaction.commit();
    }

    public List<QuizSession> getQuizSessionBySessionId(String sessionId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from QuizSession where session_id = :session_id", QuizSession.class)
                .setParameter("session_id", sessionId)
                .list();
    }

    public List<QuizSession> getQuizSessionsByUserId(User user) {
        return sessionFactory.getCurrentSession()
                .createQuery("from QuizSession qs where qs.user.id = :userId", QuizSession.class)
                .setParameter("userId", user.getId())
                .list();
    }

    public Integer getMarkByQuizSession(QuizSession quizSession) {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("SELECT sum(q.mark) " +
                        "FROM quiz_session qs" +
                        "         LEFT JOIN quiz_answers qa on qs.id = qa.quiz_session_id " +
                        "         LEFT JOIN answers a on qa.answer_id = a.id " +
                        "         LEFT JOIN questions q on a.id = q.answer_id " +
                        "WHERE qs.id = '1' AND qa.answer_id = q.answer_id;", Integer.class)
                .setParameter("quiz_session_id", quizSession.getId())
                .uniqueResult();
    }

    public QuizSession getQuizSessionById(int id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from QuizSession where id = :id", QuizSession.class)
                .setParameter("id", id)
                .uniqueResult();
    }
}
