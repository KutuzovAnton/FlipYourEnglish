package com.fye.flipyourenglish.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fye.flipyourenglish.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
                textView.animate().translationXBy(size + x).setDuration(TRANSLATION_SPEED);
            }
        });
    }

    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context,
                message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView catImageView = new ImageView(context);
        catImageView.setImageResource(R.drawable.added_successful);
        toastContainer.addView(catImageView, 0);
        toast.show();
    }

    public static void showSnackBar(Context context, String message) {
        showSnackBar(context, message, View.NO_ID);
    }

    public static void showSnackBar(Context context, String message, int icon) {
        View currentFocus = ((Activity) context).findViewById(android.R.id.content);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(message);
        if (icon != View.NO_ID) {
            builder.append(" ").setSpan(new ImageSpan(context, icon), builder.length() - 1, builder.length(), 0);
        }
        Snackbar snack = Snackbar.make(currentFocus, builder, Snackbar.LENGTH_SHORT);
        snack.setAction("CLOSE", v -> {
        }).setActionTextColor(context.getResources().getColor(R.color.editTextColor));
        snack.show();
    }
    public static void resolveVisibilityForFAB(FloatingActionButton fab, int visible) {
        if (visible == View.INVISIBLE) {
            fab.hide();
        } else {
            fab.show();
        }
    }
}
