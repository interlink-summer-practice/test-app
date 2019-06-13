package com.interlink.quiz.repository;

import com.interlink.quiz.object.UserResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserResultRepository extends JpaRepository<UserResult, Integer> {

    @Transactional
    @Query(value = "delete from UserResult where quizSession.id = :quizSessionId")
    void deleteAllByQuizSessionId(int quizSessionId);
}
