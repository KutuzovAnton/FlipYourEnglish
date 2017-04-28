package com.fye.flipyourenglish.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.Utils;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardWriter extends AppCompatActivity {

    private CardRepository cardRepository;
    private TextInputEditText word1;
    private TextInputEditText word2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.deleteWord1), View.INVISIBLE);
        Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.deleteWord2), View.INVISIBLE);
        cardRepository = new CardRepository(this);
        ((Button)findViewById(R.id.add_card)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = new Card();
                card.getWordA().setWord(((TextInputEditText) findViewById(R.id.word1)).getText().toString());
                card.getWordB().setWord(((TextInputEditText) findViewById(R.id.word2)).getText().toString());
                cardRepository.save(card);
                Utils.showSnackBar(v.getContext(), "Card has been added", R.drawable.ic_done);
            }
        });

        word1 = (TextInputEditText) findViewById(R.id.word1);
        word2 = (TextInputEditText) findViewById(R.id.word2);

        setTextWatcher(word1);
        setTextWatcher(word2);
    }

    private void setTextWatcher(TextInputEditText word) {
        word.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                onClickEditText(word);
            }
        });
    }

    public void onClickDeleteWord(View view) {
        switch (view.getId()) {
            case (R.id.deleteWord1):
                ((TextInputEditText) findViewById(R.id.word1)).setText("");
                break;
            case (R.id.deleteWord2):
                ((TextInputEditText) findViewById(R.id.word2)).setText("");
                break;
        }
    }

    public void onClickEditText(View view) {
        TextInputEditText textView = (TextInputEditText) view;
        switch (textView.getId()) {
            case (R.id.word1):
                if (!textView.getText().toString().isEmpty()) {
                    Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.deleteWord1), View.VISIBLE);
                } else {
                    Utils.showSnackBar(view.getContext(), "Word is empty");
                    Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.deleteWord1), View.INVISIBLE);
                }
                break;
            case (R.id.word2):
                if (!textView.getText().toString().isEmpty()) {
                    Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.deleteWord2), View.VISIBLE);
                } else {
                    Utils.showSnackBar(view.getContext(), "Word is empty");
                    Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.deleteWord2), View.INVISIBLE);
                }
                break;
        }
    }
}
