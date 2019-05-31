package com.interlink.quiz.repository;

import com.interlink.quiz.object.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Topic> getTopics() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Topic", Topic.class)
                .list();
    }

    public Topic getTopicByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Topic where lower(name) = lower(:name)", Topic.class)
                .setParameter("name", name)
                .uniqueResult();
    }
}
