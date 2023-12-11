package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class RoundEnd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_end);
    }

    public void navStartPage(View view){
        Intent intent = new Intent(this, start_page.class);
        startActivity(intent);
    }

    public int winnerScore(ArrayList rounds){
        return 0;
    }


}