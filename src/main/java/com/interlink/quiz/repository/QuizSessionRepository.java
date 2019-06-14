package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface QuizSessionRepository extends JpaRepository<QuizSession, Integer> {


    QuizSession findById(int id);

    @Query("select qs from QuizSession qs where qs.sessionId = :sessionId and qs.user = null")
    List<QuizSession> findAllBySessionIdAndUserIsNull(@Param("sessionId") String sessionId);

    @Query(value = "select qs.* " +
            "from group_members gm " +
            "         left join quiz_session qs on gm.quiz_session_id = qs.id " +
            "where qs.user_id = :userId", nativeQuery = true)
    List<QuizSession> findAllByGroupMembers(@Param("userId") int userId);

    @Query(value = "select * from quiz_session where user_id = :userId" +
            "    EXCEPT " +
            "select qs.* " +
            "from group_members gm " +
            "         left join quiz_session qs on gm.quiz_session_id = qs.id " +
            "where qs.user_id = :userId", nativeQuery = true)
    List<QuizSession> findAllByUserId(@Param("userId") int userId);

    @Query(value = "SELECT coalesce(sum(q.mark), 0)" +
            "FROM quiz_session qs" +
            "         LEFT JOIN quiz_answers qa on qs.id = qa.quiz_session_id " +
            "         LEFT JOIN answers a on qa.answer_id = a.id " +
            "         LEFT JOIN questions q on a.id = q.answer_id " +
            "WHERE qs.id = :quiz_session_id AND qa.answer_id = q.answer_id", nativeQuery = true)
    Long getMarkByQuizSessionId(@Param("quiz_session_id") int quizSessionId);
}
