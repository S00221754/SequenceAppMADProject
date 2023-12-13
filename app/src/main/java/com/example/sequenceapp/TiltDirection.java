package com.example.sequenceapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

public class TiltDirection implements SensorEventListener {

    //variables
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] acceleration = new float[3];
    private boolean isTiltDetected = false;
    private String tilt;
    private Handler handler = new Handler();
    private Context context;


    //gets the sensor
    public TiltDirection(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        startTiltCheck();
    }
    //begins checking for tilt directions
    private void startTiltCheck() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkTiltDirection();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }
    //Checks the xyz cords and compares them to which direction the phone tilted in
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

    //gets the xyz of the phone due to the accelerometer
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
    //notifies when a tilt has been made
    public boolean isTiltDetected(){
        return isTiltDetected;
    }
    //resets the tilt
    public void resetTilt(){
        isTiltDetected = false;
    }
}

