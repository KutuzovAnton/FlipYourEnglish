package com.fye.flipyourenglish.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.activities.LanguageChanger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Anton_Kutuzau on 5/7/2017.
 */

@EBean
public class Menu {

    public static final String APP_PREFERENCES = "settings";
    public static final long translationButtonSpeed = 600;
    public final static String LANGUAGE = "language";
    private final static String TRANSLATION_CARD_SPEED = "translationCardSpeed";
    private static String language;
    private static Long translationCardSpeed;

    @RootContext
    protected Activity activity;
    @Bean
    protected NavigationMenuView navigationView;
    private SharedPreferences mSettings;

    @AfterViews
    public void init() {
        initMenu();
        initSpinnerSpeed();
        initLanguage();
    }

    private void initMenu() {
        mSettings = activity.getSharedPreferences(Menu.APP_PREFERENCES, Context.MODE_PRIVATE);
        translationCardSpeed = mSettings.getLong(TRANSLATION_CARD_SPEED, 300);
        InputMethodSubtype ims = activity.getBaseContext().getSystemService(InputMethodManager.class).getCurrentInputMethodSubtype();
        String locale = ims.getLocale();
        String defaultLanguage = locale.equals(Language.RUSSIAN.getShortForm()) ? Language.RUSSIAN.getLanguage() : Language.ENGLISH.getLanguage();
        language = mSettings.getString(LANGUAGE, defaultLanguage);
    }

    public void saveMenu() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putLong(TRANSLATION_CARD_SPEED, translationCardSpeed);
        editor.putString(LANGUAGE, language);
        editor.apply();
    }

    private void initSpinnerSpeed() {
        List<String> values = Arrays.asList("100", "200", "300", "400", "600", "1000");
        Spinner spinner = (Spinner) navigationView.getMenu().findItem(R.id.menu_spinner).getActionView();
        spinner.setAdapter(new ArrayAdapter<>(activity.getBaseContext(), android.R.layout.simple_spinner_dropdown_item, values));
        spinner.setSelection(values.indexOf(String.valueOf(translationCardSpeed)));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Menu.translationCardSpeed = Long.valueOf(values.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initLanguage() {
        navigationView.getMenu().findItem(R.id.menu_language).setOnMenuItemClickListener(item -> {
            activity.startActivity(new Intent(activity, LanguageChanger.class));
            return true;
        });
    }

    public static long getTranslationCardSpeed() {
        return translationCardSpeed;
    }

}
