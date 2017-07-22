package com.fye.flipyourenglish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fye.flipyourenglish.MainActivity_;

/**
 * Created by Anton_Kutuzau on 7/15/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
        finish();
    }
}
