package com.fye.flipyourenglish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fye.flipyourenglish.menu.MenuActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        findViewById(R.id.main)
                .getTouchables()
                .forEach(b -> b.setOnClickListener(new MenuActivity(this)));
    }

}
