package com.interlink.quiz.object;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column(name = "quiz_url")
    private String quizUrl;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    private User curator;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "quiz_session_id")}
    )
    private List<QuizSession> sessions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuizUrl() {
        return quizUrl;
    }

    public void setQuizUrl(String quizUrl) {
        this.quizUrl = quizUrl;
    }

    public User getCurator() {
        return curator;
    }

    public void setCurator(User curator) {
        this.curator = curator;
    }

    public List<QuizSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<QuizSession> sessions) {
        this.sessions = sessions;
    }
}
