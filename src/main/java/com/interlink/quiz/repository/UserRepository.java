package com.interlink.quiz.repository;

import com.interlink.quiz.object.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User getUserByEmail(String email) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return (User) session
                .createQuery("from User where email = :email")
                .setParameter("email", email).uniqueResult();
    }

    public Integer saveUser(User user) {
        return (Integer) sessionFactory.getCurrentSession().save(user);
    }
}
