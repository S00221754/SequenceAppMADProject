package com.example.sequenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //variables
    TiltDirection tiltDirection;
    RandomSequence sequence;
    ImageView red,blue,green,yellow;
    String[] currentSequence;
    int sequenceIndex = 0, sequenceNumber = 4, totalScore = 0;
    Handler handler = new Handler();
    TextView score;
    boolean gameOver = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //find views
        red = findViewById(R.id.redTile);
        blue = findViewById(R.id.blueTile);
        green = findViewById(R.id.greenTile);
        yellow = findViewById(R.id.yellowTile);
        score = findViewById(R.id.Score);
        //initial sequence
        sequence = new RandomSequence();
        currentSequence = sequence.GenerateSequence(sequenceNumber);

        //start tracking the accelerometer (need to switch this so it will only track once the sequence showing is over
        tiltDirection = new TiltDirection(this);
        // Start flashing the sequence
        flashSequence();
    }

    // Flash the tiles according to the current sequence
    private void flashSequence() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //flashes the drawables in the order of the sequence colours
                if (sequenceIndex < currentSequence.length) {
                    flashTile(currentSequence[sequenceIndex]);
                    sequenceIndex++;
                    handler.postDelayed(this, 1000);
                }
                //begins to check tilts and sequence
                CheckTilt();
            }
        }, 1000);
    }


    // flashes the tile according to the sequence colours
    private void flashTile(String color) {

        int originalColor = getOriginalColor(color);
        switch (color) {
            case "red":
                red.getDrawable().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
                break;
            case "blue":
                blue.getDrawable().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
                break;
            case "green":
                green.getDrawable().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
                break;
            case "yellow":
                yellow.getDrawable().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
                break;
        }
        final int finalOriginalColor = originalColor;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resetTileColor(color, finalOriginalColor);
            }
        }, 500);
    }
    //resets the color of the tile after flashing
    private void resetTileColor(String color, int originalColor) {
        switch (color) {
            case "red":
                red.getDrawable().setColorFilter(originalColor, PorterDuff.Mode.SRC_ATOP);
                break;
            case "blue":
                blue.getDrawable().setColorFilter(originalColor, PorterDuff.Mode.SRC_ATOP);
                break;
            case "green":
                green.getDrawable().setColorFilter(originalColor, PorterDuff.Mode.SRC_ATOP);
                break;
            case "yellow":
                yellow.getDrawable().setColorFilter(originalColor, PorterDuff.Mode.SRC_ATOP);
                break;
        }
    }

    // gets the original color of the tile so it can be returned after flashing
    private int getOriginalColor(String color) {
        switch (color) {
            case "red":
                return ContextCompat.getColor(this, R.color.red);
            case "blue":
                return ContextCompat.getColor(this, R.color.blue);
            case "green":
                return ContextCompat.getColor(this, R.color.green);
            case "yellow":
                return ContextCompat.getColor(this, R.color.yellow);
            default:
                return 0;
        }
    }
    //this checks if a tilt is detected before checking the sequence.
    private void CheckTilt() {
        if (tiltDirection.isTiltDetected()) {
            FlashUserTile(tiltDirection.getTilt());
            CheckUserSequence();
            tiltDirection.resetTilt();
        } else {
            // if no tilt is detected, it will continue to keep checking
            handler.postDelayed(this::CheckTilt, 1000);
        }
    }
    //flashes where the user tilts towards
    private void FlashUserTile(String color) {
        int originalColor = getOriginalColor(color);

        // Check if tiltDirection is not null and tilt is not null
        if (tiltDirection != null && tiltDirection.getTilt() != null) {
            String tilt = tiltDirection.getTilt();
            Log.d("FlashTile", "Tilt direction: " + tilt);

            // Flash the tile based on the tilt direction
            switch (tilt) {
                case "red":
                    flashTileWithDirection(red, originalColor);
                    break;
                case "blue":
                    flashTileWithDirection(blue, originalColor);
                    break;
                case "green":
                    flashTileWithDirection(green, originalColor);
                    break;
                case "yellow":
                    flashTileWithDirection(yellow, originalColor);
                    break;
            }
        }

        //resets the color after flashing
        final int finalOriginalColor = originalColor;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resetTileColor(color, finalOriginalColor);
            }
        }, 500);
    }
    //used to flash the tile that the user tilts too
    private void flashTileWithDirection(ImageView tile, int originalColor) {
        tile.getDrawable().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    //Checks each tilt aligns with the sequence if not it is game over
    private void CheckUserSequence() {
        if (!gameOver) {
            String currentTilt = tiltDirection.getTilt();

            if (currentSequence != null) {
                if (currentSequence.length > 0 && currentTilt.equals(currentSequence[0])) {
                    // This removes the first color of the array and moves the next color into 0
                    currentSequence = Arrays.copyOfRange(currentSequence, 1, currentSequence.length);
                    tiltDirection.clearTilt();
                } else {

                    setGameOver(); // sets game is over
                    Intent gameOver = new Intent(getApplicationContext(), GameOverActivity.class);
                    gameOver.putExtra("Score", totalScore);
                    startActivity(gameOver);
                }

                // When a user finishes a sequence, it will reset the sequence with a new sequence with 2 additional colors
                if (currentSequence.length == 0) {
                    // Adds the score
                    totalScore += sequenceNumber;
                    score.setText(String.valueOf(totalScore));
                    sequenceNumber += 2;
                    Log.d("new sequence", String.valueOf(sequenceNumber));
                    currentSequence = sequence.GenerateSequence(sequenceNumber);
                    tiltDirection.clearTilt();
                    sequenceIndex = 0;
                    flashSequence();
                }
            }
        }
    }
    //used to set the game over to true
    private void setGameOver() {
        gameOver = true;
    }

    //the following methods below are for the tilt direction
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}