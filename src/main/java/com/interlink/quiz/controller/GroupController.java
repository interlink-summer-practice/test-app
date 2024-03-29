package com.interlink.quiz.controller;

import com.interlink.quiz.auth.security.JwtTokenProvider;
import com.interlink.quiz.object.dto.GroupDto;
import com.interlink.quiz.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

    private final GroupService groupService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public GroupController(GroupService groupService,
                           JwtTokenProvider jwtTokenProvider) {

        this.groupService = groupService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/group")
    public ResponseEntity<?> getQuestionsToGroup(@RequestBody GroupDto groupDto,
                                                 @RequestHeader(value = "auth-token", required = false) String token) {

        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        groupDto.setCuratorId(userId.intValue());

        return new ResponseEntity<>(groupService.save(groupDto), HttpStatus.OK);
    }
}
