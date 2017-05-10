package com.fye.flipyourenglish.listeners;

import android.app.Activity;
import android.view.View;

/**
 * Created by Anton_Kutuzau on 5/9/2017.
 */

public class GoBackListener implements View.OnClickListener {

    private Activity activity;

    public GoBackListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        activity.finish();
    }
}
