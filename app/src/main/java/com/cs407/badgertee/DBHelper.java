package com.cs407.badgertee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper {
    static SQLiteDatabase sqLiteDatabase;

    public DBHelper(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }
    public void createTable(){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS pastScores "+
                "(id INTEGER PRIMARY KEY, scoreId INTEGER, username TEXT, date TEXT, courseName TEXT, roundScore TEXT, numPlayers TEXT)");
    }

    public ArrayList<Scores> readScores(String username){
        createTable();
        Log.i("INFO", "%"+username+"%");
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM pastScores WHERE username LIKE ?",
                new String[]{"%"+username+"%"});
        int dateIndex = c.getColumnIndex("date");
        int courseNameIndex = c.getColumnIndex("courseName");
        int roundScoreIndex = c.getColumnIndex("roundScore");
        int numPlayersIndex = c.getColumnIndex("numPlayers");
        c.moveToFirst();
        ArrayList<Scores> scoresList = new ArrayList<>();
        while (!c.isAfterLast()){
            String courseName = c.getString(courseNameIndex);
            String date = c.getString(dateIndex);
            String roundScore = c.getString(roundScoreIndex);
            String numPlayers =c.getString(numPlayersIndex);
            Scores score = new Scores(date, username, roundScore, courseName, numPlayers);
            scoresList.add(score);
            c.moveToNext();
        }
        c.close();
        return scoresList;
    }
    public void saveScore(String username, String courseName, String date, String roundScore, String numPlayers){
        createTable();
        sqLiteDatabase.execSQL("INSERT INTO pastScores (username, date, courseName, roundScore, numPlayers) VALUES (?,?,?,?,?)",
                new String[]{username,date,courseName,roundScore, numPlayers});
    }

    public void updateScore(String username, String courseName, String date, String roundScore, String numPlayers){
        createTable();
        Scores score  = new Scores(date, username, courseName, roundScore, numPlayers);
        sqLiteDatabase.execSQL("UPDATE scores set roundScore=?, date=? where courseName=? and username =?",
                new String[]{roundScore,date,courseName,username, numPlayers});
    }
    public void deleteScore(String roundScore, String courseName) {
        createTable();
        String date = "";
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT date FROM pastScores WHERE roundScore = ?",
                new String[]{roundScore});
        if (cursor.moveToNext()){
            date = cursor.getString(0);
        }
        sqLiteDatabase.execSQL("DELETE FROM pastScores WHERE roundScore = ? AND date = ?", new String[]{roundScore, date});
        cursor.close();
    }

    public void deleteScoresByUsername(String username) {
        createTable();
        sqLiteDatabase.execSQL("DELETE FROM pastScores WHERE username = ?", new String[]{username});
    }
}