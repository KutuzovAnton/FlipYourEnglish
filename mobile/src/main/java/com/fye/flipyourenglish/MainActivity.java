package com.fye.flipyourenglish;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.fye.flipyourenglish.listeners.SensorListener;
import com.fye.flipyourenglish.menu.Language;
import com.fye.flipyourenglish.menu.Menu;
import com.fye.flipyourenglish.views.CircleView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

import static com.fye.flipyourenglish.utils.Utils.DATABASE_NAME;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    @ViewById(R.id.circleView)
    CircleView circleView;
    @Bean
    Menu menu;
    @Bean
    SensorListener sensorListener;
    private SensorManager sensorManager;

    @AfterInject
    protected void setLanguage() {
        String lang = getSharedPreferences(Menu.APP_PREFERENCES, Context.MODE_PRIVATE).getString(Menu.LANGUAGE, "English");
        Locale myLocale = new Locale(Language.getShortFormByLong(lang));
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.setLocale(myLocale);
        res.updateConfiguration(config, dm);
    }

    @AfterViews
    protected void init() {
        openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        registrationListener();
        circleView.animate().alpha(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registrationListener();
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(sensorListener);
        menu.saveMenu();
        super.onStop();
    }

    private void registrationListener() {
        sensorManager.registerListener(
                sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }


}
