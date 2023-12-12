package com.example.sequenceapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

public class TiltDirection implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] acceleration = new float[3];
    private boolean isTiltDetected = false;
    private String tilt;
    private Handler handler = new Handler();
    private Context context;


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
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void checkTiltDirection() {
        if (acceleration[2] < 7) {
            tilt = "yellow";
            Log.d("Tilt detected", "yellow");

        } else if (acceleration[0] < -3 && acceleration[1] <1) {
            tilt = "red";
            Log.d("Tilt detected", "red");
        } else if (acceleration[1] < -1) {
            tilt = "blue";
            Log.d("Tilt detected", "blue");
        } else if (acceleration[1] > 1) {
            tilt = "green";
            Log.d("Tilt detected", "green");
        }

        if (tilt != null){
            isTiltDetected = true;
        }
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
    public String getTilt(){
        return tilt;
    }
    //once called clears the tilt sequence
    public void clearTilt(){
        tilt = null;
    }
    public boolean isTiltDetected(){
        return isTiltDetected;
    }
    public void resetTilt(){
        isTiltDetected = false;
    }
}

