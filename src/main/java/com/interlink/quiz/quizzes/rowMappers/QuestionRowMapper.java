package com.interlink.quiz.quizzes.rowMappers;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.Topic;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuestionRowMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();
        question.setId(rs.getInt("id"));
        question.setName(rs.getString("name"));
        question.setDifficulty(rs.getString("difficulty"));
        question.setMark(rs.getInt("mark"));
        question.setTopic((Topic) rs.getObject("topic_id"));
        return question;
    }
}