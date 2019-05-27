package com.interlink.quiz.repository;

import com.interlink.quiz.object.Role;
import com.interlink.quiz.object.UserRole;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public RoleRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addRoleToUser(UserRole userRole) {
        sessionFactory.getCurrentSession().save(userRole);
    }

    public Role getRoleByName(String roleName) {
        return (Role) sessionFactory.getCurrentSession()
                .createQuery("from Role where name = :name")
                .setParameter("name", roleName)
                .uniqueResult();
    }
}
