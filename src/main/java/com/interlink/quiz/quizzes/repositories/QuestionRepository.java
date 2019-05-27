package com.interlink.quiz.quizzes.repositories;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.quizzes.rowMappers.QuestionRowMapper;
import com.interlink.quiz.quizzes.services.TopicService;
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
public class QuestionRepository {
    private final SessionFactory sessionFactory;
    private final TopicService topicService;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final QuestionRowMapper questionRowMapper;

    @Autowired
    public QuestionRepository(SessionFactory sessionFactory,
                              TopicService topicService,
                              NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                              QuestionRowMapper questionRowMapper) {
        this.sessionFactory = sessionFactory;
        this.topicService = topicService;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.questionRowMapper = questionRowMapper;
    }

    public void saveQuestion(Question question) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(question);
        transaction.commit();
    }

    public List<Question> getQuestionFromTopic(String nameOfTopic) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String sql = "FROM Question WHERE topic_id = :topic_id";
        Query<Question> query = session.createQuery(sql);
        query.setParameter("topic_id", Objects.requireNonNull(topicService.getTopicByName(nameOfTopic)).getId());
        List<Question> questions = query.list();
        transaction.commit();
        session.close();
        return questions;
    }

    public Question getQuestionByName(String nameOfQuestion) {
        try {
            String sql = "SELECT * FROM questions WHERE name = :name";
            return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("name", nameOfQuestion), questionRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}