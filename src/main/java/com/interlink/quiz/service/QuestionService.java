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

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final UserResultRepository userResultRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           QuizSessionRepository quizSessionRepository,
                           QuizAnswerRepository quizAnswerRepository,
                           UserResultRepository userResultRepository,
                           GroupRepository groupRepository) {

        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.userResultRepository = userResultRepository;
        this.groupRepository = groupRepository;
    }

    public QuizDto getQuestionsByUrl(Topic[] topics,
                                     Long userId,
                                     HttpSession httpSession) {

        return getQuestions(topics, userId, httpSession, "Просте");
    }

    public QuizDto getQuestions(Topic[] topicsArray,
                                Long userId,
                                HttpSession httpSession,
                                String difficulty) {

        List<Topic> topics = Arrays.stream(topicsArray).collect(toList());

        if (difficulty.equals("Середнє") || difficulty.equals("Складне")) return new QuizDto();
        QuizDto quizDto = new QuizDto();
        if (isPresentQuestionsWithThisDifficulty(topics, Collections.singletonList(difficulty))) {
            List<QuestionDto> questions = getQuestionsByTopics(topics, Collections.singletonList(difficulty));
            quizDto.setCountOfQuestionsInQuiz(questions.size());
            List<QuizSession> quizSessions;
            if (userId == null) {
                quizSessions = quizSessionRepository.getQuizSessionBySessionId(httpSession.getId());
            } else {
                quizSessions = quizSessionRepository.getQuizSessionsByUser(
                        userRepository.getUserById(userId));
            }
            for (QuizSession quizSession : quizSessions) {
                if (isAlreadyPassedQuiz(topics, quizSession, Collections.singletonList(difficulty))) {
                    if (quizSession.getQuestions().size() != questions.size()) {
                        quizDto.setExistNewQuestions(true);
                    }
                    if (isDoneQuiz(quizSession)) {
                        quizDto.setPassed(true);
                        quizDto.setQuizSession(createQuizSessionDto(quizSession));
                        quizDto.setQuestions(questions);

                        return quizDto;
                    } else {
                        quizDto.setQuizSession(createQuizSessionDto(quizSession));
                        quizDto.setQuestions(getNotPassedQuestionsByTopics(topics, quizSession));
                        quizDto.setCountOfPassedQuestions(quizAnswerRepository.getCountOfPassedQuestions(quizSession));

                        return quizDto;
                    }
                }
            }

            quizDto.setQuizSession(createQuizSessionDto(
                    createNewQuizSession(
                            httpSession,
                            userId,
                            topics,
                            Collections.singletonList(difficulty),
                            questions.stream()
                                    .map(this::createQuestionFromQuestionDto)
                                    .collect(toList()))
                    )
            );
            quizDto.setQuestions(questions);
            return quizDto;
        }
        quizDto.setQuizSession(new QuizSessionDto());
        return quizDto;
    }

    public QuizDto getQuestionsToGroup(CuratorQuiz curatorQuiz,
                                       Long userId,
                                       HttpSession httpSession,
                                       Long groupId,
                                       String quizUrl) {

        List<Topic> topics = Arrays.stream(curatorQuiz.getTopics()).collect(toList());
        List<String> difficulties = Arrays.stream(curatorQuiz.getDifficulties()).collect(toList());
        User user = userRepository.getUserById(userId);
        QuizDto quizDto = new QuizDto();
        if (isPresentQuestionsWithThisDifficulty(topics, difficulties)) {
            List<QuestionDto> questions = getQuestionsByTopicsAndDifficulties(topics, difficulties);
            quizDto.setCountOfQuestionsInQuiz(questions.size());
            List<QuizSession> quizSessions = quizSessionRepository.getQuizSessionsByGroupMember(user);
            for (QuizSession quizSession : quizSessions) {
                if (isAlreadyPassedQuiz(topics, quizSession, difficulties)) {
                    if (isDoneQuiz(quizSession)) {
                        quizDto.setPassed(true);
                        quizDto.setQuizSession(createQuizSessionDto(quizSession));
                        quizDto.setQuestions(questions);

                        return quizDto;
                    } else {
                        quizDto.setQuizSession(createQuizSessionDto(quizSession));
                        quizDto.setQuestions(getNotPassedQuestionsByTopics(topics, quizSession));
                        quizDto.setCountOfPassedQuestions(quizAnswerRepository.getCountOfPassedQuestions(quizSession));

                        return quizDto;
                    }
                }
            }

            QuizSession newQuizSession = createNewQuizSession(
                    httpSession,
                    userId,
                    topics,
                    difficulties,
                    questions.stream()
                            .map(this::createQuestionFromQuestionDto)
                            .collect(toList()));

            quizDto.setQuizSession(createQuizSessionDto(newQuizSession));
            quizDto.setQuestions(questions);

            Group group = groupRepository.getGroupById(groupId);
            group.getMembers().add(user);
            group.setQuizUrl(quizUrl);
            groupRepository.addMemberToGroup(group);
            groupRepository.setQuizSessionForMember(group, user, newQuizSession);

            return quizDto;
        }

        quizDto.setQuizSession(new QuizSessionDto());

        return quizDto;
    }

    public void addQuestionsInQuizSession(QuizSessionDto quizSessionDto) {
        QuizSession quizSession = quizSessionRepository.getQuizSessionById(quizSessionDto.getId());
        quizSession.setQuestions(
                getQuestionsByTopics(quizSession.getTopics(), quizSession.getDifficulties()).stream()
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

    private List<QuestionDto> getQuestionsByTopics(List<Topic> topics, List<String> difficulties) {
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

    private List<QuestionDto> getQuestionsByTopicsAndDifficulties(List<Topic> topics, List<String> difficulties) {
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
            for (String difficulty : quizSession.getDifficulties()) {
                questions.addAll(questionRepository.getNotPassedQuestionsByTopic(topic, quizSession, difficulty));
            }
        }

        return questions.stream()
                .map(this::createQuestionDto)
                .collect(toList());
    }

    public boolean isAlreadyPassedQuiz(List<Topic> selectedTopics,
                                       QuizSession quizSession,
                                       List<String> difficulty) {

        if (quizSession == null) return false;
        if (quizSession.getDifficulties().size() != difficulty.size()) return false;
        if (!quizSession.getDifficulties().containsAll(difficulty)) return false;
        if (selectedTopics.size() != quizSession.getTopics().size()) return false;

        return quizSession.getTopics().containsAll(selectedTopics);
    }

    private boolean isPresentQuestionsWithThisDifficulty(List<Topic> topics, List<String> difficulties) {
        long sum = 0;
        for (Topic topic : topics) {
            for (String difficulty : difficulties) {
                sum += questionRepository.getCountByTopicAndDifficulty(difficulty, topic);
            }
        }

        return sum > 0;
    }

    public boolean isDoneQuiz(QuizSession quizSession) {
        List<QuizAnswer> answers = quizAnswerRepository.getAnswersByQuizSession(quizSession);
        Set<Question> questions = answers.stream().map(QuizAnswer::getQuestion).collect(toSet());

        return quizSession.getQuestions().size() == questions.size();
    }

    private QuizSession createNewQuizSession(HttpSession httpSession,
                                             Long userId,
                                             List<Topic> topics,
                                             List<String> difficulties,
                                             List<Question> questions) {

        QuizSession newQuizSession = new QuizSession();
        newQuizSession.setSessionId(httpSession.getId());
        newQuizSession.setDate(LocalDateTime.now().toString());
        newQuizSession.setTopics(topics);
        newQuizSession.setDifficulties(difficulties);
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

    private QuizSessionDto createQuizSessionDto(QuizSession quizSession) {
        QuizSessionDto quizSessionDto = new QuizSessionDto();
        quizSessionDto.setId(quizSession.getId());

        return quizSessionDto;
    }
}
