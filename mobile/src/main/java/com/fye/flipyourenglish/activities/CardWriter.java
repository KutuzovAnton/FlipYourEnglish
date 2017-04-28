package com.fye.flipyourenglish.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.db.WordsTablesCreater;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.entities.Word;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.repositories.WordRepository;
import com.fye.flipyourenglish.utils.FileWorker;
import com.fye.flipyourenglish.utils.Utils;

import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardWriter extends AppCompatActivity {

    private CardRepository cardRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cardRepository = new CardRepository(this);
        ((Button)findViewById(R.id.add_card)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = new Card();
                card.setWordA(new Word(((EditText) findViewById(R.id.wordA)).getText().toString()));
                card.setWordB(new Word(((EditText) findViewById(R.id.wordB)).getText().toString()));
                cardRepository.save(card);
                Utils.showToast(getApplicationContext(),  "Card has been added");
        }});
    }

}
