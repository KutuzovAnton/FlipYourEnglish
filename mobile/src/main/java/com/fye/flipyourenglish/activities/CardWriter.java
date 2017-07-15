package com.fye.flipyourenglish.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.listeners.AfterTextChangedListner;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


import java.util.Locale;

import static android.R.drawable.ic_dialog_alert;
import static com.fye.flipyourenglish.R.drawable.ic_done;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

@EActivity(R.layout.activity_writer_cards)
public class CardWriter extends AppCompatActivity {

    @ViewById(R.id.word1)
    TextInputEditText word1;
    @ViewById(R.id.word2)
    TextInputEditText word2;
    @ViewById(R.id.deleteWord1)
    FloatingActionButton floatingActionButton1;
    @ViewById(R.id.deleteWord2)
    FloatingActionButton floatingActionButton2;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @Bean
    CardRepository cardRepository;

    @AfterViews
    protected void init() {
        setSupportActionBar(toolbar);
        Utils.resolveVisibilityForFAB(floatingActionButton1, View.INVISIBLE);
        Utils.resolveVisibilityForFAB(floatingActionButton2, View.INVISIBLE);
        addLanguageValidate(word1, true);
        addLanguageValidate(word2, false);
        word1.addTextChangedListener(new AfterTextChangedListner(() -> onClickEditText(word1)));
        word2.addTextChangedListener(new AfterTextChangedListner(() -> onClickEditText(word2)));
    }

    @Click(R.id.go_back)
    protected void goBack() {
        finish();
    }

    @Click(R.id.add_card)
    public void addCard() {
        cardRepository.saveIfAbsent(word1.getText().toString(), word2.getText().toString());
        Utils.showSnackBar(this, "Card has been added", ic_done, Gravity.BOTTOM);
    }

    private void addLanguageValidate(TextInputEditText word, boolean isEnglish) {
        word.setOnTouchListener((v, event) -> {
            String locale = Locale.getDefault().getLanguage();
            word.onTouchEvent(event);
            if ((isEnglish && !locale.equals("en_US")) || (!isEnglish && !locale.equals("ru"))) {
                Utils.showSnackBar(v.getContext(), "Change language to English", ic_dialog_alert, Gravity.TOP);
                return false;
            }
            return true;
        });
    }

    public void onClickDeleteWord(View view) {
        switch (view.getId()) {
            case (R.id.deleteWord1):
                word1.setText("");
                break;
            case (R.id.deleteWord2):
                word2.setText("");
                break;
        }
    }

    public void onClickEditText(View view) {
        TextInputEditText textView = (TextInputEditText) view;
        FloatingActionButton currantFloatingActionButton = null;
        if(textView.getId() == R.id.word1) {
            currantFloatingActionButton = floatingActionButton1;
        } else if(textView.getId() == R.id.word2) {
            currantFloatingActionButton = floatingActionButton2;
        }
        if (currantFloatingActionButton != null && !textView.getText().toString().isEmpty()) {
            Utils.resolveVisibilityForFAB(currantFloatingActionButton, View.VISIBLE);
        } else {
            Utils.showSnackBar(view.getContext(), "Word is empty");
            Utils.resolveVisibilityForFAB(currantFloatingActionButton, View.INVISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cardRepository.close();
    }
}
