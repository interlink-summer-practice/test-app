package com.interlink.quiz.repository;

import com.interlink.quiz.object.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public AnswerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveAnswer(Answer answer) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(answer);
        transaction.commit();
    }

    public Answer getAnswerByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Answer> query = session.createQuery("from Answer where lower(name) = :name", Answer.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }
}
