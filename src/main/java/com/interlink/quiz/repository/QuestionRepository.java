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

    @Query(value =
            "   select q.* from questions q " +
            "   left join topics t on q.topic_id = t.id " +
            "   where t.id = :topicId and q.difficulty = :difficulty " +
            "except " +
            "   select q.* from questions q " +
            "   left join topics t on q.topic_id = t.id " +
            "   left join quiz_answers qa on q.id = qa.question_id " +
            "   where qa.quiz_session_id = :quizSessionId", nativeQuery = true)
    List<Question> findAllNotPassedQuestions(@Param("topicId") int topicId,
                                             @Param("difficulty") String difficulty,
                                             @Param("quizSessionId") int quizSessionId);

    Long countByTopicAndDifficulty(Topic topic, String difficulty);
}
