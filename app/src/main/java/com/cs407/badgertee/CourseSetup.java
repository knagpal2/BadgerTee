package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CourseSetup extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioGroup courseGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_setup);
        radioGroup = findViewById(R.id.radioGroup);
        courseGroup = findViewById(R.id.courseGroup);

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
        if (radioGroup.getCheckedRadioButtonId() == -1 || courseGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a Tee and a Course", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, GameSetup.class);
            String selectedCourse = getSelectedCourse(); // Get the selected course
            intent.putExtra("selectedCourse", selectedCourse); // Pass the selected course to the GameSetup activity
            startActivity(intent);
        }
    }

    private String getSelectedCourse() {
        int selectedId = courseGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        return selectedRadioButton.getText().toString();
    }
}