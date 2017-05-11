package com.fye.flipyourenglish;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.fye.flipyourenglish.listeners.MainActivityListener;
import com.fye.flipyourenglish.menu.Language;
import com.fye.flipyourenglish.menu.Menu;
import com.fye.flipyourenglish.utils.Utils;
import com.fye.flipyourenglish.views.CircleView;

import java.util.ArrayList;
import java.util.Locale;

import static com.fye.flipyourenglish.menu.Menu.translationButtonSpeed;
import static com.fye.flipyourenglish.utils.Utils.DATABASE_NAME;


public class MainActivity extends AppCompatActivity {


    private SensorManager sensorManager;
    private static final int SHAKE_SENSITIVITY = 12;
    private CircleView circleView;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        super.onCreate(savedInstanceState);
        openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        setContentView(R.layout.activity_main);
        init();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(
                sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        ((CircleView)findViewById(R.id.circleView)).animate().alpha(1);
    }

    private void setLanguage() {
        String lang = getSharedPreferences(Menu.APP_PREFERENCES, Context.MODE_PRIVATE).getString(Menu.LANGUAGE, "English");
        Locale myLocale = new Locale(Language.getShortFormByLong(lang));
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }


    public void init() {
        MainActivityListener mainActivityListener = new MainActivityListener(this);
        circleView = (CircleView) findViewById(R.id.circleView);
        initButtons(mainActivityListener);
        NavigationView navigationView = initNavigationView(mainActivityListener);
        menu = new Menu(this, this, navigationView, getSharedPreferences(Menu.APP_PREFERENCES, Context.MODE_PRIVATE));
    }

    private NavigationView initNavigationView(MainActivityListener mainActivityListener) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initButtons(mainActivityListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mainActivityListener);

        return navigationView;
    }

    private void initButtons(MainActivityListener mainActivityListener) {
        int i = 0;
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        ArrayList<View> touchables = findViewById(R.id.main_activity).getTouchables();
        for (View view : touchables) {
            int startPosition = (int) (Math.pow(-1, i++) * point.x);
            view.setX(-startPosition);
            Utils.translationAnimation(startPosition, view, translationButtonSpeed);
            view.setOnClickListener(mainActivityListener);
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
        menu.saveMenu();
        super.onStop();
    }

    protected void onShake(){
        circleView.invalidate();
        ((CircleView)findViewById(R.id.circleView)).animate().alpha(1);
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
