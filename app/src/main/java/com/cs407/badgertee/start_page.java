package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class start_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        Intent intent = getIntent();

        String str = intent.getStringExtra("message");

    }

    public void navPastScores(View view){

        Intent intent = new Intent(this, allPastScores.class);
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
}