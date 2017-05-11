package com.fye.flipyourenglish.builders;

import android.app.Activity;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.fye.flipyourenglish.R;

/**
 * Created by Anton_Kutuzau on 3/29/2017.
 */

public class TextViewBuilder {

    private Activity activity;
    private int size;
    private int index;
    private int color;
    private Spanned hint;

    public TextViewBuilder(Activity activity) {
        this.activity = activity;
    }

    public TextView build() {

        TextView textView = new TextView(activity);
        textView.setTextSize(size);
        textView.setId(index++);
        textView.setBackgroundResource(R.drawable.card_style);
        textView.setHint(hint);
        textView.setHintTextColor(color);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textView.setPadding(20,0,0,0);
        return textView;
    }

    public TextViewBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public TextViewBuilder setColor(int color) {
        this.color = color;
        return this;
    }

    public TextViewBuilder setHint(Spanned hint) {
        this.hint = hint;
        return this;
    }
}
