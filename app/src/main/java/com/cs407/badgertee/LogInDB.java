package com.cs407.badgertee;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class LogInDB {
    static SQLiteDatabase sqLiteDatabase;

    public LogInDB(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }
    public void createTable(){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users "+
                "(id INTEGER PRIMARY KEY, password TEXT, username TEXT, gender TEXT, email TEXT)");
    }

    public boolean saveUser(String username, String password, String gender, String email){
        createTable();
        if (isUsernameExists(username)) {
            return false;
        }

        sqLiteDatabase.execSQL("INSERT INTO users (username, password, gender, email) VALUES (?,?,?,?)",
                new String[]{username, password, gender, email});

        return true;
    }

    private boolean isUsernameExists(String username) {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT username FROM users WHERE username=?", new String[]{username});

        boolean result = cursor.moveToFirst();

        cursor.close();

        return result;
    }

    public boolean checkLogin(String username, String password){
        createTable();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT username FROM users WHERE username=? AND password=?", new String[]{username, password});

        boolean result = cursor.moveToFirst();

        cursor.close();

        return result;
    }

    public boolean deleteUser(String username) {
        createTable();

        try {
            sqLiteDatabase.execSQL("DELETE FROM users WHERE username=?",new String[]{username});
            return true;
        } catch (Exception e) {
            Log.e("LogInDB", "Error deleting user: " + e.getMessage());
            return false;
        }
    }

}
