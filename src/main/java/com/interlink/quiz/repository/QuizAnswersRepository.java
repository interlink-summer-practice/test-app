package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.QuizSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizAnswersRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public QuizAnswersRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveQuizAnswer(QuizAnswer quizAnswer) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(quizAnswer);
        transaction.commit();
    }

    public List<QuizAnswer> getAnswersByQuizSession(QuizSession quizSession) {
        return sessionFactory.getCurrentSession()
                .createQuery("from QuizAnswer where quiz_session_id = :quiz_session_id", QuizAnswer.class)
                .setParameter("quiz_session_id", quizSession.getId()).list();
    }

    public void deleteQuizAnswersByQuizSession(QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from QuizAnswer where quizSession = :quizSession")
                .setParameter("quizSession", quizSession)
                .executeUpdate();
        transaction.commit();
    }
}
