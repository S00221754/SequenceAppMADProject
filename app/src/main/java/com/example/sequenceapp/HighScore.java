package com.example.sequenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import java.util.List;


public class HighScore extends AppCompatActivity {

    Button btnPlayGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        DatabaseHelper db = new DatabaseHelper(this);
        btnPlayGame = findViewById(R.id.btnPlayGame);
    }
    public void PlayAgain(View v){
        Intent startGame = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(startGame);
    }
}