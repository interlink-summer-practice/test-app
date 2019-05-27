package com.interlink.quiz.quizzes.repositories;

import com.interlink.quiz.object.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Answer> getAnswerFromQuestion(Integer questionId, Integer topicId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "FROM Answer AS a, Question AS q WHERE a.question_id = :question_id AND q.topic_id = :topic_id";
        Query<Answer> query = session.createQuery(sql);
        query.setParameter("question_id", questionId);
        query.setParameter("topic_id", topicId);
        List<Answer> topics = query.list();
        transaction.commit();
        session.close();
        return topics;
    }
}