package com.interlink.quiz.quizzes.repositories;

import com.interlink.quiz.object.Topic;
import com.interlink.quiz.quizzes.rowMappers.TopicRowMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TopicRepository {
    private final SessionFactory sessionFactory;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TopicRowMapper topicRowMapper;

    @Autowired
    public TopicRepository(SessionFactory sessionFactory,
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

    public List<Topic> getTopics() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Topic> topics = (List<Topic>) session.createQuery("FROM Topic").list();
        transaction.commit();
        return topics;
    }

    public Topic getTopicByName(String nameOfTopic) {
        try {
            String sql = "SELECT * FROM topics WHERE name = :name";
            return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("name", nameOfTopic), topicRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}