package com.interlink.quiz.repository;

import com.interlink.quiz.object.Group;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public GroupRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Group getGroupById(Long id) {
        return sessionFactory.getCurrentSession()
                .get(Group.class, id.intValue());
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

    public void setQuizSessionForMember(Group group, User user, QuizSession quizSession) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("" +
                "update group_members set quiz_session_id = :quizSessionId " +
                "where group_id = :groupId and user_id = :userId")
                .setParameter("quizSessionId", quizSession.getId())
                .setParameter("groupId", group.getId())
                .setParameter("userId", user.getId());
        transaction.commit();
    }

    public List<Group> getGroupsByCurator(int userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Group g where g.curator.id = :userId", Group.class)
                .setParameter("userId", userId)
                .list();
    }
}
