package com.interlink.quiz.object;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "quiz_session")
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "session_id")
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String date;

    @ElementCollection
    @CollectionTable(name = "difficulty_in_quiz_session", joinColumns = @JoinColumn(name = "quiz_session_id"))
    @Column(name = "difficulty")
    private List<String> difficulties;

    @ManyToMany
    @JoinTable(
            name = "selected_topics",
            joinColumns = {@JoinColumn(name = "quiz_session_id")},
            inverseJoinColumns = {@JoinColumn(name = "topic_id")}
    )
    private List<Topic> topics;

    @ManyToMany
    @JoinTable(
            name = "question_in_quiz_session",
            joinColumns = {@JoinColumn(name = "quiz_session_id")},
            inverseJoinColumns = {@JoinColumn(name = "question_id")}
    )
    private List<Question> questions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<String> getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(List<String> difficulties) {
        this.difficulties = difficulties;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}