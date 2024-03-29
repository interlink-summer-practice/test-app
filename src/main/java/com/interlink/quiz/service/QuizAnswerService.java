package com.interlink.quiz.service;

import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.dto.QuizAnswerDto;
import com.interlink.quiz.repository.AnswerRepository;
import com.interlink.quiz.repository.QuestionRepository;
import com.interlink.quiz.repository.QuizAnswerRepository;
import com.interlink.quiz.repository.QuizSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizAnswerService {

    private final QuizAnswerRepository quizAnswerRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuizSessionRepository quizSessionRepository;

    @Autowired
    public QuizAnswerService(QuizAnswerRepository quizAnswerRepository,
                             AnswerRepository answerRepository,
                             QuestionRepository questionRepository,
                             QuizSessionRepository quizSessionRepository) {

        this.quizAnswerRepository = quizAnswerRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.quizSessionRepository = quizSessionRepository;
    }

    @Transactional
    public void save(QuizAnswerDto quizAnswerDto) {
        quizAnswerRepository.save(createQuizAnswer(quizAnswerDto));
    }

    private QuizAnswer createQuizAnswer(QuizAnswerDto quizAnswerDto) {
        QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setAnswer(answerRepository.findById(quizAnswerDto.getAnswerId()));
        quizAnswer.setQuestion(questionRepository.findById(quizAnswerDto.getQuestionId()));
        quizAnswer.setQuizSession(quizSessionRepository.findById(quizAnswerDto.getQuizSessionId()));

        return quizAnswer;
    }
}
