package com.fye.flipyourenglish;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fye.flipyourenglish.menu.MenuListener;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.views.CircleView;

import java.util.ArrayList;

import static com.fye.flipyourenglish.utils.Utils.DATABASE_NAME;


public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private static final int SHAKE_SENSITIVITY = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        setContentView(R.layout.activity_main);
        init();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(
                sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void init() {
        ArrayList<View> touchables = findViewById(R.id.main).getTouchables();
        for (View view : touchables) {
            view.setOnClickListener(new MenuListener(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(
                sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(sensorListener);

        super.onStop();
    }

    protected void onShake(){
        ((CircleView)findViewById(R.id.circleView)).invalidate();
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {

        private float accel = SensorManager.GRAVITY_EARTH;
        private float accelPrevious = SensorManager.GRAVITY_EARTH;

        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            accelPrevious = accel;
            accel = (float) Math.sqrt((double) (x * x + y * y));
            if (accel - accelPrevious > SHAKE_SENSITIVITY) {
                onShake();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

}
