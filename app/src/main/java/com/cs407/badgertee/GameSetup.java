package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GameSetup extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioGroup radioGroup3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup3 = findViewById(R.id.radioGroup3);
    }

    public void navRangeFinder(View view){

        if (radioGroup.getCheckedRadioButtonId() == -1 || radioGroup3.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select options in both groups", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, RangeFinder.class);
            startActivity(intent);
        }
    }
}