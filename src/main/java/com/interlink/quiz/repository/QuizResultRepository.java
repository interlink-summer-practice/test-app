package com.interlink.quiz.repository;

import com.interlink.quiz.object.TopicResult;
import com.interlink.quiz.object.QuizSession;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizResultRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public QuizResultRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<TopicResult> getQuizAnswersBySession(QuizSession quizSession) {
        return sessionFactory.getCurrentSession()
                .createQuery("" +
                        "select new com.interlink.quiz.object.TopicResult (" +
                        "   t, " +
                        "   sum(case when qa.quizSession.id = :quizSessionId then 1 else 0 end )," +
                        "   sum(case when qa.quizSession.id = :quizSessionId and qa.answer = q.rightAnswer then 1 else 0 end ))" +
                        "from QuizAnswer qa " +
                        "       left join Question q on qa.question.id = q.id " +
                        "       left join Answer a on qa.answer.id = a.id " +
                        "       left join Topic t on q.topic.id = t.id " +
                        "where qa.quizSession.id = :quizSessionId " +
                        "group by t.id", TopicResult.class)
                .setParameter("quizSessionId", quizSession.getId())
                .list();
    }

    public TopicResult getPercentRightQuizAnswer(QuizSession quizSession) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT NEW com.interlink.quiz.object.TopicResult (" +
                        "SUM(CASE WHEN qa.quizSession.id = :quizSessionId THEN 1 ELSE 0 END )," +
                        "SUM(CASE WHEN qa.quizSession.id = :quizSessionId AND qa.answer = q.rightAnswer THEN 1 ELSE 0 END ))" +
                        "FROM QuizAnswer qa " +
                        "       LEFT JOIN Question q ON qa.question.id = q.id " +
                        "WHERE qa.quizSession.id = :quizSessionId " +
                        "GROUP BY qa.quizSession", TopicResult.class)
                .setParameter("quizSessionId", quizSession.getId())
                .uniqueResult();
    }
}
