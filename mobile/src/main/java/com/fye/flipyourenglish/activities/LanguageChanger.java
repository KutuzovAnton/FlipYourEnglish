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

/**
 * Created by Anton_Kutuzau on 5/11/2017.
 */

public class LanguageChanger extends AppCompatActivity {

    private RadioButton english;
    private RadioButton russian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        russian = (RadioButton) findViewById(R.id.radioButtonRussian);
        english = (RadioButton) findViewById(R.id.radioButtonEnglish);
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
