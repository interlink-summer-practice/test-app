package com.interlink.quiz.test.repository;

import com.interlink.quiz.object.Answer;
import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TestRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveTopic(Topic topic) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(topic);
        transaction.commit();
    }

    public void saveQuestion(Question question) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(question);
        transaction.commit();
    }

    public void saveAnswer(Answer answer) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(answer);
        transaction.commit();
    }

    public List<Topic> listTopics() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Topic> topics = (List<Topic>) session.createQuery("FROM Topic").list();
        transaction.commit();
        return topics;
    }

    public List<Topic> getTopic(String nameOfTopic) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Topic AS t INNER JOIN Question AS q ON t.id = q.topic_id WHERE name = :name");
        query.setParameter("name", nameOfTopic);
        List<Topic> topics = query.list();
        transaction.commit();
        session.close();
        return topics;
    }
}