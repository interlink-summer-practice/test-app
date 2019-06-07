package com.interlink.quiz.service;

import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.object.dto.QuestionDto;
import com.interlink.quiz.object.dto.QuizDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final UserResultRepository userResultRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           QuizSessionRepository quizSessionRepository,
                           QuizAnswerRepository quizAnswerRepository,
                           UserResultRepository userResultRepository) {

        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.userResultRepository = userResultRepository;
    }

    public QuizDto getQuestions(Topic[] topicsArray,
                                Long userId,
                                HttpSession httpSession,
                                String difficulty) {

        List<Topic> topics = Arrays.stream(topicsArray).collect(toList());
        if (isPresentQuestionsWithThisDifficulty(topics, difficulty)) {
            QuizDto quizDto = new QuizDto();
            List<QuizSession> quizSessions;
            if (userId == null) {
                quizSessions = quizSessionRepository.getQuizSessionBySessionId(httpSession.getId());
            } else {
                quizSessions = quizSessionRepository.getQuizSessionsByUserId(
                        userRepository.getUserById(userId));
            }
            for (QuizSession quizSession : quizSessions) {
                if (isAlreadyPassedQuiz(topics, quizSession, difficulty)) {
                    if (isDoneQuiz(topics, quizSession, difficulty)) {
                        quizDto.setPassed(true);
                        quizDto.setQuizSession(quizSession);
                        quizDto.setQuestions(getQuestionsByTopics(topics, difficulty));

                        return quizDto;
                    } else {
                        quizDto.setQuizSession(quizSession);
                        quizDto.setQuestions(getNotPassedQuestionsByTopics(topics, quizSession));
                        
                        return quizDto;
                    }
                }
            }

            quizDto.setQuizSession(createNewQuizSession(httpSession, userId, topics, difficulty));
            quizDto.setQuestions(getQuestionsByTopics(topics, difficulty));
            return quizDto;
        }

        return null;
    }

    public void updateResultsOfPassedQuiz(QuizSessionDto quizSessionDto, Long userId) {
        QuizSession quizSession = quizSessionRepository.getQuizSessionById(quizSessionDto.getId());
        quizSession.setDate(LocalDateTime.now().toString());
        quizSessionRepository.updateQuizSession(quizSession);
        quizAnswerRepository.deleteQuizAnswersByQuizSession(quizSession);
        if (userId == null) {
            userResultRepository.deleteUserResult(quizSession);
        }
    }

    private List<QuestionDto> getQuestionsByTopics(List<Topic> topics, String difficulty) {
        List<Question> questions = new ArrayList<>();
        for (Topic topic : topics) {
            questions.addAll(questionRepository.getQuestionsByTopic(topic, difficulty));
        }

        return questions.stream()
                .map(this::createQuestionDto)
                .collect(toList());
    }

    private List<QuestionDto> getNotPassedQuestionsByTopics(List<Topic> topics,
                                                            QuizSession quizSession) {

        List<Question> questions = new ArrayList<>();
        for (Topic topic : topics) {
            questions.addAll(questionRepository.getNotPassedQuestionsByTopic(topic, quizSession));
        }

        return questions.stream()
                .map(this::createQuestionDto)
                .collect(toList());
    }

    private QuizSession createNewQuizSession(HttpSession httpSession,
                                             Long userId,
                                             List<Topic> topics,
                                             String difficulty) {

        QuizSession newQuizSession = new QuizSession();
        newQuizSession.setSessionId(httpSession.getId());
        newQuizSession.setDate(LocalDateTime.now().toString());
        newQuizSession.setTopics(topics);
        newQuizSession.setDifficulty(difficulty);
        if (userId != null) {
            newQuizSession.setUser(userRepository.getUserById(userId));
        }
        quizSessionRepository.createQuizSession(newQuizSession);

        return newQuizSession;
    }

    private boolean isAlreadyPassedQuiz(List<Topic> selectedTopics,
                                        QuizSession quizSession,
                                        String currentDifficulty) {

        if (quizSession == null) return false;
        if (!quizSession.getDifficulty().equals(currentDifficulty)) return false;
        if (selectedTopics.size() != quizSession.getTopics().size()) return false;
        for (Topic sessionTopic : quizSession.getTopics()) {
            if (!selectedTopics.contains(sessionTopic)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPresentQuestionsWithThisDifficulty(List<Topic> topics, String difficulty) {
        long sum = topics.stream()
                .mapToLong(topic -> questionRepository.getCountByTopicAndDifficulty(difficulty, topic))
                .sum();

        return sum > 0;
    }

    private boolean isDoneQuiz(List<Topic> topics, QuizSession quizSession, String difficulty) {
        List<QuestionDto> questionsByTopics = getQuestionsByTopics(topics, difficulty);
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
