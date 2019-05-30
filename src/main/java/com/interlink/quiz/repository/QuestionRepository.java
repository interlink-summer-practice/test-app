package com.interlink.quiz.repository;

import com.interlink.quiz.object.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public QuestionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveQuestion(Question question) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(question);
        transaction.commit();
    }
}
