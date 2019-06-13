package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.math.BigInteger;
import java.util.List;

@Repository
public class QuizSessionRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public QuizSessionRepository(EntityManagerFactory entityManagerFactory) {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public void createQuizSession(QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(quizSession);
        transaction.commit();
    }

    public void updateQuizSession(QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(quizSession);
        transaction.commit();
    }

    public QuizSession getQuizSessionById(int id) {
        return sessionFactory.getCurrentSession()
                .get(QuizSession.class, id);
    }

    public List<QuizSession> getQuizSessionBySessionId(String sessionId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from QuizSession where session_id = :session_id and user_id = null", QuizSession.class)
                .setParameter("session_id", sessionId)
                .list();
    }

    public List<QuizSession> getQuizSessionsByUser(User user) {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select * from quiz_session where user_id = :userId" +
                        "    EXCEPT " +
                        "select qs.* " +
                        "from group_members gm " +
                        "         left join quiz_session qs on gm.quiz_session_id = qs.id " +
                        "where gm.user_id = :userId", QuizSession.class)
                .setParameter("userId", user.getId())
                .list();
    }

    public List<QuizSession> getQuizSessionsByGroupMember(User user) {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select qs.* " +
                        "from group_members gm " +
                        "         left join quiz_session qs on gm.quiz_session_id = qs.id " +
                        "where gm.user_id = :userId", QuizSession.class)
                .setParameter("userId", user.getId())
                .list();
    }

    public int getMarkByQuizSession(QuizSession quizSession) {
        try {
            BigInteger mark = (BigInteger) sessionFactory.getCurrentSession()
                    .createNativeQuery("SELECT sum(q.mark) " +
                            "FROM quiz_session qs" +
                            "         LEFT JOIN quiz_answers qa on qs.id = qa.quiz_session_id " +
                            "         LEFT JOIN answers a on qa.answer_id = a.id " +
                            "         LEFT JOIN questions q on a.id = q.answer_id " +
                            "WHERE qs.id = :quiz_session_id AND qa.answer_id = q.answer_id;")
                    .setParameter("quiz_session_id", quizSession.getId())
                    .uniqueResult();
            return mark.intValue();
        } catch (NullPointerException e) {
            return 0;
        }
    }

}
