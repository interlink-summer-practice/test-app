package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.object.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizAnswerRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public QuizAnswerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveQuizAnswer(QuizAnswer quizAnswer) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(quizAnswer);
        transaction.commit();
    }

    public List<QuizAnswer> getAnswersByQuizSession(QuizSession quizSession) {
        return sessionFactory.getCurrentSession()
                .createQuery("from QuizAnswer where quiz_session_id = :quiz_session_id", QuizAnswer.class)
                .setParameter("quiz_session_id", quizSession.getId())
                .list();
    }

    public Long getCountOfRightAnswerBySessionAndTopic(QuizSession quizSession, Topic topic) {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("select count(qa) from QuizAnswer qa " +
                        "left join Question q on qa.question.id = q.id " +
                        "where qa.quizSession.id = :quizSessionId and q.topic.id = :topicId and qa.answer.id = q.rightAnswer.id")
                .setParameter("quizSessionId", quizSession.getId())
                .setParameter("topicId", topic.getId())
                .uniqueResult();
    }

    public Long getCountOfRightAnswerBySession(QuizSession quizSession) {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("select count(qa) from QuizAnswer qa " +
                        "left join Question q on qa.question.id = q.id " +
                        "where qa.quizSession.id = :quizSessionId and qa.answer.id = q.rightAnswer.id")
                .setParameter("quizSessionId", quizSession.getId())
                .uniqueResult();
    }

    public void deleteQuizAnswersByQuizSession(QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from QuizAnswer where quizSession.id = :quizSessionId")
                .setParameter("quizSessionId", quizSession.getId())
                .executeUpdate();
        transaction.commit();
    }

    public Long getCountOfPassedQuestions(QuizSession quizSession) {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("select count(qa) from QuizAnswer qa " +
                        "where quizSession.id = :quizSessionId")
                .setParameter("quizSessionId", quizSession.getId())
                .uniqueResult();
    }

    public int getCountOfRightAnswerByTopic(User user, Topic topic) {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select distinct on (qa.question_id) qa " +
                        "from quiz_answers qa " +
                        "         left join questions q on qa.question_id = q.id " +
                        "         left join quiz_session qs on qa.quiz_session_id = qs.id " +
                        "         left join users u on qs.user_id = u.id " +
                        "where u.id = :userId " +
                        "  and q.topic_id = :topicId and qa.answer_id = q.answer_id " +
                        "group by qs.id, qa.id", QuizAnswer.class)
                .setParameter("user", user)
                .setParameter("topic", topic)
                .list().size();
    }
}