package com.interlink.quiz.quizzes.repositories;

import com.interlink.quiz.object.Topic;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
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
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Topic> topics = (List<Topic>) session.createQuery("FROM Topic").list();
        transaction.commit();
        return topics;
    }

    public Topic getTopicByName(String nameOfTopic) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Topic.class);
        Topic topic = (Topic) criteria.add(Restrictions.eq("name", nameOfTopic)).uniqueResult();
        transaction.commit();
        session.close();
        return topic;
    }
}