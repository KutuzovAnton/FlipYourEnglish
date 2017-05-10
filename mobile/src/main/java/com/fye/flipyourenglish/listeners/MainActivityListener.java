package com.fye.flipyourenglish.listeners;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.activities.CardReader;
import com.fye.flipyourenglish.activities.CardRemover;
import com.fye.flipyourenglish.activities.CardSelector;
import com.fye.flipyourenglish.activities.CardWriter;


/**
 * Created by Anton_Kutuzau on 3/13/2017.
 */

public class MainActivityListener implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private final Activity activity;

    public MainActivityListener(Activity activity) {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //TODO we can running some activate
        return true;
    }

}
