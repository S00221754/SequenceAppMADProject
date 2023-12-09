package com.example.sequenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
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
    int sequenceIndex = 0;
    Handler handler = new Handler();
    TextView sequenceList;
    boolean inProgress = false, // checks if its the users turn
            gameOver = false;



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
        sequence = new RandomSequence();
        currentSequence = sequence.GenerateSequence(4);

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
                    handler.postDelayed(this, 1000); //
                } else {
                    inProgress = true;
                }
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

    //currently checks the whole sequence of the user, TODO: the moment a user makes the wrong move its game over
    private void checkSequence(){
        List<String> tiltSequence = tiltDirection.getTiltSequence();
        if(inProgress && tiltSequence.size() == currentSequence.length){

            if(tiltSequence.equals(Arrays.asList(currentSequence))){
                sequenceList.setText("Correct");
            }else {
                sequenceList.setText("Wrong");
            }
            tiltDirection.clearTileSequence();
        }
    }
    //check when four tilts are detected TODO: this method may be removed or altered to satisfy the moment a user makes the wrong move
    public void fourTiltsDetected() {
        // Now you can call checkSequence when four tilts are detected
        checkSequence();
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