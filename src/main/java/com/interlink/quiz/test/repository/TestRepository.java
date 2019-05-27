package com.interlink.quiz.test.repository;

import com.interlink.quiz.object.Answer;
import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.test.rowMapper.TopicRowMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class TestRepository {
    private final SessionFactory sessionFactory;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TopicRowMapper topicRowMapper;

    @Autowired
    public TestRepository(SessionFactory sessionFactory,
                          NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                          TopicRowMapper topicRowMapper) {
        this.sessionFactory = sessionFactory;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.topicRowMapper = topicRowMapper;
    }

    public void saveTopic(Topic topic) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(topic);
        transaction.commit();
    }

    public void saveQuestion(Question question) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(question);
        transaction.commit();
    }

    public void saveAnswer(Answer answer) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(answer);
        transaction.commit();
    }

    public List<Topic> listTopics() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Topic> topics = (List<Topic>) session.createQuery("FROM Topic").list();
        transaction.commit();
        return topics;
    }

    public List<Question> getQuestionFromTopic(String nameOfTopic) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String sql = "FROM Question WHERE topic_id = :topic_id";
        Query<Question> query = session.createQuery(sql);
        query.setParameter("topic_id", Objects.requireNonNull(getTopicByName(nameOfTopic)).getId());
        List<Question> topics = query.list();
        transaction.commit();
        session.close();
        return topics;
    }

    private Topic getTopicByName(String nameOfTopic) {
        try {
            String sql = "SELECT * FROM topics WHERE name = :name";
            return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("name", nameOfTopic), topicRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}