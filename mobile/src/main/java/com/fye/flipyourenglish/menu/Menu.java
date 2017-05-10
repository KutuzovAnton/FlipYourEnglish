package com.fye.flipyourenglish.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fye.flipyourenglish.R;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Anton_Kutuzau on 5/7/2017.
 */

public class Menu {

    public static final String APP_PREFERENCES = "settings";
    private static String TRANSLATION_CARD_SPEED = "translationCardSpeed";
    private static String LANGUAGE = "language";
    private static String language;
    private static Long translationCardSpeed;
    private Context context;
    private NavigationView navigationView;
    private SharedPreferences mSettings;

    public static long translationButtonSpeed = 600;


    public Menu(Context context, NavigationView navigationView, SharedPreferences mSettings) {
        this.context = context;
        this.navigationView = navigationView;
        this.mSettings = mSettings;
        initMenu();
        initSpinnerSpeed();
        initLanguage();
    }

    private void initMenu() {
        translationCardSpeed = mSettings.getLong(TRANSLATION_CARD_SPEED, 300);
        language = mSettings.getString(LANGUAGE, "English");
    }

    public void saveMenu() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putLong(TRANSLATION_CARD_SPEED, translationCardSpeed);
        editor.putString(LANGUAGE, language);
        editor.apply();
    }

    private void initSpinnerSpeed() {
        initSpinner(R.id.menu_spinner, Arrays.asList("100", "200", "300", "400", "600", "1000"), this::setTranslationCardSpeed, String.valueOf(translationCardSpeed));
    }

    private void initLanguage() {
        initSpinner(R.id.menu_language, Arrays.asList("English", "Russian"), this::setLanguage, language);
    }

    private void initSpinner(int id, List<String> values, Function<String, String> function, String defaultValue) {
        Spinner spinner = (Spinner) navigationView.getMenu().findItem(id).getActionView();
        spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, values));
        spinner.setSelection(values.indexOf(defaultValue));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                function.apply(values.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public static long getTranslationCardSpeed() {
        return translationCardSpeed;
    }

    private String setTranslationCardSpeed(String translationCardSpeed) {
        Menu.translationCardSpeed = Long.valueOf(translationCardSpeed);
        return translationCardSpeed;
    }

    private String setLanguage(String language) {
        Menu.language = language;
        return language;
    }

    public static String getLanguage() {
        return language;
    }
}
