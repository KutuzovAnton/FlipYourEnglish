package com.fye.flipyourenglish.menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.activities.CardReader;
import com.fye.flipyourenglish.activities.CardRemover;
import com.fye.flipyourenglish.activities.CardWriter;
import com.fye.flipyourenglish.entities.Card;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/13/2017.
 */

public class MenuActivity implements View.OnClickListener {

    private final Activity activity;

    public MenuActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_cards:
                activity.startActivity(new Intent(activity, CardReader.class));
                break;
            case R.id.add_cards:
                activity.startActivity(new Intent(activity, CardWriter.class));
                break;
            case R.id.delete_cards:
                activity.startActivity(new Intent(activity, CardRemover.class));
                break;
        }
    }
}
