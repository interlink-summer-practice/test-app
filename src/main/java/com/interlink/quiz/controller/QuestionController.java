package com.interlink.quiz.controller;

import com.interlink.quiz.auth.security.JwtTokenProvider;
import com.interlink.quiz.object.CuratorQuiz;
import com.interlink.quiz.object.dto.FilteredQuizDto;
import com.interlink.quiz.object.dto.QuizDto;
import com.interlink.quiz.service.GroupService;
import com.interlink.quiz.service.QuestionService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;

@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final GroupService groupService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public QuestionController(QuestionService questionService,
                              GroupService groupService,
                              JwtTokenProvider jwtTokenProvider) {

        this.questionService = questionService;
        this.groupService = groupService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/questions")
    public QuizDto getQuestions(@RequestBody FilteredQuizDto filteredQuizDto,
                                @RequestHeader(value = "auth-token", required = false) String token,
                                HttpSession httpSession) {

        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }

        return questionService.getQuestions(
                filteredQuizDto.getTopics(),
                userId,
                httpSession,
                filteredQuizDto.getDifficulty()
        );
    }

    @GetMapping("/questions/{url}")
    public ResponseEntity<?> getQuestionsToGroup(@PathVariable String url,
                                                 @RequestHeader(value = "auth-token", required = false) String token,
                                                 HttpSession httpSession) throws IOException {

        Long userId = null;
        if (!token.isEmpty()) {
            userId = jwtTokenProvider.getUserIdFromJWT(token);
        }
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        byte[] decode = Base64.getDecoder().decode(url);
        String test = new String(decode);
        CuratorQuiz curatorQuiz = new ObjectMapper().readValue(test, CuratorQuiz.class);
        QuizDto quizDto = questionService.getQuestionsToGroup(curatorQuiz, userId, httpSession);
        groupService.addMemberToGroup(curatorQuiz.getGroupId(), userId, url, quizDto.getQuizSession());

        return new ResponseEntity<>(quizDto, HttpStatus.OK);
    }
}
