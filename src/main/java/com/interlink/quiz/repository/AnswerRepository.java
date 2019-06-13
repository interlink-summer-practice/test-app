package com.interlink.quiz.repository;

import com.interlink.quiz.object.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    Answer findById(int id);

    Answer findByName(String name);
}
