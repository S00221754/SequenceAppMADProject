package com.example.sequenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GameOverActivity extends AppCompatActivity {

    //variables
    DatabaseHelper db;
    Button btnTryAgain, btnHighScore;
    TextView tvScore;
    int finalScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
         db = new DatabaseHelper(this);
         //gets score from other activity
        Bundle score = getIntent().getExtras();
        finalScore = score.getInt("Score");


        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnHighScore = findViewById(R.id.btnHighScore);
        tvScore = findViewById(R.id.score);

        tvScore.setText(String.valueOf(finalScore));
        if (topFiveScore(finalScore)){
            //calls the addscore activity to add the score
            Intent addScore = new Intent(getApplicationContext(), InputScore.class);
            addScore.putExtra("Score", finalScore);
            startActivity(addScore);
        }
    }
    //used to check the current score is higher than the fifth score
    private boolean topFiveScore(int currentScore){
        List<Score> topFiveScores = db.getTopScores();
        //if there is less than five scores in the database it will auto add it
        if (topFiveScores.size() < 5){
            return true;
        }
        int fifthScore = Integer.parseInt(topFiveScores.get(4).getFinalScore());
        return currentScore > fifthScore;
    }
    //allows user to play the game again
    public void PlayAgain(View v){
        Intent startGame = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(startGame);
    }
    //allows user to view the highscores
    public void HighScores(View v){
        Intent viewScores = new Intent(getApplicationContext(), HighScore.class);
        startActivity(viewScores);
    }
}