package com.interlink.quiz.service;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.object.dto.QuestionDto;
import com.interlink.quiz.object.dto.QuizDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.repository.QuestionRepository;
import com.interlink.quiz.repository.QuizAnswerRepository;
import com.interlink.quiz.repository.QuizSessionRepository;
import com.interlink.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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

    public QuizDto getQuestions(Topic[] topicsArray,
                                UserDetails userDetails,
                                HttpSession httpSession) {

        QuizDto quizDto = new QuizDto();
        List<QuizSession> quizSessions;
        List<Topic> topics = Arrays.stream(topicsArray).collect(toList());
        if (userDetails == null) {
            quizSessions = quizSessionRepository.getQuizSessionBySessionId(httpSession.getId());
        } else {
            quizSessions = quizSessionRepository.getQuizSessionsByUserId(
                    userRepository.getUserByEmail(userDetails.getUsername()));
        }
        for (QuizSession quizSession : quizSessions) {
            if (isAlreadyPassedQuiz(topics, quizSession)) {
                if (isDoneQuiz(topics, quizSession)) {
                    quizDto.setPassed(true);
                    quizDto.setQuizSession(quizSession);
                    quizDto.setQuestions(getQuestionsByTopics(topics));
                    return quizDto;
                } else {
                    quizDto.setQuizSession(quizSession);
                    quizDto.setQuestions(getNotPassedQuestionsByTopics(topics, quizSession));
                    quizDto.setCountOfPassedQuestions(quizAnswerRepository.getCountOfPassedQuestions(quizSession));

                    return quizDto;
                }
            }
        }

        quizDto.setQuizSession(createNewQuizSession(httpSession, userDetails, topics));
        quizDto.setQuestions(getQuestionsByTopics(topics));
        return quizDto;
    }

    public void updateResultsOfPassedQuiz(QuizSessionDto quizSessionDto) {
        QuizSession quizSession = quizSessionRepository.getQuizSessionById(quizSessionDto.getId());
        quizSession.setDate(LocalDateTime.now().toString());
        quizSessionRepository.updateQuizSession(quizSession);
        quizAnswerRepository.deleteQuizAnswersByQuizSession(quizSession);
    }

    private List<QuestionDto> getQuestionsByTopics(List<Topic> topics) {
        List<Question> questions = new ArrayList<>();
        for (Topic topic : topics) {
            questions.addAll(questionRepository.getQuestionsByTopic(topic));
        }

        return questions.stream()
                .map(this::createQuestionDto)
                .collect(toList());
    }

    private List<QuestionDto> getNotPassedQuestionsByTopics(List<Topic> topics, QuizSession quizSession) {
        List<Question> questions = new ArrayList<>();
        for (Topic topic : topics) {
            questions.addAll(questionRepository.getNotPassedQuestionsByTopic(topic, quizSession));
        }

        return questions.stream()
                .map(this::createQuestionDto)
                .collect(toList());
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

    private boolean isAlreadyPassedQuiz(List<Topic> selectedTopics, QuizSession quizSession) {
        if (quizSession == null) return false;
        if (selectedTopics.size() != quizSession.getTopics().size()) return false;
        for (Topic sessionTopic : quizSession.getTopics()) {
            if (!selectedTopics.contains(sessionTopic)) {
                return false;
            }
        }
        return true;
    }

    private boolean isDoneQuiz(List<Topic> topics, QuizSession quizSession) {
        List<QuestionDto> questionsByTopics = getQuestionsByTopics(topics);
        List<QuizAnswer> answers = quizAnswerRepository.getAnswersByQuizSession(quizSession);
        Set<Question> questions = answers.stream().map(QuizAnswer::getQuestion).collect(toSet());
        return questionsByTopics.size() == questions.size();
    }

    private QuestionDto createQuestionDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setName(question.getName());
        questionDto.setDifficulty(question.getDifficulty());
        questionDto.setTopic(question.getTopic());
        questionDto.setAnswers(question.getAnswers());

        return questionDto;
    }
}
