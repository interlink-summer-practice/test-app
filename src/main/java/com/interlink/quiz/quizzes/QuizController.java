package com.interlink.quiz.quizzes;

import com.interlink.quiz.quizzes.services.AnswerService;
import com.interlink.quiz.quizzes.services.QuestionService;
import com.interlink.quiz.quizzes.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizController {
    private final TopicService topicService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public QuizController(TopicService topicService,
                          QuestionService questionService,
                          AnswerService answerService) {
        this.topicService = topicService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("listOfTopic", topicService.getTopics());
        return "index.html";
    }

    @RequestMapping(value = "/{nameOfTopic}", method = RequestMethod.GET)
    public String getQuestionOfTopic(@PathVariable String nameOfTopic, @RequestParam String nameOfQuestion, Model model) {
        model.addAttribute("listOfQuestion", questionService.getQuestionFromTopic(nameOfTopic));
        model.addAttribute("listOfAnswer", answerService.getAnswerFromQuestion(nameOfTopic, nameOfQuestion));
        return "test.html";
    }
}