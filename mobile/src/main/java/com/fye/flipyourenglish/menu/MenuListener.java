package com.fye.flipyourenglish.menu;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.activities.CardReader;
import com.fye.flipyourenglish.activities.CardRemover;
import com.fye.flipyourenglish.activities.CardSelector;
import com.fye.flipyourenglish.activities.CardWriter;

/**
 * Created by Anton_Kutuzau on 3/13/2017.
 */

public class MenuListener implements View.OnClickListener {

    private final Activity activity;

    public MenuListener(Activity activity) {
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
            case R.id.select_cards:
                activity.startActivity(new Intent(activity, CardSelector.class));
                break;
            case R.id.delete_cards:
                activity.startActivity(new Intent(activity, CardRemover.class));
                break;
        }
    }
}
