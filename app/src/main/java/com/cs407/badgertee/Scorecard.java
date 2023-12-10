package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Scorecard extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);
        Intent intent = getIntent();
        HashMap<String, ArrayList<Integer>> playerScores = (HashMap<String, ArrayList<Integer>>) intent.getSerializableExtra("hashMap");

        ArrayList<String> displayScores = new ArrayList<>();

        for (String playerName : playerScores.keySet()) {
            ArrayList<Integer> scores = playerScores.get(playerName);
            StringBuilder playerScoreString = new StringBuilder(playerName + ": ");
            for (int score : scores) {
                playerScoreString.append(score).append(", ");
            }
            displayScores.add(playerScoreString.toString());
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayScores);
        ListView listView = (ListView) findViewById(R.id.scorecardListView);

        listView.setAdapter(adapter);
    }

    public void navBack(View view){
        Intent intent = getIntent();
        HashMap<String, ArrayList<Integer>> playerScores = (HashMap<String, ArrayList<Integer>>) intent.getSerializableExtra("hashMap");
        int currentHole = (int) intent.getSerializableExtra("currentHole");
        String selectedPlayerOption = intent.getStringExtra("selectedPlayerOption");
        String selectedGameTypeOption = intent.getStringExtra("selectedGameTypeOption");
        String selectedCourse = intent.getStringExtra("selectedCourse");

        intent = new Intent(this, RangeFinder.class);
        intent.putExtra("hashMap", playerScores);
        intent.putExtra("currentHole", currentHole);
        intent.putExtra("selectedPlayerOption", selectedPlayerOption);
        intent.putExtra("selectedGameTypeOption", selectedGameTypeOption);
        intent.putExtra("selectedCourse", selectedCourse);

        startActivity(intent);
    }
}