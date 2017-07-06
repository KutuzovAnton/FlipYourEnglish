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
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fye.flipyourenglish.R;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class Utils {

    public static AutoResize autoResizer = new AutoResize();
    public static final String DATABASE_NAME = "core.db";

    public static void translation(int size, View view, Runnable function, Long translationCardSpeed) {
        int x = (int) view.getX();
        translationAnimation(size, view, translationCardSpeed).withEndAction(() -> {
            view.setX(-size);
            function.run();
            translationAnimation(size + x, view, translationCardSpeed);
        });
    }

    public static ViewPropertyAnimator translationAnimation(int size, View view, long duration) {
        return view.animate().translationXBy(size).setDuration(duration);
    }

    public static void showSnackBar(Context context, String message) {
        showSnackBar(context, message, View.NO_ID, Gravity.BOTTOM);
    }

    public static void showSnackBar(Context context, String message, int icon, int gravity) {
        View currentFocus = ((Activity) context).findViewById(android.R.id.content);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(message).append("  ");
        if (icon != View.NO_ID) {
            builder.append(" ").setSpan(new ImageSpan(context, icon), builder.length() - 1, builder.length(), 0);
        }
        Snackbar snack = Snackbar.make(currentFocus, builder, Snackbar.LENGTH_SHORT);
        View view = snack.getView();
        view.setBackgroundColor((context.getResources().getColor(R.color.bright_greenOpacity)));
        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
