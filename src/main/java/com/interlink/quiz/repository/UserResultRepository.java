package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.UserResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserResultRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserResultRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveUserResult(UserResult userResult) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(userResult);
        transaction.commit();
    }

    public void deleteUserResult(QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from UserResult where quizSession.id = :quizSessionId")
                .setParameter("quizSessionId", quizSession.getId())
                .executeUpdate();
        transaction.commit();
    }
}
