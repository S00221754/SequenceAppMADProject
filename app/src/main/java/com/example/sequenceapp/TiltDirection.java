package com.example.sequenceapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TiltDirection implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] acceleration = new float[3];
    private List<String> tiltSequence = new ArrayList<>(); //this is used to store the direction the user tilted their phone in
    private Handler handler = new Handler();
    private Context context;

    private int tileCount = 0;

    public TiltDirection(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        startTiltCheck();
    }

    private void startTiltCheck() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkTiltDirection();
                handler.postDelayed(this, 500);
            }
        }, 1000);
    }

    private void checkTiltDirection() {
        if (acceleration[2] < 6) {
            processTilt("yellow");
        } else if (acceleration[0] < 2) {
            processTilt("red");
        } else if (acceleration[1] < -1) {
            processTilt("blue");
        } else if (acceleration[1] > 1) {
            processTilt("green");
        }
    }
    private void processTilt(String tiltDirection) {
        showToast(tiltDirection);
        tiltSequence.add(tiltDirection);

        if (tiltSequence.size() == 4) {
            ((GameActivity) context).fourTiltsDetected();
        }
    }


    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        acceleration[0] = event.values[0]; // x
        acceleration[1] = event.values[1]; // y
        acceleration[2] = event.values[2]; // z
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }


    //gets the tilt sequence
    public List<String> getTiltSequence(){
        return tiltSequence;
    }
    //once called clears the tilt sequence
    public void clearTileSequence(){
        tiltSequence.clear();
    }
}

