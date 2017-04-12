package com.fye.flipyourenglish.builders;

import android.app.Activity;
import android.widget.TextView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.activities.OnSwipeTouchListener;

/**
 * Created by Anton_Kutuzau on 3/29/2017.
 */

public class TextViewBuilder {

    private Activity activity;
    private String text;
    private int size;
    private int index;

    public TextViewBuilder(Activity activity) {
        this.activity = activity;
    }

    public TextView build() {

        TextView textView = new TextView(activity);
        textView.setTextSize(size);
        textView.setId(index++);
        textView.setTextColor(activity.getResources().getColor(R.color.black));
        textView.setBackgroundResource(R.drawable.card_style);
        textView.setText(text);
        return textView;
    }

    public TextViewBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TextViewBuilder setSize(int size) {
        this.size = size;
        return this;
    }
}
