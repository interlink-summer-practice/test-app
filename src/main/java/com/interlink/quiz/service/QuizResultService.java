package com.interlink.quiz.service;

import com.interlink.quiz.object.*;
import com.interlink.quiz.object.dto.QuizResultDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizResultService {

    private final QuizSessionRepository quizSessionRepository;
    private final UserResultRepository userResultRepository;
    private final QuestionRepository questionRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final UserService userService;

    @Autowired
    public QuizResultService(
            QuizSessionRepository quizSessionRepository,
            UserResultRepository userResultRepository,
            QuestionRepository questionRepository,
            QuizAnswerRepository quizAnswerRepository,
            UserService userService) {

        this.quizSessionRepository = quizSessionRepository;
        this.userResultRepository = userResultRepository;
        this.questionRepository = questionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.userService = userService;
    }

    public QuizResult getQuizResult(QuizSessionDto quizSessionDto, UserDetails userDetails) {
        QuizResult quizResult = new QuizResult();
        QuizSession quizSession = quizSessionRepository.getQuizSessionById(quizSessionDto.getId());
        int mark = quizSessionRepository.getMarkByQuizSession(quizSession);
        quizResult.setMark(mark);
        if (userDetails != null) {
            saveUserResult(quizSession, mark);
        }
        quizResult.setCountOfQuestion(quizAnswerRepository.getCountOfQuestionBySession(quizSession));
        quizResult.setCountOfCorrectAnswers(quizAnswerRepository.getCountOfRightAnswerBySession(quizSession));
        quizResult.setPercentOfPassingQuiz(
                quizResult.getCountOfCorrectAnswers() * 100.0 / quizResult.getCountOfQuestion());
        quizResult.setTopicResults(getTopicResultsBySessions(quizSession));

        return quizResult;
    }

    public List<QuizResultDto> getHistoryOfQuizzesByUser(Long userId) {
        User user = userService.getUserById(userId);
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
            TopicResult topicResult = new TopicResult();
            topicResult.setTopic(topic);
            topicResult.setNumberOfQuestions(questionRepository.getCountOfQuestionByTopic(topic));
            topicResult.setNumberOfCorrectAnswers(quizAnswerRepository.getCountOfRightAnswerBySessionAndTopic(quizSession, topic));
            topicResult.setResult(topicResult.getNumberOfCorrectAnswers() * 100.0 / topicResult.getNumberOfQuestions());
            topicResultList.add(topicResult);
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
        return quizResultDto;
    }
}