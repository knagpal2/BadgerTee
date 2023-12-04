package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.badgertee", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("username", "")!= "" ){
            username=sharedPreferences.getString("username", "");
            goToActivity(username);
        }else{
            setContentView(R.layout.activity_main);
        }
    }

    public void goToActivity(String s){
        Intent intent = new Intent(this, start_page.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.golf_menu, menu);
        return true;
    }

    public void navSignOn(View view){

        Intent intent = new Intent(this, sign_up_page.class);
        startActivity(intent);

    }

    public void navStart(View view){
        EditText myTextField = (EditText) findViewById(R.id.editTextText) ;
        if (myTextField.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a valid username and password", Toast.LENGTH_SHORT).show();

        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.badgertee", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("username", myTextField.getText().toString()).apply();
            goToActivity(myTextField.getText().toString());


            Intent intent = new Intent(this, start_page.class);
            startActivity(intent);
        }
    }


}