package com.interlink.quiz.repository;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.Topic;
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

    public List<Question> getQuestionsByTopic(Topic topic) {
        Query<Question> query = sessionFactory.getCurrentSession()
                .createQuery("FROM Question WHERE topic = :topic", Question.class);
        query.setParameter("topic", topic);
        return query.list();
    }
}
