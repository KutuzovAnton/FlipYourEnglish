package com.fye.flipyourenglish.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.views.CircleView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Anton_Kutuzau on 6/30/2017.
 */

@EBean
public class SensorListener implements SensorEventListener {

    @ViewById(R.id.circleView)
    CircleView circleView;

    private static final int SHAKE_SENSITIVITY = 12;
    private float accel = SensorManager.GRAVITY_EARTH;
    private float accelPrevious = SensorManager.GRAVITY_EARTH;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        accelPrevious = accel;
        accel = (float) Math.sqrt((double) (x * x + y * y));
        if (accel - accelPrevious > SHAKE_SENSITIVITY) {
            onShake();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void onShake(){
        circleView.invalidate();
        circleView.animate().alpha(1);
    }
}
