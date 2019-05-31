package com.interlink.quiz.repository;

import com.interlink.quiz.object.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TopicRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TopicRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveTopic(Topic topic) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(topic);
        transaction.commit();
    }

    public Topic getTopicByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Topic> query = session.createQuery("from Topic where lower(name) = lower(:name)", Topic.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }
}
