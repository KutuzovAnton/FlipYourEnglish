package com.fye.flipyourenglish.menu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.listeners.MainActivityListener;
import com.fye.flipyourenglish.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import static com.fye.flipyourenglish.menu.Menu.translationButtonSpeed;

/**
 * Created by Anton_Kutuzau on 6/27/2017.
 */

@EBean
public class NavigationMenuView {

    @ViewById(R.id.nav_view)
    NavigationView navigationView;
    @ViewById(R.id.main_activity)
    View mainView;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @RootContext
    Activity activity;
    @Bean
    MainActivityListener mainActivityListener;

    @AfterViews
    protected void initNavigationView() {
        initButtons();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(mainActivityListener);
    }

    private void initButtons() {
        int i = 0;
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        for (View view : mainView.getTouchables()) {
            int startPosition = (int) (Math.pow(-1, i++) * point.x);
            view.setX(-startPosition);
            Utils.translationAnimation(startPosition, view, translationButtonSpeed);
            view.setOnClickListener(mainActivityListener);
        }
    }

    public android.view.Menu getMenu() {
        return navigationView.getMenu();
    }

}
