package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class pastScore extends AppCompatActivity {

    private int scoreId =-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_score);
        Intent intent = getIntent();
        int i = intent.getIntExtra("scoreId", -1);
        scoreId = i;

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.badgertee", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("pastScores", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        ArrayList<Scores> scoreClicked =dbHelper.readScores(username);
        if (scoreId != -1){
            Scores score = scoreClicked.get(scoreId);
            TextView courseNameTextView = findViewById(R.id.CourseName);
            courseNameTextView.setText(score.getCourseName());

            TextView roundDateTextView = findViewById(R.id.RoundDate);
            roundDateTextView.setText(score.getDate());


            ArrayList<String> displayScores = new ArrayList<>();
            String allScores = score.getRoundScore();
            String[] resultArray = allScores.split(",");
            displayScores.add(String.format("Round Score:%s", resultArray[0]));

            for (i=1; i<resultArray.length; i++){
                displayScores.add(String.format("Hole %d: %s\n", i, resultArray[i]));
            }

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayScores);
            ListView listView = (ListView) findViewById(R.id.scoreListView);
            listView.setAdapter(adapter);
        }
    }
    public void navAllPastScores(View view){

        Intent intent = new Intent(this, allPastScores.class);
        startActivity(intent);

    }
}