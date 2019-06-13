package com.interlink.quiz.repository;

import com.interlink.quiz.object.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public class RoleRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public RoleRepository(EntityManagerFactory entityManagerFactory) {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public Role getRoleByName(String roleName) {
        return (Role) sessionFactory.getCurrentSession()
                .createQuery("from Role where name = :name")
                .setParameter("name", roleName)
                .uniqueResult();
    }
}
