package com.interlink.quiz.service;

import com.interlink.quiz.object.Group;
import com.interlink.quiz.object.dto.GroupDto;
import com.interlink.quiz.repository.GroupRepository;
import com.interlink.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository,
                        UserRepository userRepository) {

        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group saveGroup(GroupDto groupDto) {
        return groupRepository.saveGroup(createGroup(groupDto));
    }

    public void addMemberToGroup(Long groupId, Long userId) {
        Group group = groupRepository.getGroupById(groupId);
        group.getMembers().add(userRepository.getUserById(userId));
        groupRepository.addMemberToGroup(group);
    }

    private Group createGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        group.setQuizUrl(groupDto.getQuizUrl());
        group.setUser(userRepository.getUserById(groupDto.getCuratorId()));

        return group;
    }
}
