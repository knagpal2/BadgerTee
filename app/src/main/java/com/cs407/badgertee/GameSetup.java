package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameSetup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
    }

    public void navRangeFinder(View view){

        Intent intent = new Intent(this, RangeFinder.class);
        startActivity(intent);

    }
}