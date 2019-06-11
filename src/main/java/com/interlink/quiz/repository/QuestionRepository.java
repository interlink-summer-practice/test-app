package com.interlink.quiz.repository;

import com.interlink.quiz.object.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    public Question getQuestionById(int id) {
        return sessionFactory.getCurrentSession()
                .get(Question.class, id);
    }

    public Long getCountByTopicAndDifficulty(String difficulty, Topic topic) {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("select count(q) from Question q " +
                        "WHERE topic = :topic and difficulty = :difficulty")
                .setParameter("topic", topic)
                .setParameter("difficulty", difficulty)
                .uniqueResult();
    }

    public List<Question> getQuestionsByTopic(Topic topic, String difficulty) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Question WHERE topic = :topic AND difficulty = :difficulty", Question.class)
                .setParameter("topic", topic)
                .setParameter("difficulty", difficulty)
                .list();
    }

    public List<Question> getNotPassedQuestionsByTopic(Topic topic, QuizSession quizSession, String difficulty) {

        return sessionFactory.getCurrentSession()
                .createNativeQuery("" +
                        "SELECT q.* " +
                        "FROM questions q " +
                        "       LEFT JOIN topics t on q.topic_id = t.id " +
                        "WHERE t.id = :topic_id AND q.difficulty = :difficulty " +
                        "EXCEPT " +
                        "SELECT q.* " +
                        "FROM questions q " +
                        "       LEFT JOIN topics t on q.topic_id = t.id " +
                        "       LEFT JOIN quiz_answers qa on q.id = qa.question_id " +
                        "WHERE qa.quiz_session_id = :session_id", Question.class)
                .setParameter("topic_id", topic.getId())
                .setParameter("session_id", quizSession.getId())
                .setParameter("difficulty", difficulty)
                .list();
    }

    public int getCountOfQuestionByTopic(User user, Topic topic) {

        List<QuizAnswer> list = sessionFactory.getCurrentSession()
                .createNativeQuery("select distinct on(qa.question_id) qa.* " +
                        "from quiz_answers qa " +
                        "         left join questions q on qa.question_id = q.id " +
                        "         left join quiz_session qs on qa.quiz_session_id = qs.id " +
                        "         left join users u on qs.user_id = u.id " +
                        "where u.id = :user_id " +
                        "  and q.topic_id = :topic_id " +
                        "group by qs.id, qa.id;", QuizAnswer.class)
                .setParameter("user_id", user.getId())
                .setParameter("topic_id", topic.getId()).list();

        return list.size();
    }
}
