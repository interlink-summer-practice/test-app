package com.interlink.quiz.repository;

import com.interlink.quiz.object.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    List<Topic> findAll();

    Topic findByName(String name);
}
