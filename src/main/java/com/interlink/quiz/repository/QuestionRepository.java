package com.interlink.quiz.repository;

import com.interlink.quiz.object.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findById(int id);

    List<Question> findAllByTopicAndDifficulty(Topic topic, String difficulty);

    Long countByTopicAndDifficulty(Topic topic, String difficulty);

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
