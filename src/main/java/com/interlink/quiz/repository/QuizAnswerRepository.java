package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.QuizSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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

    public void deleteQuizAnswersByQuizSession(QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from QuizAnswer where quizSession.id = :quizSessionId")
                .setParameter("quizSessionId", quizSession.getId())
                .executeUpdate();
        transaction.commit();
    }

    public List<QuizResult> getQuizAnswersBySession(QuizSession quizSession) {
        return sessionFactory.getCurrentSession()
                .createQuery("" +
                        "select new com.interlink.quiz.object.QuizResult (" +
                        "   t, " +
                        "   sum(case when qa.quizSession.id = :quizSessionId then 1 else 0 end )," +
                        "   sum(case when qa.quizSession.id = :quizSessionId and qa.answer = q.rightAnswer then 1 else 0 end ))" +
                        "from QuizAnswer qa " +
                        "       left join Question q on qa.question.id = q.id " +
                        "       left join Answer a on qa.answer.id = a.id " +
                        "       left join Topic t on q.topic.id = t.id " +
                        "where qa.quizSession.id = :quizSessionId " +
                        "group by t.id", QuizResult.class)
                .setParameter("quizSessionId", quizSession.getId())
                .list();
    }

    public QuizResult getPercentRightQuizAnswer(QuizSession quizSession) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT NEW com.interlink.quiz.object.QuizResult (" +
                        "SUM(CASE WHEN qa.quizSession.id = :quizSessionId THEN 1 ELSE 0 END )," +
                        "SUM(CASE WHEN qa.quizSession.id = :quizSessionId AND qa.answer = q.rightAnswer THEN 1 ELSE 0 END ))" +
                        "FROM QuizAnswer qa " +
                        "       LEFT JOIN Question q ON qa.question.id = q.id " +
                        "WHERE qa.quizSession.id = :quizSessionId " +
                        "GROUP BY qa.quizSession", QuizResult.class)
                .setParameter("quizSessionId", quizSession.getId())
                .uniqueResult();
    }
}