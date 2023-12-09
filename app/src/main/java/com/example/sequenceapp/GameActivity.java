package com.example.sequenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TiltDirection tiltDirection;
    RandomSequence sequence;
    ImageView red,blue,green,yellow;
    String[] currentSequence;
    int sequenceIndex = 0;
    Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        red = findViewById(R.id.redTile);
        blue = findViewById(R.id.blueTile);
        green = findViewById(R.id.greenTile);
        yellow = findViewById(R.id.yellowTile);

        sequence = new RandomSequence();
        currentSequence = sequence.GenerateSequence(4);

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
                    handler.postDelayed(this, 1000); // Adjust the flashing duration as needed
                } else {
                    // The sequence flashing is complete, you can perform additional actions here
                }
            }
        }, 1000); // Initial delay before starting the flashing
    }

    // Flash the tile based on the color
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
        }, 500); // Adjust the duration as needed
    }
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

    // Get the original color of a specific tile
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
                return 0; // Default to 0 if the color is not recognized
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