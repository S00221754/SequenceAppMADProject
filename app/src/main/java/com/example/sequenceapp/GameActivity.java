package com.example.sequenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

    /*TODO: 1. Allow the program to check each tilt input and compare to the sequence index it is aligned to
            2. Implement a way to display the game over screen.
            3. Allow users to restart the game when its game over
            4. Add the score at the after each successful round
            5. Implement database function (will add tasks to this after functionality is done)*/

    TiltDirection tiltDirection;
    RandomSequence sequence;
    ImageView red,blue,green,yellow;
    String[] currentSequence;
    int sequenceIndex = 0, sequenceNumber = 4, totalScore = 0, AddScore=0;
    Handler handler = new Handler();
    TextView sequenceList, score;
    boolean newGame = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //find views
        red = findViewById(R.id.redTile);
        blue = findViewById(R.id.blueTile);
        green = findViewById(R.id.greenTile);
        yellow = findViewById(R.id.yellowTile);
        sequenceList = findViewById(R.id.sequence);
        score = findViewById(R.id.Score);
        sequence = new RandomSequence();
        currentSequence = sequence.GenerateSequence(sequenceNumber);
        //currentSequence = new String[]{"blue", "red", "yellow", "green"};
        //for testing.
        StringBuilder build = new StringBuilder();
        for(String item : currentSequence){
            build.append(item).append("\n");
        }
        sequenceList.setText(build.toString());
        //start tracking the accelerometer (need to switch this so it will only track once the sequence showing is over
        tiltDirection = new TiltDirection(this);
        // Start flashing the sequence
        flashSequence();
    }

    // Flash the current sequence
    private void flashSequence() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sequenceIndex < currentSequence.length) {
                    flashTile(currentSequence[sequenceIndex]);
                    sequenceIndex++;
                    handler.postDelayed(this, 1000);
                }
                checkAndProceed();
            }
        }, 1000);
    }


    // flashes the tile according to the color
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
    private void checkAndProceed() {
        if (tiltDirection.isTiltDetected()) {
            CheckUserSequence();
            tiltDirection.resetTilt();
        } else {
            // If no tilt is detected, you can add additional logic or just wait for the next iteration.
            handler.postDelayed(this::checkAndProceed, 100);
        }
    }

    //Checks each tilt aligns with the sequence if not it is game over
    private void CheckUserSequence() {
        String currentTilt = tiltDirection.getTilt();

        if (currentSequence != null){
            if (currentSequence.length > 0 && currentTilt.equals(currentSequence[0])) {
                //this removes the first color of the array and moves the next color into 0
                currentSequence = Arrays.copyOfRange(currentSequence, 1, currentSequence.length);
                //testing
                StringBuilder build = new StringBuilder();
                for(String item : currentSequence){
                    build.append(item).append("\n");
                }
                sequenceList.setText(build.toString());
                tiltDirection.clearTilt();
            } else {
                sequenceList.setText("You lose");
            }
            //when a user finishes a sequence it will reset the sequence with a new sequence with 2 additional colours
            if (currentSequence.length == 0){
                //adds the score
                totalScore += sequenceNumber;
                score.setText(String.valueOf(totalScore));
                sequenceNumber += 2;
                Log.d("new sequence", String.valueOf(sequenceNumber));
                currentSequence = sequence.GenerateSequence(sequenceNumber);
                //testing
                StringBuilder build = new StringBuilder();
                for(String item : currentSequence){
                    build.append(item).append("\n");
                }
                sequenceList.setText(build.toString());
                tiltDirection.clearTilt();
                sequenceIndex = 0;
                flashSequence();

            }
        }

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