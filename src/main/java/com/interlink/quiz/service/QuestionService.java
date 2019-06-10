package com.interlink.quiz.service;

import com.interlink.quiz.object.*;
import com.interlink.quiz.object.dto.QuestionDto;
import com.interlink.quiz.object.dto.QuizDto;
import com.interlink.quiz.object.dto.QuizSessionDto;
import com.interlink.quiz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        if (difficulty.equals("Середнє") || difficulty.equals("Складне")) return new QuizDto();

        if (isPresentQuestionsWithThisDifficulty(topics, difficulty)) {
            QuizDto quizDto = new QuizDto();
            List<QuestionDto> questions = getQuestionsByTopics(topics, difficulty);
            quizDto.setCountOfQuestionsInQuiz(questions.size());
            List<QuizSession> quizSessions;
            if (userId == null) {
                quizSessions = quizSessionRepository.getQuizSessionBySessionId(httpSession.getId());
            } else {
                quizSessions = quizSessionRepository.getQuizSessionsByUserId(
                        userRepository.getUserById(userId));
            }
            for (QuizSession quizSession : quizSessions) {
                if (isAlreadyPassedQuiz(topics, quizSession, difficulty)) {
                    if (quizSession.getQuestions().size() != questions.size()) {
                        quizDto.setExistNewQuestions(true);
                    }
                    if (isDoneQuiz(quizSession)) {
                        quizDto.setPassed(true);
                        quizDto.setQuizSession(quizSession);
                        quizDto.setQuestions(questions);

                        return quizDto;
                    } else {
                        quizDto.setQuizSession(quizSession);
                        quizDto.setQuestions(getNotPassedQuestionsByTopics(topics, quizSession));
                        quizDto.setCountOfPassedQuestions(quizAnswerRepository.getCountOfPassedQuestions(quizSession));

                        return quizDto;
                    }
                }
            }

            quizDto.setQuizSession(createNewQuizSession(
                    httpSession,
                    userId,
                    topics,
                    difficulty,
                    questions.stream()
                            .map(this::createQuestionFromQuestionDto)
                            .collect(Collectors.toList()))
            );
            quizDto.setQuestions(questions);

            return quizDto;
        }

        return new QuizDto();
    }

    public QuizDto getQuestionsToGroup(CuratorQuiz curatorQuiz,
                                       Long userId,
                                       HttpSession httpSession) {

        List<Topic> topics = Arrays.stream(curatorQuiz.getTopics()).collect(toList());
        String difficulty = Stream.of(curatorQuiz.getDifficulties())
                .sorted()
                .reduce((x, y) -> x + "+" + y)
                .orElse("");

        QuizDto quizDto = new QuizDto();
        List<QuestionDto> questions = getQuestionsByTopicsAndDifficulties(topics, curatorQuiz.getDifficulties());
        quizDto.setCountOfQuestionsInQuiz(questions.size());
        List<QuizSession> quizSessions = quizSessionRepository.getQuizSessionsByUserId(userRepository.getUserById(userId));
        for (QuizSession quizSession : quizSessions) {
            if (isAlreadyPassedGroupTest(topics, difficulty, quizSession)) {
                if (isDoneQuiz(quizSession)) {
                    quizDto.setPassed(true);
                    quizDto.setQuizSession(quizSession);
                    quizDto.setQuestions(questions);

                    return quizDto;
                } else {
                    quizDto.setQuizSession(quizSession);
                    quizDto.setQuestions(getNotPassedQuestionsByTopics(topics, quizSession));
                    quizDto.setCountOfPassedQuestions(quizAnswerRepository.getCountOfPassedQuestions(quizSession));

                    return quizDto;
                }
            }
        }

        quizDto.setQuizSession(createNewQuizSession(
                httpSession,
                userId,
                topics,
                difficulty,
                questions.stream()
                        .map(this::createQuestionFromQuestionDto)
                        .collect(Collectors.toList()))
        );
        quizDto.setQuestions(questions);

        return quizDto;
    }

    public void addQuestionsInQuizSession(QuizSessionDto quizSessionDto) {
        QuizSession quizSession = quizSessionRepository.getQuizSessionById(quizSessionDto.getId());
        quizSession.setQuestions(
                getQuestionsByTopics(quizSession.getTopics(), "Просте").stream()
                .map(this::createQuestionFromQuestionDto)
                .collect(Collectors.toList())
        );
        quizSessionRepository.updateQuizSession(quizSession);
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

    private List<QuestionDto> getQuestionsByTopicsAndDifficulties(List<Topic> topics, String[] difficulties) {
        List<Question> questions = new ArrayList<>();
        for (Topic topic : topics) {
            for (String difficulty : difficulties) {
                questions.addAll(questionRepository.getQuestionsByTopic(topic, difficulty));
            }
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

    private boolean isAlreadyPassedGroupTest(List<Topic> topics,
                                             String difficulties,
                                             QuizSession quizSession) {

        if (quizSession == null) return false;
        if (topics.size() != quizSession.getTopics().size()) return false;
        if (!difficulties.equals(quizSession.getDifficulty())) return false;

//        for (Topic sessionTopic : quizSession.getTopics()) {
//            if (!selectedTopics.contains(sessionTopic)) {
//                return false;
//            }
//        }
        return true;
    }

    private boolean isPresentQuestionsWithThisDifficulty(List<Topic> topics, String difficulty) {
        long sum = topics.stream()
                .mapToLong(topic -> questionRepository.getCountByTopicAndDifficulty(difficulty, topic))
                .sum();

        return sum > 0;
    }

    private boolean isDoneQuiz(QuizSession quizSession) {
        List<QuizAnswer> answers = quizAnswerRepository.getAnswersByQuizSession(quizSession);
        Set<Question> questions = answers.stream().map(QuizAnswer::getQuestion).collect(toSet());

        return quizSession.getQuestions().size() == questions.size();
    }

    private QuizSession createNewQuizSession(HttpSession httpSession,
                                             Long userId,
                                             List<Topic> topics,
                                             String difficulty,
                                             List<Question> questions) {

        QuizSession newQuizSession = new QuizSession();
        newQuizSession.setSessionId(httpSession.getId());
        newQuizSession.setDate(LocalDateTime.now().toString());
        newQuizSession.setTopics(topics);
        newQuizSession.setDifficulty(difficulty);
        newQuizSession.setQuestions(questions);
        if (userId != null) {
            newQuizSession.setUser(userRepository.getUserById(userId));
        }
        quizSessionRepository.createQuizSession(newQuizSession);

        return newQuizSession;
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

    private Question createQuestionFromQuestionDto(QuestionDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setName(questionDto.getName());
        question.setDifficulty(questionDto.getDifficulty());
        question.setTopic(questionDto.getTopic());
        question.setAnswers(questionDto.getAnswers());

        return question;
    }
}
