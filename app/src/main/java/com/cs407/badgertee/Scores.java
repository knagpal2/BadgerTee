package com.cs407.badgertee;


public class Scores {
    private String date;
    private String username;
    private String roundScore;
    private String courseName;
    private String numPlayers;
    public Scores(String date, String username, String roundScore, String courseName, String numPlayers){
        this.date = date;
        this.username = username;
        this.roundScore = roundScore;
        this.courseName = courseName;
        this.numPlayers = numPlayers;
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

    public String  getNumPlayers(){return numPlayers;}
}
