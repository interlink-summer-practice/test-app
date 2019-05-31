package com.interlink.quiz.service;

import com.interlink.quiz.object.QuizResult;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.User;
import com.interlink.quiz.object.dto.QuizResultDto;
import com.interlink.quiz.repository.QuizResultRepository;
import com.interlink.quiz.repository.QuizSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizResultService {

    private final QuizResultRepository quizResultRepository;
    private final QuizSessionRepository quizSessionRepository;

    @Autowired
    public QuizResultService(QuizResultRepository quizResultRepository,
                             QuizSessionRepository quizSessionRepository) {
        this.quizResultRepository = quizResultRepository;
        this.quizSessionRepository = quizSessionRepository;
    }

    public List<QuizResult> getQuizResult(QuizSession quizSession) {
        List<QuizResult> quizResults = new ArrayList<>(getQuizAnswersBySession(quizSession));
        quizResults.add(getPercentRightQuizAnswer(quizSession));
        return quizResults;
    }

    public List<QuizResultDto> getHistoryOfQuizzesByUser(User user) {
        List<QuizResultDto> result = new ArrayList<>();
        List<QuizSession> quizSessions = quizSessionRepository.getQuizSessionsByUserId(user);
        for (QuizSession quizSession : quizSessions) {
            QuizResultDto quizResultDto = new QuizResultDto();
            quizResultDto.setQuizSession(quizSession);
            quizResultDto.setResults(getQuizResult(quizSession));
            result.add(quizResultDto);
        }
        return result;
    }

    private List<QuizResult> getQuizAnswersBySession(QuizSession quizSession) {
        return quizResultRepository.getQuizAnswersBySession(quizSession)
                .stream()
                .peek(qr -> qr.setResult(
                        qr.getNumberOfCorrectAnswers() * 100.0 / qr.getNumberOfAnswers()))
                .collect(Collectors.toList());
    }

    private QuizResult getPercentRightQuizAnswer(QuizSession quizSession) {
        QuizResult quizResult = quizResultRepository.getPercentRightQuizAnswer(quizSession);
        quizResult.setResult(quizResult.getNumberOfCorrectAnswers() * 100.0 / quizResult.getNumberOfAnswers());
        return quizResult;
    }
}
