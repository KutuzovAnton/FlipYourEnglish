package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.entities.Word;
import com.fye.flipyourenglish.listeners.GoBackListener;
import com.fye.flipyourenglish.menu.Menu;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.AutoResizeEditText;
import com.fye.flipyourenglish.utils.AutoResizeTextView;
import com.fye.flipyourenglish.utils.Utils;

import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

import static android.R.drawable.ic_dialog_alert;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardReader extends AppCompatActivity {

    private static final int ROTATION_SPEED = 1000;
    private List<Card> cards;
    private AutoResizeTextView textView;
    private AutoResizeEditText cardEditView;
    private ViewSwitcher cardSwitchView;
    private boolean isTranslate = true;
    private int currentCardIndex = 0;
    private CardRepository cardRepository;

    private void init() {
        ImageButton goBack = (ImageButton) findViewById(R.id.go_back);
        goBack.setOnClickListener(new GoBackListener(this));
        textView = ((AutoResizeTextView) findViewById(R.id.card));
        cardEditView = (AutoResizeEditText) findViewById(R.id.card_edit_view);
        cardSwitchView = (ViewSwitcher) findViewById(R.id.cardTextViewSwitcher);
        cardRepository = new CardRepository(this);
        cards = cardRepository.findActiveCards();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_cards);
        init();
        if (!cards.isEmpty()) {
            Collections.shuffle(cards);
            printWord();
            setOnTouchListener();
            setTextWatcher(cardEditView);
        } else {
            Utils.showSnackBar(this, "No cards", ic_dialog_alert, Gravity.BOTTOM);
        }
    }

    private void setOnTouchListener() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        cardSwitchView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                getNext();
                Utils.translation(point.x, cardSwitchView, () -> printWord(), Menu.getTranslationCardSpeed());
                isTranslate = true;
            }

            public void onSwipeLeft() {
                getPrev();
                Utils.translation(-point.x, cardSwitchView, () -> printWord(), Menu.getTranslationCardSpeed());
                isTranslate = true;
            }

            public void onClick() {
                isTranslate = !isTranslate;
                cardSwitchView.animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED).withEndAction(() -> {
                    cardSwitchView.setRotationX(isTranslate ? 270 : -270);
                    printWord();
                    cardSwitchView.animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED);
                });
            }
        });
    }

    private void getNext() {
        if (currentCardIndex == cards.size() - 1)
            currentCardIndex = 0;
        else
            currentCardIndex++;
    }

    private void getPrev() {
        if (currentCardIndex == 0)
            currentCardIndex = cards.size() - 1;
        else
            currentCardIndex--;
    }

    private void printWord() {
        textView.setText(getCurrentWord().getWord());
    }

    private void setTextWatcher(AutoResizeEditText word) {
        word.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                resolveVisibilityForFAB(word);
            }
        });
    }


    public void onClickEditCard(View view) {
        ViewSwitcher switcher = switchAndGetTextView(view);
        AutoResizeEditText cardEditView = (AutoResizeEditText) switcher.findViewById(R.id.card_edit_view);
        AutoResizeTextView cardView = (AutoResizeTextView) switcher.findViewById(R.id.card);
        cardEditView.setText(cardView.getText());
        findViewById(R.id.editCardFAB).setEnabled(false);
    }

    public void onClickConfirmText(View view) {
        ViewSwitcher viewSwitcher = switchAndGetTextView(textView);
        AutoResizeEditText cardEditView = (AutoResizeEditText) viewSwitcher.findViewById(R.id.card_edit_view);
        ((AutoResizeTextView) viewSwitcher.findViewById(R.id.card)).setText(cardEditView.getText().toString());
        getCurrentWord().setWord(cardEditView.getText().toString());
        cardRepository.update(cards.get(currentCardIndex));
        ((FloatingActionButton) findViewById(R.id.confirmCardFAB)).hide();
        findViewById(R.id.editCardFAB).setEnabled(true);
    }

    private ViewSwitcher switchAndGetTextView(View view) {
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.cardTextViewSwitcher);
        switcher.showNext();
        return switcher;
    }

    private void resolveVisibilityForFAB(AutoResizeEditText view) {
        if (!StringUtils.isEmpty(view.getText())) {
            Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.confirmCardFAB), View.VISIBLE);
        } else {
            Utils.showSnackBar(view.getContext(), "Word is empty");
            Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.confirmCardFAB), View.INVISIBLE);
        }
    }

    private Word getCurrentWord() {
        return isTranslate ? cards.get(currentCardIndex).getWordA() : cards.get(currentCardIndex).getWordB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cardRepository.close();
    }
}
