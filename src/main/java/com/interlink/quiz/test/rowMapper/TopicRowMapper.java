package com.interlink.quiz.test.rowMapper;

import com.interlink.quiz.object.Topic;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TopicRowMapper implements RowMapper<Topic> {

    @Override
    public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
        Topic topic = new Topic();
        topic.setId(rs.getInt("id"));
        topic.setName(rs.getString("name"));
        return topic;
    }
}