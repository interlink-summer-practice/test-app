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
    private final QuizAnswerRepository quizAnswerRepository;
    private final UserRepository userRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuizResultService(
            QuizSessionRepository quizSessionRepository,
            UserResultRepository userResultRepository,
            QuizAnswerRepository quizAnswerRepository,
            UserRepository userRepository,
            QuestionService questionService,
            QuestionRepository questionRepository) {

        this.quizSessionRepository = quizSessionRepository;
        this.userResultRepository = userResultRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.userRepository = userRepository;
        this.questionService = questionService;
        this.questionRepository = questionRepository;
    }

    public QuizResult getQuizResult(QuizSessionDto quizSessionDto, Long userId) {
        QuizResult quizResult = new QuizResult();
        QuizSession quizSession = quizSessionRepository.getQuizSessionById(quizSessionDto.getId());
        quizResult.setMark(quizSessionRepository.getMarkByQuizSession(quizSession));
        quizResult.setCountOfQuestion(quizSession.getQuestions().size());
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
        accountDto.setLastName(user.getLastName());

        List<QuizResultDto> results = new ArrayList<>();
        for (QuizSession quizSession : quizSessionRepository.getQuizSessionsByUser(user)) {
            results.add(createQuizResultDto(quizSession));
        }
        accountDto.setResults(results);

        return accountDto;
    }

    public List<TopicResult> getTopicsResultByUser(Long userId){
        if (userId == null) return new ArrayList<>();

        User user = userRepository.getUserById(userId);

        Map<Topic, TopicResult> topicResults = new HashMap<>();
        List<QuizSession> quizSessions = quizSessionRepository.getQuizSessionsByUser(user);
        for (QuizSession quizSession : quizSessions) {
            for (Topic topic : quizSession.getTopics()) {
                if (!topicResults.containsKey(topic)) {
                    TopicResult topicResult = new TopicResult();
                    topicResult.setTopic(topic);
                    int countOfQuestionByTopic = questionRepository.getCountOfQuestionByTopic(user, topic);
                    int countOfRightAnswerByTopic = quizAnswerRepository.getCountOfRightAnswerByTopic(user, topic);
                    double resultByTopic = countOfRightAnswerByTopic * 100.0 / countOfQuestionByTopic;
                    topicResult.setNumberOfCorrectAnswers(countOfRightAnswerByTopic);
                    topicResult.setNumberOfQuestions(countOfQuestionByTopic);
                    topicResult.setResult(resultByTopic);
                    topicResults.put(topic, topicResult);
                }
            }
        }

        return new ArrayList<>(topicResults.values());
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

    public QuizResultDto createQuizResultDto(QuizSession quizSession) {
        QuizResultDto quizResultDto = new QuizResultDto();
        quizResultDto.setQuizSessionId(quizSession.getId());
        quizResultDto.setDate(quizSession.getDate());
        quizResultDto.setTopics(quizSession.getTopics());
        quizResultDto.setCountOfQuestions(Integer.toUnsignedLong(quizSession.getQuestions().size()));
        quizResultDto.setCountOfCorrectAnswers(quizAnswerRepository.getCountOfRightAnswerBySession(quizSession));
        quizResultDto.setPercentOfPassingQuiz(quizResultDto.getCountOfCorrectAnswers() * 100.0 / quizResultDto.getCountOfQuestions());

        if (!questionService.isDoneQuiz(quizSession)) {
            quizResultDto.setPassed(false);
        }

        return quizResultDto;
    }

    private TopicResult createTopicResult(QuizSession quizSession, Topic topic) {
        TopicResult topicResult = new TopicResult();
        topicResult.setTopic(topic);
        topicResult.setNumberOfQuestions(
                quizSession.getQuestions()
                        .stream()
                        .filter(question -> question.getTopic() == topic)
                        .count());
        topicResult.setNumberOfCorrectAnswers(
                quizAnswerRepository.getCountOfRightAnswerBySessionAndTopic(quizSession, topic));
        topicResult.setResult(topicResult.getNumberOfCorrectAnswers() * 100.0 / topicResult.getNumberOfQuestions());

        return topicResult;
    }
}