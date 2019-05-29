package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.QuizSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    public List<QuizResult> getQuizAnswersBySession(QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Query<QuizResult> query = session
                .createQuery("" +
                        "select new com.interlink.quiz.object.QuizResult (" +
                        "   t, " +
                        "   sum(case when qa.quizSession = :quizSession then 1 else 0 end )," +
                        "   sum(case when qa.quizSession = :quizSession and qa.answer = q.rightAnswer then 1 else 0 end ))" +
                        "from QuizAnswer qa " +
                        "       left join Question q on qa.question.id = q.id " +
                        "       left join Answer a on qa.answer.id = a.id " +
                        "       left join Topic t on q.topic.id = t.id " +
                        "group by t.id", QuizResult.class);
        query.setParameter("quizSession", quizSession);
        return query.list();
    }
}
