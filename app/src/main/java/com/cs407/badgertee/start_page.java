package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class start_page extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        Intent intent = getIntent();

        username = intent.getStringExtra("message");

    }

    public void navPastScores(View view){


        Intent intent = new Intent(this, allPastScores.class);
        intent.putExtra("message", username);

        startActivity(intent);

    }

    public void navCourseSetup(View view){

        Intent intent = new Intent(this, CourseSetup.class);
        startActivity(intent);

    }
    public void logout(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.badgertee", MODE_PRIVATE);
        String userName= sharedPreferences.getString("username", "");
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteAccount(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.badgertee", MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", "");

        LogInDB logInDB = new LogInDB(openOrCreateDatabase("users", MODE_PRIVATE, null));
        boolean isUserDeleted = logInDB.deleteUser(userName);
        if (isUserDeleted) {
            sharedPreferences.edit().clear().apply();
            Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error deleting account", Toast.LENGTH_SHORT).show();
        }
    }
}