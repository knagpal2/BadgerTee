package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class sign_up_page extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox maleCheckBox;
    private CheckBox femaleCheckBox;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);


        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        maleCheckBox = findViewById(R.id.male);
        femaleCheckBox = findViewById(R.id.female);
        createAccountButton = findViewById(R.id.createAccount);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String gender = maleCheckBox.isChecked() ? "Male" : (femaleCheckBox.isChecked() ? "Female" : "");

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                    Toast.makeText(sign_up_page.this, "Please fill in all information", Toast.LENGTH_SHORT).show();
                    return;
                }
                Context context = getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("users", Context.MODE_PRIVATE, null);

                LogInDB logInDB = new LogInDB(sqLiteDatabase);
                boolean isUserSaved = logInDB.saveUser(username, password, gender, email);

                if (isUserSaved) {
                    SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.badgertee", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("username", nameEditText.getText().toString()).apply();
                    goToActivity(nameEditText.getText().toString());
                } else {
                    Toast.makeText(sign_up_page.this, "Username already taken", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goToActivity(String s){
        Intent intent = new Intent(this, start_page.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }


}