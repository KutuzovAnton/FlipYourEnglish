package com.fye.flipyourenglish.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class Utils {

    public final static Gson gson = new GsonBuilder().create();
    private static final long TRANSLATION_SPEED = 1000;

    public static void translation(int size, TextView textView, Runnable function) {
        float x = textView.getX();
        textView.animate().translationXBy(size).setDuration(TRANSLATION_SPEED).withEndAction(() -> {
            textView.setX(-size);
            function.run();
            textView.animate().translationXBy(size+x).setDuration(TRANSLATION_SPEED);
        });
    }

}
