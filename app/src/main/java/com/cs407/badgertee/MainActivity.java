package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        Intent intent = new Intent(this, start_page.class);
        startActivity(intent);

    }


}