package com.interlink.quiz.repository;

import com.interlink.quiz.object.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findById(long id);
}
