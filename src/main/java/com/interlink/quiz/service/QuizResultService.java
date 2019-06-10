package com.interlink.quiz.service;

import com.interlink.quiz.object.*;
import com.interlink.quiz.object.dto.AccountDto;
import com.interlink.quiz.object.dto.QuizResultDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public AccountDto getHistoryOfQuizzesByUser(Long userId) {
        if (userId == null) return new AccountDto();

        User user = userRepository.getUserById(userId);
        AccountDto accountDto = new AccountDto();
        accountDto.setFirstName(user.getFirstName());
        accountDto.setLastName(user.getFirstName());

        List<QuizResultDto> results = new ArrayList<>();
        for (QuizSession quizSession : quizSessionRepository.getQuizSessionsByUserId(user)) {
            results.add(createQuizResultDto(quizSession));
        }
        accountDto.setResults(results);

        return accountDto;
    }

    public List<TopicResult> getStatisticByTopics(Long userId) {
        if (userId == null) return Collections.emptyList();
        User user = userRepository.getUserById(userId);
        Map<Topic, Long> resultByTopics = new HashMap<>();
        List<QuizSession> quizSessions = quizSessionRepository.getQuizSessionsByUserId(user);
        for (QuizSession quizSession : quizSessions) {
            if (isDone(quizSession)) {
                for (Topic topic : quizSession.getTopics()) {
                    if (!resultByTopics.containsKey(topic)) {
                        resultByTopics.put(
                                topic,
                                quizAnswerRepository.getCountOfRightAnswerBySessionAndTopic(quizSession, topic)
                        );
                    } else {
                        Long currentResult = resultByTopics.get(topic);
                        Long newResult = quizAnswerRepository.getCountOfRightAnswerBySessionAndTopic(quizSession, topic);
                        if (newResult > currentResult) {
                            resultByTopics.put(topic, newResult);
                        }
                    }
                }
            }
        }

        List<TopicResult> result = new ArrayList<>();
        for (Topic topic : resultByTopics.keySet()) {
            TopicResult topicResult = new TopicResult();
            topicResult.setTopic(topic);
            topicResult.setResult(resultByTopics.get(topic) * 100
                    / questionRepository.getQuestionsByTopic(topic, "Просте").size());
            result.add(topicResult);
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
        quizResultDto.setDate(quizSession.getDate());
        quizResultDto.setTopics(quizSession.getTopics());
        quizResultDto.setCountOfQuestions(quizAnswerRepository.getCountOfQuestionBySession(quizSession));
        quizResultDto.setCountOfCorrectAnswers(quizAnswerRepository.getCountOfRightAnswerBySession(quizSession));
        quizResultDto.setPercentOfPassingQuiz(quizResultDto.getCountOfCorrectAnswers() * 100.0 / quizResultDto.getCountOfQuestions());

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

    private boolean isDone(QuizSession quizSession) {
        List<Topic> topics = quizSession.getTopics();
        long countOfQuestions = topics.stream()
                .mapToLong(topic -> questionRepository.getCountByTopicAndDifficulty(quizSession.getDifficulty(), topic))
                .sum();

        return countOfQuestions == Math.toIntExact(quizAnswerRepository.getCountOfPassedQuestions(quizSession));
    }
}