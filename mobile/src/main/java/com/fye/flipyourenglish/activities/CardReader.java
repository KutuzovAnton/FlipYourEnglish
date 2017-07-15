package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.entities.Cards;
import com.fye.flipyourenglish.entities.Word;
import com.fye.flipyourenglish.listeners.AfterTextChangedListner;
import com.fye.flipyourenglish.listeners.OnSwipeTouchListener;
import com.fye.flipyourenglish.menu.Menu;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.AutoResizeEditText;
import com.fye.flipyourenglish.utils.AutoResizeTextView;
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

    private static final int ROTATION_SPEED = 300;
    private Cards cards;

    @ViewById(R.id.card)
    TextView textView;
    @ViewById(R.id.card_edit_view)
    EditText cardEditView;
    @ViewById(R.id.cardTextViewSwitcher)
    ViewSwitcher cardSwitchView;
    @ViewById(R.id.playWord)
    ImageButton playWord;
    @Bean
    CardRepository cardRepository;
    @Bean
    CardSpeaker cardSpeaker;
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

    @Click(R.id.playWord)
    protected void speak() {
        if (isTranslate) {
            cardSpeaker.getSpeaker().setPitch(0.9f);
            cardSpeaker.getSpeaker().speak(cards.getCurrent().getWordA().getWord(), TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            Utils.showSnackBar(this, "It's not english word", ic_dialog_alert, Gravity.BOTTOM);
        }
    }

    private void setOnTouchListener() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        cardSwitchView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                Utils.translation(point.x, playWord, null, null, Menu.getTranslationCardSpeed());
                Utils.translation(point.x, cardSwitchView, () -> printWord(cards.getNext()), () -> {
                    isTranslate = true;
                    playWord.animate().scaleY(1).scaleX(1).setDuration(1);
                    playWord.setVisibility(View.VISIBLE);
                }, Menu.getTranslationCardSpeed());
            }

            public void onSwipeLeft() {
                Utils.translation(-point.x, playWord, null, null, Menu.getTranslationCardSpeed());
                Utils.translation(-point.x, cardSwitchView, () -> printWord(cards.getPrev()), () -> {
                    isTranslate = true;
                    playWord.animate().scaleY(1).scaleX(1).setDuration(1);
                    playWord.setVisibility(View.VISIBLE);
                }, Menu.getTranslationCardSpeed());
            }

            public void onClick() {
                playWord.animate().scaleY(0).scaleX(0).setDuration(100).withEndAction(() -> {
                    playWord.setVisibility(View.INVISIBLE);
                    isTranslate = !isTranslate;
                    cardSwitchView.animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED).withEndAction(() -> {
                        cardSwitchView.setRotationX(isTranslate ? 270 : -270);
                        printWord(cards.getCurrent());
                        cardSwitchView.animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED).withEndAction(() -> {
                            if (isTranslate) {
                                playWord.animate().scaleY(1).scaleX(1).setDuration(100).withStartAction(() -> playWord.setVisibility(View.VISIBLE));
                            }
                        });
                    });
                });
            }
        });
    }

    private void printWord(Card card) {
        textView.setText(getCurrantCardSide(card).getWord());
    }

    public void onClickEditCard(View view) {
        playWord.setVisibility(View.INVISIBLE);
        ViewSwitcher switcher = switchAndGetTextView(view);
        AutoResizeEditText cardEditView = (AutoResizeEditText) switcher.findViewById(R.id.card_edit_view);
        AutoResizeTextView cardView = (AutoResizeTextView) switcher.findViewById(R.id.card);
        cardEditView.setText(cardView.getText());
        findViewById(R.id.editCardFAB).setEnabled(false);
    }

    public void onClickConfirmText(View view) {
        playWord.setVisibility(View.VISIBLE);
        ViewSwitcher viewSwitcher = switchAndGetTextView(textView);
        AutoResizeEditText cardEditView = (AutoResizeEditText) viewSwitcher.findViewById(R.id.card_edit_view);
        ((AutoResizeTextView) viewSwitcher.findViewById(R.id.card)).setText(cardEditView.getText().toString());
        getCurrantCardSide(cards.getCurrent()).setWord(cardEditView.getText().toString());
        cardRepository.update(cards.getCurrent());
        ((FloatingActionButton) findViewById(R.id.confirmCardFAB)).hide();
        findViewById(R.id.editCardFAB).setEnabled(true);
    }

    private ViewSwitcher switchAndGetTextView(View view) {
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.cardTextViewSwitcher);
        switcher.showNext();
        return switcher;
    }

    private void resolveVisibilityForFAB(EditText view) {
        if (view.getText() != null) {
    private void resolveVisibilityForFAB(AutoResizeEditText view) {
        if (!StringUtils.isEmpty(view.getText())) {
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
        cardSpeaker.getSpeaker().stop();
        cardSpeaker.getSpeaker().shutdown();
    }
}
