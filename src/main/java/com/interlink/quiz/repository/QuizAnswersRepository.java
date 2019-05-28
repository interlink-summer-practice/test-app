package com.interlink.quiz.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuizAnswersRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public QuizAnswersRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
