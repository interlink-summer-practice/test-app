package com.interlink.quiz.repository;

import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.object.User;
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
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Integer> {

    List<QuizAnswer> findAllByQuizSessionId(int quizSessionId);

    @Query(value = "select distinct on(qa.question_id) qa.* " +
            "from quiz_answers qa " +
            "left join questions q on qa.question_id = q.id " +
            "left join quiz_session qs on qa.quiz_session_id = qs.id " +
            "where qs.user_id = :userId " +
            "  and q.topic_id = :topicId " +
            "group by qs.id, qa.id", nativeQuery = true)
    List<QuizAnswer> findAllByUserIdAndTopicId(@Param("userId") int userId,
                                               @Param("topicId") int topicId);

    @Query(value = "select distinct on (qa.question_id) qa.* " +
            "from quiz_answers qa " +
            "   left join questions q on qa.question_id = q.id " +
            "   left join quiz_session qs on qa.quiz_session_id = qs.id " +
            "   left join users u on qs.user_id = u.id " +
            "where u.id = :userId" +
            "   and q.topic_id = :topicId " +
            "   and qa.answer_id = q.answer_id " +
            "group by qs.id, qa.id", nativeQuery = true)
    List<QuizAnswer> findAllRightAnswerByUserIdAndTopicId(@Param("userId") int userId,
                                                          @Param("topicId") int topicId);

    @Query("select count(qa) from QuizAnswer qa " +
            "where qa.quizSession.id = :quizSessionId " +
            "   and qa.question.topic.id = :topicId " +
            "   and qa.answer.id = qa.question.rightAnswer.id")
    Long countOfRightAnswerByQuizSessionIdAndTopicId(@Param("quizSessionId") int quizSessionId,
                                                     @Param("topicId") int topicId);

    @Query("select count(qa) from QuizAnswer qa " +
            "where qa.quizSession.id = :quizSessionId " +
            "   and qa.answer.id = qa.question.rightAnswer.id")
    Long countOfRightAnswerByQuizSessionId(@Param("quizSessionId") int quizSessionId);

    @Query
    Long countByQuizSessionId(int quizSessionId);

    @Query
    void deleteAllByQuizSessionId(int quizSessionId);
}