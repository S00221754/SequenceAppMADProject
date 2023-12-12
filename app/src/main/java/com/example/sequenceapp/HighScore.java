package com.example.sequenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.util.Log;
import android.widget.ListView;

import java.util.List;


public class HighScore extends AppCompatActivity {

    Button btnPlayGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        DatabaseHelper db = new DatabaseHelper(this);
        btnPlayGame = findViewById(R.id.btnPlayGame);

        ListView topScores = findViewById(R.id.lvTopScores);
        List<Score> topScorers = db.getTopScores();

        ArrayAdapter<Score> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topScorers);
        topScores.setAdapter(adapter);
    }
    public void PlayAgain(View v){
        Intent startGame = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(startGame);
    }
}