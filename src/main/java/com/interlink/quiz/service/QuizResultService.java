package com.interlink.quiz.service;

import com.interlink.quiz.object.*;
import com.interlink.quiz.object.dto.QuizResultDto;
import com.interlink.quiz.repository.QuizResultRepository;
import com.interlink.quiz.repository.QuizSessionRepository;
import com.interlink.quiz.repository.UserResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizResultService {

    private final QuizResultRepository quizResultRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final UserResultRepository userResultRepository;
    private final UserService userService;

    @Autowired
    public QuizResultService(QuizResultRepository quizResultRepository,
                             QuizSessionRepository quizSessionRepository,
                             UserResultRepository userResultRepository,
                             UserService userService) {

        this.quizResultRepository = quizResultRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.userResultRepository = userResultRepository;
        this.userService = userService;
    }

    public QuizResult getQuizResult(QuizSession quizSession, UserDetails userDetails) {
        QuizResult quizResult = new QuizResult();
        Integer mark = quizSessionRepository.getMarkByQuizSesion(quizSession);
        quizResult.setMark(mark);
        if(userDetails != null) {
            saveUserResult(quizSession, mark);
        }
        List<TopicResult> topicResults = new ArrayList<>(getQuizAnswersBySession(quizSession));
        quizResult.setPercentOfPassingQuiz(getPercentRightQuizAnswer(quizSession).getResult());
        quizResult.setTopicResults(topicResults);

        return quizResult;
    }

    public List<QuizResultDto> getHistoryOfQuizzesByUser(UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<QuizResultDto> result = new ArrayList<>();
        List<QuizSession> quizSessions = quizSessionRepository.getQuizSessionsByUserId(user);
        for (QuizSession quizSession : quizSessions) {
            QuizResultDto quizResultDto = new QuizResultDto();
            quizResultDto.setQuizSession(quizSession);
            quizResultDto.setQuizResult(getQuizResult(quizSession, userDetails));
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

    private List<TopicResult> getQuizAnswersBySession(QuizSession quizSession) {
        return quizResultRepository.getQuizAnswersBySession(quizSession)
                .stream()
                .peek(qr -> qr.setResult(
                        qr.getNumberOfCorrectAnswers() * 100.0 / qr.getNumberOfAnswers()))
                .collect(Collectors.toList());
    }

    private TopicResult getPercentRightQuizAnswer(QuizSession quizSession) {
        TopicResult topicResult = quizResultRepository.getPercentRightQuizAnswer(quizSession);
        topicResult.setResult(topicResult.getNumberOfCorrectAnswers() * 100.0 / topicResult.getNumberOfAnswers());
        return topicResult;
    }
}
