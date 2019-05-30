package com.interlink.quiz.service;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.repository.QuestionRepository;
import com.interlink.quiz.repository.QuizAnswersRepository;
import com.interlink.quiz.repository.QuizSessionRepository;
import com.interlink.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final QuizAnswersRepository quizAnswersRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           QuizSessionRepository quizSessionRepository,
                           QuizAnswersRepository quizAnswersRepository) {

        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.quizAnswersRepository = quizAnswersRepository;
    }

    public List<Question> getQuestions(Topic[] topicsArray,
                                       UserDetails userDetails,
                                       HttpSession httpSession) {

        List<Topic> topics = Arrays.stream(topicsArray).collect(Collectors.toList());
        if (userDetails == null) {
            List<QuizSession> quizSessions = quizSessionRepository.getQuizSessionBySessionId(httpSession.getId());
            for (QuizSession quizSession : quizSessions) {
                if (isAlreadyPassedQuiz(topics, quizSession)) {
                    if (isDoneQuiz(topics, quizSession)) {
                        return getQuestionsByTopics(topics);
                    } else {
                        return getNotPassedQuestionsByTopics(topics, quizSession);
                    }
                }
            }
            QuizSession newQuizSession = new QuizSession();
            newQuizSession.setSessionId(httpSession.getId());
            newQuizSession.setDate(LocalDateTime.now().toString());
            newQuizSession.setTopics(topics);
            quizSessionRepository.createQuizSession(newQuizSession);

            return getQuestionsByTopics(topics);
        } else {
            ///for user
        }
        return getQuestionsByTopics(topics);
    }

    private List<Question> getQuestionsByTopics(List<Topic> topics) {
        List<Question> questions = new ArrayList<>();
        for (Topic topic : topics) {
            questions.addAll(questionRepository.getQuestionsByTopic(topic));
        }

        return questions;
    }

    private List<Question> getNotPassedQuestionsByTopics(List<Topic> topics, QuizSession quizSession) {
        List<Question> questions = new ArrayList<>();
        for (Topic topic : topics) {
            questions.addAll(questionRepository.getNotPassedQuestionsByTopic(topic, quizSession));
        }

        return questions;
    }

    private boolean isAlreadyPassedQuiz(List<Topic> selectedTopics, QuizSession quizSession) {
        if (quizSession == null) return false;
        for (Topic sessionTopic : quizSession.getTopics()) {
            if (!selectedTopics.contains(sessionTopic)) {
                return false;
            }
        }
        return true;
    }

    private boolean isDoneQuiz(List<Topic> topics, QuizSession quizSession) {
        List<Question> questionsByTopics = getQuestionsByTopics(topics);
        List<QuizAnswer> answers = quizAnswersRepository.getAnswersByQuizSession(quizSession);
        return questionsByTopics.size() == answers.size();
    }
}
