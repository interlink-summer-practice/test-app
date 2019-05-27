package com.interlink.quiz.quizzes.rowMappers;

import com.interlink.quiz.object.Answer;
import com.interlink.quiz.object.Question;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AnswerRowMapper implements RowMapper<Answer> {

    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Answer answer = new Answer();
        answer.setId(rs.getInt("id"));
        answer.setName(rs.getString("name"));
        answer.setQuestion((Question) rs.getObject("question_id"));
        answer.setRight(rs.getBoolean("right"));
        return answer;
    }
}