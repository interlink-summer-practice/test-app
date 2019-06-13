package com.interlink.quiz.service;

import com.interlink.quiz.object.*;
import com.interlink.quiz.object.dto.GroupDto;
import com.interlink.quiz.object.dto.GroupResultDto;
import com.interlink.quiz.object.dto.MemberResultDto;
import com.interlink.quiz.repository.GroupRepository;
import com.interlink.quiz.repository.QuizSessionRepository;
import com.interlink.quiz.repository.UserRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final QuestionService questionService;
    private final QuizResultService quizResultService;

    @Autowired
    public GroupService(GroupRepository groupRepository,
                        UserRepository userRepository,
                        QuizSessionRepository quizSessionRepository,
                        QuestionService questionService,
                        QuizResultService quizResultService) {

        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.questionService = questionService;
        this.quizResultService = quizResultService;
    }

    public Group saveGroup(GroupDto groupDto) {
        return groupRepository.saveGroup(createGroup(groupDto));
    }

    public List<GroupResultDto> getResultsByGroups(Long userId) throws IOException {
        List<Group> groups = groupRepository.getGroupsByCurator(userId.intValue());
        List<GroupResultDto> result = new ArrayList<>();
        for (Group group : groups) {
            result.add(getResultByGroup(group));
        }

        return result;
    }

    private GroupResultDto getResultByGroup(Group group) throws IOException {
        if (group.getQuizUrl() == null) return null;

        byte[] decode = Base64.getDecoder().decode(group.getQuizUrl());
        String test = new String(decode);
        CuratorQuiz curatorQuiz = new ObjectMapper().readValue(test, CuratorQuiz.class);
        List<Topic> topics = Arrays.stream(curatorQuiz.getTopics()).collect(toList());
        List<String> difficulties = Arrays.stream(curatorQuiz.getDifficulties()).collect(toList());

        GroupResultDto groupResultDto = new GroupResultDto();
        groupResultDto.setGroupName(group.getName());
        groupResultDto.setQuizUrl(group.getQuizUrl());

        List<MemberResultDto> results = new ArrayList<>();
        for (QuizSession quizSession : group.getQuizSessions()) {
            if (questionService.isAlreadyPassedQuiz(topics, quizSession, difficulties)
                    && questionService.isDoneQuiz(quizSession)) {

                results.add(createMemberResultDto(quizSession));
            }
        }

        groupResultDto.setResults(results);

        return groupResultDto;
    }

    private Group createGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        group.setCurator(userRepository.getUserById((long) groupDto.getCuratorId()));

        return group;
    }

    private MemberResultDto createMemberResultDto(QuizSession quizSession) {
        MemberResultDto memberResultDto = new MemberResultDto();
        memberResultDto.setFirstName(quizSession.getUser().getFirstName());
        memberResultDto.setLastName(quizSession.getUser().getLastName());
        memberResultDto.setEmail(quizSession.getUser().getEmail());
        memberResultDto.setQuizResultDto(quizResultService.createQuizResultDto(quizSession));

        return memberResultDto;
    }
}
