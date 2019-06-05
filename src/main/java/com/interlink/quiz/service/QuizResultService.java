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

    private final QuizResultRepository quizResultRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final UserResultRepository userResultRepository;
    private final QuestionRepository questionRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final UserService userService;

    @Autowired
    public QuizResultService(QuizResultRepository quizResultRepository,
                             QuizSessionRepository quizSessionRepository,
                             UserResultRepository userResultRepository,
                             QuestionRepository questionRepository,
                             QuizAnswerRepository quizAnswerRepository,
                             UserService userService) {

        this.quizResultRepository = quizResultRepository;
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
        if(userDetails != null) {
            saveUserResult(quizSession, mark);
        }
        quizResult.setPercentOfPassingQuiz(getPercentRightQuizAnswer(quizSession).getResult());

        List<TopicResult> topicResultList = new ArrayList<>();
        for (Topic topic : quizSession.getTopics()) {
            TopicResult topicResult = new TopicResult();
            topicResult.setNumberOfQuestions(questionRepository.getCountOfQuestionByTopic(topic));
            topicResult.setNumberOfCorrectAnswers(quizAnswerRepository.getCountOfRightAnswerBySessionAndTopic(quizSession, topic));
            topicResult.setResult(topicResult.getNumberOfCorrectAnswers() * 100.0 / topicResult.getNumberOfQuestions());
            topicResultList.add(topicResult);
        }
        quizResult.setTopicResults(topicResultList);

        return quizResult;
    }

    public List<QuizResultDto> getHistoryOfQuizzesByUser(UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<QuizResultDto> result = new ArrayList<>();
        List<QuizSession> quizSessions = quizSessionRepository.getQuizSessionsByUserId(user);
        for (QuizSession quizSession : quizSessions) {
            QuizResultDto quizResultDto = new QuizResultDto();
            quizResultDto.setQuizSession(quizSession);
            QuizSessionDto quizSessionDto = new QuizSessionDto();
            quizSessionDto.setId(quizSession.getId());
            quizResultDto.setQuizResult(getQuizResult(quizSessionDto, userDetails));
            result.add(quizResultDto);
        }
        return result;
    }

    private void saveUserResult(QuizSession quizSession, int mark) {
        UserResult userResult = new UserResult();
        userResult.setQuizSession(quizSession);
        userResult.setResult(mark);
        userResultRepository.saveUserResult(userResult);
    }

    private TopicResult getPercentRightQuizAnswer(QuizSession quizSession) {
        TopicResult topicResult = quizResultRepository.getPercentRightQuizAnswer(quizSession);
        topicResult.setResult(topicResult.getNumberOfCorrectAnswers() * 100.0 / topicResult.getNumberOfQuestions());
        return topicResult;
    }
}