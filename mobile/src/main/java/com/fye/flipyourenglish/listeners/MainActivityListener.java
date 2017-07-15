package com.fye.flipyourenglish.listeners;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.activities.CardReader_;
import com.fye.flipyourenglish.activities.CardRemover_;
import com.fye.flipyourenglish.activities.CardSelector_;
import com.fye.flipyourenglish.activities.CardWriter_;

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
                startActivity(CardReader_.class);
                break;
            case R.id.add_cards:
                startActivity(CardWriter_.class);
                break;
            case R.id.select_cards:
                startActivity(CardSelector_.class);
                break;
            case R.id.delete_cards:
                startActivity(CardRemover_.class);
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
