package com.cs407.badgertee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;

public class allPastScores extends AppCompatActivity {

    private ArrayList<String> displayScores = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_past_scores);


        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.badgertee", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("pastScores", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        ArrayList<Scores> score1 = dbHelper.readScores(username);
        ArrayList<String> displayScores = new ArrayList<>();
        for (Scores score: score1){
            String allScores = score.getRoundScore();
            String[] resultArray = allScores.split(",");

            displayScores.add(String.format("Title: %s\nDate: %s\nRound Score: %s\n ", score.getCourseName(), score.getDate(), resultArray[0]));
        }

        Log.i("INFO", displayScores.toString());

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayScores);
        ListView listView = (ListView) findViewById(R.id.scoresListView);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), pastScore.class);
                intent.putExtra("scoreId", i);
                startActivity(intent);
            }
        });

    }

    public void navStart(View view){

        Intent intent = new Intent(this, start_page.class);
        startActivity(intent);

    }

    private void addDummyPastScores(DBHelper dbHelper, String username) {
        dbHelper.saveScore(username, "Lots of Holes", "2023-01-02",
                "80,3,5,8,4,5,4,3,2,1,4,1,4,5");
    }

}