package com.interlink.quiz.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuizResultRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public QuizResultRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
