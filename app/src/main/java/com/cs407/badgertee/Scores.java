package com.cs407.badgertee;


public class Scores {
    private String date;
    private String username;
    private String roundScore;
    private String courseName;
    public Scores(String date, String username, String roundScore, String courseName){
        this.date = date;
        this.username = username;
        this.roundScore = roundScore;
        this.courseName = courseName;
    }

    public String getRoundScore() {
        return roundScore;
    }

    public String getDate() {
        return date;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getUsername() {
        return username;
    }
}
