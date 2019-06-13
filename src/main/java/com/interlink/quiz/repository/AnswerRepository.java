package com.interlink.quiz.repository;

import com.interlink.quiz.object.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public class AnswerRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public AnswerRepository(EntityManagerFactory entityManagerFactory) {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public void saveAnswer(Answer answer) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(answer);
        transaction.commit();
    }

    public Answer getAnswerByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Answer where lower(name) = lower(:name)", Answer.class)
                .setParameter("name", name)
                .uniqueResult();
    }

    public Answer getAnswerById(int id) {
        return sessionFactory.getCurrentSession()
                .get(Answer.class, id);
    }
}
