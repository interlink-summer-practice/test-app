package com.interlink.quiz.object.dto;

public class MemberResultDto {

    private String firstName;
    private String lastName;
    private String email;
    private String date;
    private QuizResultDto quizResultDto;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public QuizResultDto getQuizResultDto() {
        return quizResultDto;
    }

    public void setQuizResultDto(QuizResultDto quizResultDto) {
        this.quizResultDto = quizResultDto;
    }
}
