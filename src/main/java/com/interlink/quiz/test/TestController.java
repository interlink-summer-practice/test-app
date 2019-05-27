package com.interlink.quiz.test;

import com.interlink.quiz.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getPage(Model model) {
        model.addAttribute("list", testService.listTopics());
        return "index.html";
    }

    @RequestMapping(value = "/{titleOfTopic}", method = RequestMethod.GET)
    public String getFirstTest(@PathVariable String titleOfTopic, Model model) {
        model.addAttribute("list", testService.getTopic(titleOfTopic));
        return "index.html";
    }
}