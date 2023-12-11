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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RoundEnd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_end);

        Intent intent = getIntent();

        HashMap<String, ArrayList<Integer>> playerScores = (HashMap<String, ArrayList<Integer>>) intent.getSerializableExtra("hashMap");
        int currentHole = (int) intent.getSerializableExtra("currentHole");
        String selectedPlayerOption = intent.getStringExtra("selectedPlayerOption");
        String selectedGameTypeOption = intent.getStringExtra("selectedGameTypeOption");
        String selectedCourse = intent.getStringExtra("selectedCourse");
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.badgertee", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");


        Context context = getApplicationContext();

        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("pastScores", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        String formattedDate = currentDate.format(formatter);

        List<String> playerOrder = Arrays.asList("Player 1", "Player 2", "Player 3", "Player 4");
        String roundScoreString="";
        ArrayList<String> displayScores = new ArrayList<>();

        for (String playerName : playerOrder) {
            int roundScore=0;
            if (playerScores.containsKey(playerName)) {
                ArrayList<Integer> scores = playerScores.get(playerName);
                StringBuilder playerScoreString = new StringBuilder();
                StringBuilder DisplayPlayerScoresString = new StringBuilder(playerName + ": ");

                for (int score : scores) {
                    playerScoreString.append(score).append(",");
                    roundScore+=score;
                    DisplayPlayerScoresString.append(score).append(", ");

                }
                if (playerScoreString.length() > 0) {
                    playerScoreString.setLength(playerScoreString.length() - 1);
                    DisplayPlayerScoresString.setLength(DisplayPlayerScoresString.length() - 2);

                }
                displayScores.add(DisplayPlayerScoresString.toString());
                roundScoreString=roundScoreString+roundScore+","+playerScoreString+",";
            }
        }
        roundScoreString.substring(0, roundScoreString.length() - 1);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayScores);
        ListView listView = (ListView) findViewById(R.id.finalScoreListView);
        listView.setAdapter(adapter);


        dbHelper.saveScore(username, selectedCourse, formattedDate,
                roundScoreString, selectedPlayerOption);

    }

    public void navStartPage(View view){
        Intent intent = new Intent(this, start_page.class);
        startActivity(intent);
    }

    public int winnerScore(ArrayList rounds){
        return 0;
    }


}