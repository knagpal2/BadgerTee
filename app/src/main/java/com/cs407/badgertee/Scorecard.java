package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Scorecard extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);
        Intent intent = getIntent();
        HashMap<String, ArrayList<Integer>> playerScores = (HashMap<String, ArrayList<Integer>>) intent.getSerializableExtra("hashMap");
        String selectedPlayerOption = intent.getStringExtra("selectedPlayerOption");

        List<String> playerOrder = Arrays.asList("Player 1", "Player 2", "Player 3", "Player 4");

        String roundScoreString = "";
        for (String playerName : playerOrder) {
            int roundScore = 0;
            if (playerScores.containsKey(playerName)) {
                ArrayList<Integer> scores = playerScores.get(playerName);
                StringBuilder playerScoreString = new StringBuilder();
                for (int score : scores) {
                    playerScoreString.append(score).append(",");
                    roundScore += score;

                }
                if (playerScoreString.length() > 0) {
                    playerScoreString.setLength(playerScoreString.length() - 1);

                }
                roundScoreString = roundScoreString + roundScore + "," + playerScoreString + ",";
            }
        }
        roundScoreString.substring(0, roundScoreString.length() - 1);

        Scores score = new Scores("","",roundScoreString,"",selectedPlayerOption);
        ArrayList<String> displayScores = new ArrayList<>();
        String allScores = score.getRoundScore();
        String[] resultArray = allScores.split(",");
        String roundScores="";
        int pos= resultArray.length/Integer.parseInt(score.getNumPlayers());
        for (int j =0; j<Integer.parseInt(score.getNumPlayers()); j++){
            roundScores+="Player "+ (1+j) +": "+resultArray[pos*j]+"   ";
        }
        displayScores.add(String.format("%s", roundScores));

        for (int j=1; j<pos; j++){
            roundScores="";
            for (int k =0; k<Integer.parseInt(score.getNumPlayers()); k++){
                roundScores+=resultArray[j+k*pos]+"                    ";
            }
            displayScores.add(String.format("Hole %d:     %s\n", j, roundScores));
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