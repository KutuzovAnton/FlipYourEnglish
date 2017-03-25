package com.fye.flipyourenglish.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.utils.FileWorker;
import com.fye.flipyourenglish.utils.Utils;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardWriter extends AppCompatActivity {


    List<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cards = FileWorker.readCards(getFilesDir());
        ((Button)findViewById(R.id.add_card)).setOnClickListener(v -> {
            Card card = new Card();
            card.setWord1(((EditText)findViewById(R.id.word1)).getText().toString());
            card.setWord2(((EditText)findViewById(R.id.word2)).getText().toString());
            cards.add(card);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileWorker.writeCard(getFilesDir(), cards);
    }
}
