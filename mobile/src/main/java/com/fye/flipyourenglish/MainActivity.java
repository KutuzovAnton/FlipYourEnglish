package com.fye.flipyourenglish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fye.flipyourenglish.menu.MenuListener;

import java.util.ArrayList;

import static com.fye.flipyourenglish.utils.Utils.DATABASE_NAME;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        ArrayList<View> touchables = findViewById(R.id.main).getTouchables();
        for (View view : touchables) {
            view.setOnClickListener(new MenuListener(this));
        }
    }

}
