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
import com.fye.flipyourenglish.utils.FileWorker;
import com.fye.flipyourenglish.utils.Utils;

import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardWriter extends AppCompatActivity {


    private List<Card> cards;
    private TextInputEditText word1;
    private TextInputEditText word2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cards = FileWorker.readCards(getFilesDir(), false);
        resolveVisibilityofFAB(R.id.deleteWord1, View.INVISIBLE);
        resolveVisibilityofFAB(R.id.deleteWord2, View.INVISIBLE);
        ((Button) findViewById(R.id.add_card)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = new Card();
                card.setWord1(((TextInputEditText) findViewById(R.id.word1)).getText().toString());
                card.setWord2(((TextInputEditText) findViewById(R.id.word2)).getText().toString());
                cards.add(card);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileWorker.writeCard(getFilesDir(), cards, false);
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
                    resolveVisibilityofFAB(R.id.deleteWord1, View.VISIBLE);
                } else {
                    Utils.showSnackBar(view.getContext(), "Word is empty");
                    resolveVisibilityofFAB(R.id.deleteWord1, View.INVISIBLE);
                }
                break;
            case (R.id.word2):
                if (!textView.getText().toString().isEmpty()) {
                    resolveVisibilityofFAB(R.id.deleteWord2, View.VISIBLE);
                } else {
                    Utils.showSnackBar(view.getContext(), "Word is empty");
                    resolveVisibilityofFAB(R.id.deleteWord2, View.INVISIBLE);
                }
                break;
        }
    }

    private void resolveVisibilityofFAB(int viewById, int visible) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(viewById);
        if (visible == View.INVISIBLE) {
            fab.hide();
        } else {
            fab.show();
        }
    }
}
