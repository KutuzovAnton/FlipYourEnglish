package com.fye.flipyourenglish.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.fye.flipyourenglish.R;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class Utils {

    public static final String DATABASE_NAME = "core.db";
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

    public static void translation(int size, ViewSwitcher viewSwitcher, Runnable function) {
        float x = viewSwitcher.getX();
        viewSwitcher.animate().translationXBy(size).setDuration(TRANSLATION_SPEED).withEndAction(new Runnable() {
            @Override
            public void run() {
                viewSwitcher.setX(-size);
                function.run();
                viewSwitcher.animate().translationXBy(size + x).setDuration(TRANSLATION_SPEED);
            }
        });
    }

    public static void showSnackBar(Context context, String message) {
        showSnackBar(context, message, View.NO_ID, Gravity.BOTTOM);
    }

    public static void showSnackBar(Context context, String message, int icon, int gravity) {
        View currentFocus = ((Activity) context).findViewById(android.R.id.content);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(message);
        if (icon != View.NO_ID) {
            builder.append(" ").setSpan(new ImageSpan(context, icon), builder.length() - 1, builder.length(), 0);
        }
        Snackbar snack = Snackbar.make(currentFocus, builder, Snackbar.LENGTH_SHORT);
        View view = snack.getView();
        view.setBackgroundColor((context.getResources().getColor(R.color.bright_greenOpacity)));
        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = gravity;
        view.setLayoutParams(params);
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
