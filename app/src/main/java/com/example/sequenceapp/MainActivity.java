package com.example.sequenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.btnPlay);
        //starts the game
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGame = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(startGame);
            }
        });
    }
    //allows user to view the top 5 scores
    public void viewScores(View v){
        Intent viewScores = new Intent(getApplicationContext(), HighScore.class);
        startActivity(viewScores);
    }
}