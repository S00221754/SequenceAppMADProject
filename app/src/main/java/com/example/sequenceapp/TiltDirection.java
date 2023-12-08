package com.example.sequenceapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.widget.Toast;

public class TiltDirection implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] acceleration = new float[3];
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
                handler.postDelayed(this, 1000); // Check every 1 second
            }
        }, 1000); // Start checking after 1 second
    }

    private void checkTiltDirection() {
        if (acceleration[2] < 6) {
            showToast("Tilt Backwards Detected");
        } else if (acceleration[0] < 2) {
            showToast("Tilt Forward Detected");
        } else if (acceleration[1] < -1) {
            showToast("Tilt Left Detected");
        } else if (acceleration[1] > 1) {
            showToast("Tilt Right Detected");
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
}

