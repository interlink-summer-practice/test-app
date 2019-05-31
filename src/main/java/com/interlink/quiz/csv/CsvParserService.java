package com.interlink.quiz.csv;

import com.interlink.quiz.object.Answer;
import com.interlink.quiz.object.Question;
import com.interlink.quiz.object.Topic;
import com.interlink.quiz.repository.AnswerRepository;
import com.interlink.quiz.repository.QuestionRepository;
import com.interlink.quiz.repository.TopicRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvParserService {

    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public CsvParserService(TopicRepository topicRepository, QuestionRepository questionRepository,
                            AnswerRepository answerRepository) {
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void parseCsvFileToDataBase(File file) {
        List<CSVQuiz> csvQuizzes = parseCsvFileToListOfQuizzes(file);

        for (CSVQuiz csvQuiz : csvQuizzes) {
            List<Answer> answers = new ArrayList<>();
            for (String a : csvQuiz.getAnswers()) {
                Answer answer = answerRepository.getAnswerByName(a);
                if (answer == null) {
                    answer = new Answer();
                    answer.setName(a);
                    answerRepository.saveAnswer(answer);
                }
                answers.add(answer);
            }

            Answer rightAnswer = answerRepository.getAnswerByName(csvQuiz.getRightAnswer());

            Topic topic = topicRepository.getTopicByName(csvQuiz.getTopicName());
            if (topic == null) {
                topic = new Topic();
                topic.setName(csvQuiz.getTopicName());
                topicRepository.saveTopic(topic);
            }

            Question question = new Question();
            question.setName(csvQuiz.getQuestionName());
            question.setDifficulty(csvQuiz.getDifficulty());
            question.setMark(csvQuiz.getMark());
            question.setTopic(topic);
            question.setRightAnswer(rightAnswer);
            question.setAnswers(answers);
            questionRepository.saveQuestion(question);
        }
    }

    private List<CSVQuiz> parseCsvFileToListOfQuizzes(File file) {
        List<CSVQuiz> result = Collections.emptyList();
        try (Reader reader = Files.newBufferedReader(file.toPath())) {
            CsvToBean<CSVQuiz> csvToBean = new CsvToBeanBuilder<CSVQuiz>(reader)
                    .withType(CSVQuiz.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            result = csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.stream()
                .peek(e -> e.setAnswers(
                        Arrays.asList(
                                e.getFirstAnswer(),
                                e.getSecondAnswer(),
                                e.getThirdAnswer(),
                                e.getFourthAnswer())))
                .collect(Collectors.toList());
    }
}
