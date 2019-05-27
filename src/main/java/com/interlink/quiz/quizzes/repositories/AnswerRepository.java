package com.interlink.quiz.quizzes.repositories;

import com.interlink.quiz.object.Answer;
import com.interlink.quiz.quizzes.services.QuestionService;
import com.interlink.quiz.quizzes.services.TopicService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AnswerRepository {
    private final SessionFactory sessionFactory;
    private final TopicService topicService;
    private final QuestionService questionService;

    @Autowired
    public AnswerRepository(SessionFactory sessionFactory,
                            TopicService topicService,
                            QuestionService questionService) {
        this.sessionFactory = sessionFactory;
        this.topicService = topicService;
        this.questionService = questionService;
    }

    public void saveAnswer(Answer answer) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(answer);
        transaction.commit();
    }

    public List<Answer> getAnswerFromQuestion(String nameOfTopic, String nameOfQuestion) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String sql = "FROM Answer AS a, Question AS q WHERE a.question_id = :question_id AND q.topic_id = :topic_id";
        Query<Answer> query = session.createQuery(sql);
        query.setParameter("question_id", questionService.getQuestionByName(nameOfQuestion).getId());
        query.setParameter("topic_id", Objects.requireNonNull(topicService.getTopicByName(nameOfTopic)).getId());
        List<Answer> topics = query.list();
        transaction.commit();
        session.close();
        return topics;
    }
}