package com.interlink.quiz.repository;

import com.interlink.quiz.object.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    Group findById(int id);

    List<Group> findAllByCuratorId(int curatorId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update group_members set quiz_session_id = :quizSessionId " +
            "where group_id = :groupId and user_id = :userId", nativeQuery = true)
    void updateQuizSessionForMember(@Param("quizSessionId") int quizSessionId,
                                    @Param("groupId") int groupId,
                                    @Param("userId") int userId);
}
