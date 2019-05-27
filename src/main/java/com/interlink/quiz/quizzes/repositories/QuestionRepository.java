package com.interlink.quiz.quizzes.repositories;

import com.interlink.quiz.object.Question;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Question> getQuestionFromTopic(Integer topicId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String sql = "FROM Question WHERE topic_id = :topic_id";
        Query<Question> query = session.createQuery(sql);
        query.setParameter("topic_id", topicId);
        List<Question> questions = query.list();
        transaction.commit();
        session.close();
        return questions;
    }

    public Question getQuestionByName(String nameOfQuestion) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Question.class);
        Question question = (Question) criteria.add(Restrictions.eq("name", nameOfQuestion)).uniqueResult();
        transaction.commit();
        session.close();
        return question;
    }
}