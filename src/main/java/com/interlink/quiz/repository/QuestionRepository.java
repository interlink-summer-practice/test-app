package com.interlink.quiz.repository;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    public List<Question> getQuestionsByTopic(Topic topic) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Question WHERE topic = :topic", Question.class)
                .setParameter("topic", topic)
                .list();
    }

    public List<Question> getNotPassedQuestionsByTopic(Topic topic, QuizSession quizSession) {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("" +
                "SELECT q.* " +
                "FROM questions q " +
                "       LEFT JOIN topics t on q.topic_id = t.id " +
                "WHERE t.id = :topic_id " +
                "EXCEPT " +
                "SELECT q.* " +
                "FROM questions q " +
                "       LEFT JOIN topics t on q.topic_id = t.id " +
                "       LEFT JOIN quiz_answers qa on q.id = qa.question_id " +
                "WHERE qa.quiz_session_id = :session_id", Question.class)
                .setParameter("topic_id", topic.getId())
                .setParameter("session_id", quizSession.getId())
                .list();
    }
}
