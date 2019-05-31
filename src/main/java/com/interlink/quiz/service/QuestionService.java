package com.interlink.quiz.service;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.object.dto.QuestionsDto;
import com.interlink.quiz.repository.QuestionRepository;
import com.interlink.quiz.repository.QuizAnswerRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final QuizAnswerRepository quizAnswerRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           QuizSessionRepository quizSessionRepository,
                           QuizAnswerRepository quizAnswerRepository) {

        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
    }

    public QuestionsDto getQuestions(Topic[] topicsArray,
                                     UserDetails userDetails,
                                     HttpSession httpSession) {

        QuestionsDto questionsDto = new QuestionsDto();
        List<QuizSession> quizSessions;
        List<Topic> topics = Arrays.stream(topicsArray).collect(Collectors.toList());
        if (userDetails == null) {
            quizSessions = quizSessionRepository.getQuizSessionBySessionId(httpSession.getId());
        } else {
            quizSessions = quizSessionRepository.getQuizSessionsByUserId(
                    userRepository.getUserByEmail(userDetails.getUsername()));
        }
        for (QuizSession quizSession : quizSessions) {
            if (isAlreadyPassedQuiz(topics, quizSession)) {
                if (isDoneQuiz(topics, quizSession)) {
                    quizSession.setDate(LocalDateTime.now().toString());

                    quizSessionRepository.updateQuizSession(quizSession);
                    quizAnswerRepository.deleteQuizAnswersByQuizSession(quizSession);

                    questionsDto.setQuizSession(quizSession);
                    questionsDto.setQuestions(getQuestionsByTopics(topics));
                    return questionsDto;
                } else {
                    questionsDto.setQuizSession(quizSession);
                    questionsDto.setQuestions(getNotPassedQuestionsByTopics(topics, quizSession));
                    return questionsDto;
                }
            }
        }

        questionsDto.setQuizSession(createNewQuizSession(httpSession, userDetails, topics));
        questionsDto.setQuestions(getQuestionsByTopics(topics));
        return questionsDto;
    }

    private List<Question> getQuestionsByTopics(List<Topic> topics) {
        List<Question> questions = new ArrayList<>();
        for (Topic topic : topics) {
            questions.addAll(questionRepository.getQuestionsByTopic(topic));
        }

        return questions;
    }

    private QuizSession createNewQuizSession(HttpSession httpSession,
                                             UserDetails userDetails,
                                             List<Topic> topics) {

        QuizSession newQuizSession = new QuizSession();
        newQuizSession.setSessionId(httpSession.getId());
        newQuizSession.setDate(LocalDateTime.now().toString());
        newQuizSession.setTopics(topics);
        if (userDetails != null) {
            newQuizSession.setUser(userRepository.getUserByEmail(userDetails.getUsername()));
        }
        quizSessionRepository.createQuizSession(newQuizSession);

        return newQuizSession;
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
        List<QuizAnswer> answers = quizAnswerRepository.getAnswersByQuizSession(quizSession);
        Set<Question> questions = answers.stream().map(QuizAnswer::getQuestion).collect(Collectors.toSet());
        return questionsByTopics.size() == questions.size();
    }
}
