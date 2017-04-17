package com.fye.flipyourenglish.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.utils.FileWorker;
import com.fye.flipyourenglish.utils.Utils;

import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardWriter extends AppCompatActivity {


    private List<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cards = FileWorker.readCards(getFilesDir(), false);
        ((Button)findViewById(R.id.add_card)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Card card = new Card();
            card.setWord1(((EditText)findViewById(R.id.word1)).getText().toString());
            card.setWord2(((EditText)findViewById(R.id.word2)).getText().toString());
            cards.add(card);
            Utils.showToast(getApplicationContext(),  "Card has been added");
        }});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileWorker.writeCard(getFilesDir(), cards, false);
    }
}
