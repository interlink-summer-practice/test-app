package com.interlink.quiz.repository;

import com.interlink.quiz.object.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    Answer findById(int id);

    Answer findByName(String name);
}
