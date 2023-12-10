package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
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


        Button backButton = (Button) findViewById(R.id.backButtonGameType);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToCourseSelect = new Intent(GameSetup.this, CourseSetup.class);
                startActivity(backToCourseSelect);
            }
        });
    }

    public void navRangeFinder(View view){

        if (radioGroup.getCheckedRadioButtonId() == -1 || radioGroup3.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select options in both groups", Toast.LENGTH_SHORT).show();
        } else {

            Intent intentFromCourseSetup = getIntent();
            String selectedCourse = intentFromCourseSetup.getStringExtra("selectedCourse");

            int selectedPlayerOptionId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedPlayerRadioButton = findViewById(selectedPlayerOptionId);
            String selectedPlayerOption = selectedPlayerRadioButton.getText().toString();

            int selectedGameTypeOptionId = radioGroup3.getCheckedRadioButtonId();
            RadioButton selectedGameTypeRadioButton = findViewById(selectedGameTypeOptionId);
            String selectedGameTypeOption = selectedGameTypeRadioButton.getText().toString();


            Intent intent = new Intent(this, RangeFinder.class);
            intent.putExtra("selectedPlayerOption", selectedPlayerOption);
            intent.putExtra("selectedGameTypeOption", selectedGameTypeOption);
            intent.putExtra("selectedCourse", selectedCourse);
            startActivity(intent);
        }
    }
}