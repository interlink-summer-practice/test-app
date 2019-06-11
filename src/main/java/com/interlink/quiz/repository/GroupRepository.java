package com.interlink.quiz.repository;

import com.interlink.quiz.object.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public GroupRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Group getGroupById(Long id) {
        return sessionFactory.getCurrentSession()
                .get(Group.class, id);
    }

    public Group saveGroup(Group group) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(group);
        transaction.commit();

        return group;
    }

    public void addMemberToGroup(Group group) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(group);
        transaction.commit();
    }
}
