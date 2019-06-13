package com.interlink.quiz.service;

import com.interlink.quiz.object.*;
import com.interlink.quiz.object.dto.AccountDto;
import com.interlink.quiz.object.dto.QuizResultDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        QuizSession quizSession = quizSessionRepository.findById(quizSessionDto.getId());
        quizResult.setMark(quizSessionRepository.getMarkByQuizSessionId(quizSession.getId()).intValue());
        quizResult.setCountOfQuestion(quizSession.getQuestions().size());
        quizResult.setCountOfCorrectAnswers(quizAnswerRepository.countOfRightAnswerByQuizSessionId(quizSession.getId()));
        quizResult.setPercentOfPassingQuiz(
                quizResult.getCountOfCorrectAnswers() * 100.0 / quizResult.getCountOfQuestion());
        quizResult.setTopicResults(getTopicResultsBySessions(quizSession));

        if (userId != null) {
            saveUserResult(quizSession, quizResult.getMark());
        }

        return quizResult;
    }

    public AccountDto getHistoryOfQuizzesByUser(int userId) {
        if (userId == 0) return new AccountDto();

        User user = userRepository.findById(userId);
        AccountDto accountDto = new AccountDto();
        accountDto.setFirstName(user.getFirstName());
        accountDto.setLastName(user.getLastName());

        List<QuizResultDto> results = new ArrayList<>();
        for (QuizSession quizSession : quizSessionRepository.findAllByUserId(user.getId())) {
            results.add(createQuizResultDto(quizSession));
        }
        accountDto.setResults(results);

        return accountDto;
    }

    public List<TopicResult> getTopicsResultByUser(Long userId) {
        if (userId == null) return new ArrayList<>();

        User user = userRepository.findById(userId.intValue());

        Map<Topic, TopicResult> topicResults = new HashMap<>();
        List<QuizSession> quizSessions = quizSessionRepository.findAllByUserId(user.getId());
        for (QuizSession quizSession : quizSessions) {
            for (Topic topic : quizSession.getTopics()) {
                if (!topicResults.containsKey(topic)) {
                    TopicResult topicResult = new TopicResult();
                    topicResult.setTopic(topic);
                    int countOfQuestionByTopic =
                            quizAnswerRepository.findAllByUserIdAndTopicId(user.getId(), topic.getId()).size();
                    int countOfRightAnswerByTopic =
                            quizAnswerRepository.findAllRightAnswerByUserIdAndTopicId(user.getId(), topic.getId()).size();
                    double resultByTopic = countOfRightAnswerByTopic * 100.0 / countOfQuestionByTopic;
                    topicResult.setNumberOfCorrectAnswers(countOfRightAnswerByTopic);
                    topicResult.setNumberOfQuestions(countOfQuestionByTopic);
                    topicResult.setResult(resultByTopic);
                    topicResults.put(topic, topicResult);
                }
            }
        }

        return topicResults.values().stream().sorted().collect(Collectors.toList());
    }

    private void saveUserResult(QuizSession quizSession, int mark) {
        UserResult userResult = new UserResult();
        userResult.setQuizSession(quizSession);
        userResult.setResult(mark);
        userResultRepository.save(userResult);
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
        quizResultDto.setCountOfCorrectAnswers(quizAnswerRepository.countOfRightAnswerByQuizSessionId(quizSession.getId()));
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
                quizAnswerRepository.countOfRightAnswerByQuizSessionIdAndTopicId(quizSession.getId(), topic.getId()));
        topicResult.setResult(topicResult.getNumberOfCorrectAnswers() * 100.0 / topicResult.getNumberOfQuestions());

        return topicResult;
    }
}