package com.interlink.quiz.service;

import com.interlink.quiz.object.*;
import com.interlink.quiz.object.dto.QuizResultDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuizResultService {

    private final QuizSessionRepository quizSessionRepository;
    private final UserResultRepository userResultRepository;
    private final QuestionRepository questionRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuizResultService(
            QuizSessionRepository quizSessionRepository,
            UserResultRepository userResultRepository,
            QuestionRepository questionRepository,
            QuizAnswerRepository quizAnswerRepository,
            UserRepository userRepository) {

        this.quizSessionRepository = quizSessionRepository;
        this.userResultRepository = userResultRepository;
        this.questionRepository = questionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.userRepository = userRepository;
    }

    public QuizResult getQuizResult(QuizSessionDto quizSessionDto, Long userId) {
        QuizResult quizResult = new QuizResult();
        QuizSession quizSession = quizSessionRepository.getQuizSessionById(quizSessionDto.getId());
        quizResult.setMark(quizSessionRepository.getMarkByQuizSession(quizSession));
        quizResult.setCountOfQuestion(quizAnswerRepository.getCountOfQuestionBySession(quizSession));
        quizResult.setCountOfCorrectAnswers(quizAnswerRepository.getCountOfRightAnswerBySession(quizSession));
        quizResult.setPercentOfPassingQuiz(
                quizResult.getCountOfCorrectAnswers() * 100.0 / quizResult.getCountOfQuestion());
        quizResult.setTopicResults(getTopicResultsBySessions(quizSession));

        if (userId != null) {
            saveUserResult(quizSession, quizResult.getMark());
        }

        return quizResult;
    }

    public List<QuizResultDto> getHistoryOfQuizzesByUser(Long userId) {
        if (userId == null) return Collections.emptyList();
        User user = userRepository.getUserById(userId);
        List<QuizResultDto> result = new ArrayList<>();
        for (QuizSession quizSession : quizSessionRepository.getQuizSessionsByUserId(user)) {
            result.add(createQuizResultDto(quizSession));
        }

        return result;
    }

    private void saveUserResult(QuizSession quizSession, int mark) {
        UserResult userResult = new UserResult();
        userResult.setQuizSession(quizSession);
        userResult.setResult(mark);
        userResultRepository.saveUserResult(userResult);
    }

    private List<TopicResult> getTopicResultsBySessions(QuizSession quizSession) {
        List<TopicResult> topicResultList = new ArrayList<>();
        for (Topic topic : quizSession.getTopics()) {
            topicResultList.add(createTopicResult(quizSession, topic));
        }

        return topicResultList;
    }

    private QuizResultDto createQuizResultDto(QuizSession quizSession) {
        QuizResultDto quizResultDto = new QuizResultDto();
        quizResultDto.setQuizSessionId(quizSession.getId());
        quizResultDto.setFirstName(quizSession.getUser().getFirstName());
        quizResultDto.setLastName(quizSession.getUser().getLastName());
        quizResultDto.setDate(quizSession.getDate());
        quizResultDto.setCountOfQuestions(quizAnswerRepository.getCountOfQuestionBySession(quizSession));
        quizResultDto.setCountOfCorrectAnswers(quizAnswerRepository.getCountOfRightAnswerBySession(quizSession));
        quizResultDto.setPercentOfPassingQuiz(
                quizResultDto.getCountOfCorrectAnswers() * 100.0 / quizResultDto.getCountOfQuestions());
        quizResultDto.setTopics(quizSession.getTopics());
        return quizResultDto;
    }

    private TopicResult createTopicResult(QuizSession quizSession, Topic topic) {
        TopicResult topicResult = new TopicResult();
        topicResult.setTopic(topic);
        topicResult.setNumberOfQuestions(
                questionRepository.getCountByTopicAndDifficulty(quizSession.getDifficulty(), topic));
        topicResult.setNumberOfCorrectAnswers(
                quizAnswerRepository.getCountOfRightAnswerBySessionAndTopic(quizSession, topic));
        topicResult.setResult(topicResult.getNumberOfCorrectAnswers() * 100.0 / topicResult.getNumberOfQuestions());

        return topicResult;
    }
}