package com.fye.flipyourenglish.listeners;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.activities.CardReader;
import com.fye.flipyourenglish.activities.CardRemover;
import com.fye.flipyourenglish.activities.CardSelector;
import com.fye.flipyourenglish.activities.CardWriter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;


/**
 * Created by Anton_Kutuzau on 3/13/2017.
 */

@EBean
public class MainActivityListener implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    @RootContext
    protected Activity activity;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_cards:
                startActivity(CardReader.class);
                break;
            case R.id.add_cards:
                startActivity(CardWriter.class);
                break;
            case R.id.select_cards:
                startActivity(CardSelector.class);
                break;
            case R.id.delete_cards:
                startActivity(CardRemover.class);
                break;
        }
    }

    private void startActivity(Class clazz) {
        activity.startActivity(new Intent(activity, clazz));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        activity.startActivity(activity.getIntent());
        return true;
    }

}
