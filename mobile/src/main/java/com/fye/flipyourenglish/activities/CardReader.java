package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.entities.Cards;
import com.fye.flipyourenglish.entities.Word;
import com.fye.flipyourenglish.listeners.AfterTextChangedListner;
import com.fye.flipyourenglish.listeners.OnSwipeTouchListener;
import com.fye.flipyourenglish.menu.Menu;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Collections;

import static android.R.drawable.ic_dialog_alert;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

@EActivity(R.layout.activity_reader_cards)
public class CardReader extends AppCompatActivity {

    private static final int ROTATION_SPEED = 1000;
    private Cards cards;

    @ViewById(R.id.card)
    protected TextView textView;
    @ViewById(R.id.card_edit_view)
    protected EditText cardEditView;
    @ViewById(R.id.cardTextViewSwitcher)
    protected ViewSwitcher cardSwitchView;
    @Bean
    protected CardRepository cardRepository;
    private boolean isTranslate = true;

    @AfterViews
    protected void init() {
        cards = cardRepository.findCards(true);
        if (!cards.isEmpty()) {
            Collections.shuffle(cards.getAsList());
            printWord(cards.getCurrent());
            setOnTouchListener();
            cardEditView.addTextChangedListener(new AfterTextChangedListner(() -> resolveVisibilityForFAB(cardEditView)));
        } else {
            Utils.showSnackBar(this, "No cards", ic_dialog_alert, Gravity.BOTTOM);
        }
    }

    @Click(R.id.go_back)
    protected void goBack() {
        finish();
    }

    private void setOnTouchListener() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        cardSwitchView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                Utils.translation(point.x, cardSwitchView, () -> printWord(cards.getNext()), Menu.getTranslationCardSpeed());
                isTranslate = true;
            }

            public void onSwipeLeft() {
                Utils.translation(-point.x, cardSwitchView, () -> printWord(cards.getPrev()), Menu.getTranslationCardSpeed());
                isTranslate = true;
            }

            public void onClick() {
                isTranslate = !isTranslate;
                cardSwitchView.animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED).withEndAction(() -> {
                    cardSwitchView.setRotationX(isTranslate ? 270 : -270);
                    printWord(cards.getCurrent());
                    cardSwitchView.animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED);
                });
            }
        });
    }

    private void printWord(Card card) {
        textView.setText(getCurrantCardSide(card).getWord());
    }

    public void onClickEditCard(View view) {
        ViewSwitcher switcher = switchAndGetTextView(view);
        EditText cardEditView = (EditText) switcher.findViewById(R.id.card_edit_view);
        TextView cardView = (TextView) switcher.findViewById(R.id.card);
        cardEditView.setText(cardView.getText());
    }

    public void onClickConfirmText(View view) {
        ViewSwitcher viewSwitcher = switchAndGetTextView(textView);
        EditText cardEditView = (EditText) viewSwitcher.findViewById(R.id.card_edit_view);
        ((TextView) viewSwitcher.findViewById(R.id.card)).setText(cardEditView.getText().toString());
        getCurrantCardSide(cards.getCurrent()).setWord(cardEditView.getText().toString());
        cardRepository.update(cards.getCurrent());
        ((FloatingActionButton) findViewById(R.id.confirmCardFAB)).hide();
    }

    private ViewSwitcher switchAndGetTextView(View view) {
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.cardTextViewSwitcher);
        switcher.showNext();
        return switcher;
    }

    private void resolveVisibilityForFAB(EditText view) {
        if (view.getText() != null) {
            Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.confirmCardFAB), View.VISIBLE);
        } else {
            Utils.showSnackBar(view.getContext(), "Word is empty");
            Utils.resolveVisibilityForFAB((FloatingActionButton) findViewById(R.id.confirmCardFAB), View.INVISIBLE);
        }
    }

    private Word getCurrantCardSide(Card card) {
        return isTranslate ? card.getWordA() : card.getWordB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cardRepository.close();
    }
}
