package com.fye.flipyourenglish.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fye.flipyourenglish.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class Utils {

    public static final String DATABASE_NAME = "core.db";
    public final static Gson gson = new GsonBuilder().create();
    private static final long TRANSLATION_SPEED = 1000;

    public static void translation(int size, TextView textView, Runnable function) {
        float x = textView.getX();
        textView.animate().translationXBy(size).setDuration(TRANSLATION_SPEED).withEndAction(new Runnable() {
            @Override
            public void run() {
                textView.setX(-size);
                function.run();
                textView.animate().translationXBy(size+x).setDuration(TRANSLATION_SPEED);
            }
        });
    }

    public static void showToast(Context context, String massage) {
        Toast toast = Toast.makeText(context,
                massage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView catImageView = new ImageView(context);
        catImageView.setImageResource(R.drawable.added_successful);
        toastContainer.addView(catImageView, 0);
        toast.show();
    }

}
