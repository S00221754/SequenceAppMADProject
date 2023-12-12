package com.example.sequenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InputScore extends AppCompatActivity {
    DatabaseHelper db;
    Button btnAdd;
    TextView userName;
    int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_score);
        db = new DatabaseHelper(this);
        Bundle score = getIntent().getExtras();
        finalScore = score.getInt("Score");
        btnAdd = findViewById(R.id.btnAdd);
        userName = findViewById(R.id.evUserName);

    }
    public void AddScore(View v){
        if (userName.getText() != null){
            db.addScore(new Score(String.valueOf(userName.getText()),String.valueOf(finalScore)));
            Intent viewScores = new Intent(getApplicationContext(), HighScore.class);
            startActivity(viewScores);
        }
        else {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        }
    }
}