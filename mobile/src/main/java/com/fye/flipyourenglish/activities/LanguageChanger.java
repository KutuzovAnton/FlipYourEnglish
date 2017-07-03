package com.fye.flipyourenglish.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;

import com.fye.flipyourenglish.MainActivity;
import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.menu.Language;
import com.fye.flipyourenglish.menu.Menu;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


/**
 * Created by Anton_Kutuzau on 5/11/2017.
 */

@EActivity(R.layout.activity_language)
public class LanguageChanger extends AppCompatActivity {

    @ViewById(R.id.radioButtonEnglish)
    RadioButton english;
    @ViewById(R.id.radioButtonRussian)
    RadioButton russian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        russian.setOnClickListener(v -> changeLanguageAndReturn(Language.RUSSIAN));
        english.setOnClickListener(v -> changeLanguageAndReturn(Language.ENGLISH));
    }

    private void changeLanguageAndReturn(Language language) {
        SharedPreferences.Editor editor = getSharedPreferences(Menu.APP_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putString(Menu.LANGUAGE, language.getLanguage());
        editor.apply();
        startActivity(new Intent(this, MainActivity.class));
    }

}
