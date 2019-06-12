package com.interlink.quiz.controller;

import com.interlink.quiz.auth.security.JwtTokenProvider;
import com.interlink.quiz.object.TopicResult;
import com.interlink.quiz.object.dto.AccountDto;
import com.interlink.quiz.object.dto.GroupDto;
import com.interlink.quiz.service.GroupService;
import com.interlink.quiz.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    private final QuizResultService quizResultService;
    private final GroupService groupService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AccountController(QuizResultService quizResultService,
                             GroupService groupService,
                             JwtTokenProvider jwtTokenProvider) {
        this.quizResultService = quizResultService;
        this.groupService = groupService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/account/groups")
    public ResponseEntity<?> getGroupsForCurator(@RequestHeader(value = "auth-token", required = false) String token) {
        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(groupService.getGroupsByCurator(userId.intValue()), HttpStatus.OK);
    }

    @GetMapping("/account")
    public AccountDto getTestHistory(@RequestHeader(value = "auth-token", required = false) String token) {
        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }

        return quizResultService.getHistoryOfQuizzesByUser(userId);
    }

    @GetMapping("/account/statistic")
    public List<TopicResult> getStatisticByTopics(@RequestHeader(value = "auth-token", required = false) String token) {
        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }

        return quizResultService.getTopicsResultByUser(userId);
    }
}
