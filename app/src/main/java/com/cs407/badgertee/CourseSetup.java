package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CourseSetup extends AppCompatActivity {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_setup);
        radioGroup = findViewById(R.id.radioGroup);

        Button backButton = (Button) findViewById(R.id.backButtonCourseSelect);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToStartPage = new Intent(CourseSetup.this, start_page.class);
                startActivity(backToStartPage);
            }
        });
    }

    public void navGameSetup(View view){
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a Tee", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, GameSetup.class);
            startActivity(intent);
        }
    }
}