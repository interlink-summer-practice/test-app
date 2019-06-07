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
import java.util.Collections;
import java.util.List;

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
        List<QuizLine> lines = parseCsvFileToListOfQuizzes(file);

        for (QuizLine quizLine : lines) {
            String[] answersArray = quizLine.getAnswers().split("\\n");
            List<Answer> answers = new ArrayList<>();
            for (String s : answersArray) {
                Answer answer = answerRepository.getAnswerByName(s);
                if (answer == null) {
                    answer = new Answer();
                    answer.setName(s);
                    answerRepository.saveAnswer(answer);
                }
                answers.add(answer);
            }

            Answer rightAnswer = answerRepository.getAnswerByName(quizLine.getRightAnswer());

            Topic topic = topicRepository.getTopicByName(quizLine.getTopic());
            if (topic == null) {
                topic = new Topic();
                topic.setName(quizLine.getTopic());
                topicRepository.saveTopic(topic);
            }

            Question question = new Question();
            question.setName(quizLine.getQuestion());
            question.setDifficulty(quizLine.getDifficulty());

            switch (quizLine.getDifficulty()) {
                case "Просте":
                    question.setMark(1);
                    break;
                case "Середнє":
                    question.setMark(2);
                    break;
                case "Складне":
                    question.setMark(3);
                    break;
            }

            question.setTopic(topic);
            question.setRightAnswer(rightAnswer);
            question.setAnswers(answers);
            questionRepository.saveQuestion(question);
        }
    }

    private List<QuizLine> parseCsvFileToListOfQuizzes(File file) {
        List<QuizLine> result = Collections.emptyList();
        try (Reader reader = Files.newBufferedReader(file.toPath())) {
            CsvToBean<QuizLine> csvToBean = new CsvToBeanBuilder<QuizLine>(reader)
                    .withType(QuizLine.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            result = csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
