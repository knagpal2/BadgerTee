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
        displayWinnerInfo(playerScores, selectedGameTypeOption);


        dbHelper.saveScore(username, selectedCourse, formattedDate,
                roundScoreString, selectedPlayerOption);
        Scores score = new Scores(formattedDate,username,roundScoreString,selectedCourse,selectedPlayerOption);
        ArrayList<String> displayScores = new ArrayList<>();
        String allScores = score.getRoundScore();
        String[] resultArray = allScores.split(",");
        String roundScores="";
        int pos= resultArray.length/Integer.parseInt(score.getNumPlayers());
        for (int j =0; j<Integer.parseInt(score.getNumPlayers()); j++){
            roundScores+="Player "+ (1+j) +": "+resultArray[pos*j]+" ";
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
        ListView listView = (ListView) findViewById(R.id.finalScoreListView);
        listView.setAdapter(adapter);



    }

    public void navStartPage(View view) {
        Intent intent = new Intent(this, start_page.class);
        startActivity(intent);
    }


    public void displayWinnerInfo(HashMap<String, ArrayList<Integer>> playerScores, String gameType) {
        int winnerScore;
        String winnerName = "";

        if ("match play".equalsIgnoreCase(gameType)) {
            winnerScore = findMatchPlayWinnerScore(playerScores);
        } else {
            winnerScore = findStrokePlayWinnerScore(playerScores);
        }

        for (String playerName : playerScores.keySet()) {
            ArrayList<Integer> scores = playerScores.get(playerName);
            int roundScore = calculateRoundScore(scores);

            if (roundScore == winnerScore) {
                winnerName = playerName;
                break;
            }
        }

        TextView winnerTextView = findViewById(R.id.winnerText);
        winnerTextView.setText("Congratulations, " + winnerName + "!");

    }

    public int findMatchPlayWinnerScore(HashMap<String, ArrayList<Integer>> playerScores) {
        int maxHolesWon = -1;
        int winnerScore = Integer.MAX_VALUE;

        for (String playerName : playerScores.keySet()) {
            ArrayList<Integer> scores = playerScores.get(playerName);
            int holesWon = calculateHolesWon(playerScores, playerName);

            if (holesWon > maxHolesWon || (holesWon == maxHolesWon && calculateRoundScore(scores) < winnerScore)) {
                maxHolesWon = holesWon;
                winnerScore = calculateRoundScore(scores);
            }
        }

        TextView winnerScoreTextView = findViewById(R.id.winnerScore);
        winnerScoreTextView.setText("Winner Holes Won: " + maxHolesWon+"\nWinner Score: "+ winnerScore);
        return winnerScore;
    }



    public int findStrokePlayWinnerScore(HashMap<String, ArrayList<Integer>> playerScores) {
        int minScore = Integer.MAX_VALUE;

        for (ArrayList<Integer> scores : playerScores.values()) {
            int roundScore = calculateRoundScore(scores);
            minScore = Math.min(minScore, roundScore);
        }
        TextView winnerScoreTextView = findViewById(R.id.winnerScore);
        winnerScoreTextView.setText("Score: " + minScore);
        return minScore;
    }

    public int calculateRoundScore(ArrayList<Integer> scores) {
        int roundScore = 0;
        for (Integer score : scores) {
            roundScore += score;
        }
        return roundScore;
    }


    public int calculateHolesWon(HashMap<String, ArrayList<Integer>> playerScores, String currentPlayerName ){
        int holesWon = 0;
        for (int i = 0; i < playerScores.get(currentPlayerName).size(); i++) {
            int currentPlayerScore = playerScores.get(currentPlayerName).get(i);
            int otherPlayersMinScore = Integer.MAX_VALUE;

            for (String otherPlayerName : playerScores.keySet()) {
                if (!otherPlayerName.equals(currentPlayerName)) {
                    ArrayList<Integer> otherPlayerScores = playerScores.get(otherPlayerName);
                    otherPlayersMinScore = Math.min(otherPlayersMinScore, otherPlayerScores.get(i));
                }
            }

            if (currentPlayerScore < otherPlayersMinScore) {
                holesWon++;
            }
        }

        return holesWon;
    }
}