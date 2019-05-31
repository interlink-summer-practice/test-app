package com.interlink.quiz.controller;

import com.interlink.quiz.csv.CsvParserService;
import com.interlink.quiz.object.QuizAnswer;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.object.dto.QuestionsDto;
import com.interlink.quiz.service.QuestionService;
import com.interlink.quiz.service.QuizAnswersService;
import com.interlink.quiz.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@RestController
public class QuizController {

    private final TopicService topicService;
    private final QuestionService questionService;
    private final QuizAnswersService quizAnswersService;
    private final CsvParserService csvParserService;

    @Autowired
    public QuizController(TopicService topicService,
                          QuestionService questionService,
                          QuizAnswersService quizAnswersService, CsvParserService csvParserService) {

        this.topicService = topicService;
        this.questionService = questionService;
        this.quizAnswersService = quizAnswersService;
        this.csvParserService = csvParserService;
    }

    @GetMapping("/get_topics")
    public List<Topic> getTopics() {
        return topicService.getTopics();
    }

    @GetMapping("/get_questions")
    public QuestionsDto getQuestions(@RequestParam Topic[] topics,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     HttpSession httpSession) {

        return questionService.getQuestions(topics, userDetails, httpSession);
    }

    @PostMapping("/save_quiz_answer")
    public String saveQuizAnswer(@RequestParam QuizAnswer quizAnswer) {
        quizAnswersService.saveQuizAnswer(quizAnswer);

        return "OK";
    }

    @GetMapping("/import")
    public void saveQuizFromCsvFile(@RequestBody File file) {
        csvParserService.parseCsvFileToDataBase(file);
    }
}
