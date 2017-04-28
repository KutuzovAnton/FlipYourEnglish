package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.FileWorker;
import com.fye.flipyourenglish.utils.Utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardReader extends AppCompatActivity {

    private static final int ROTATION_SPEED = 1000;
    private static final int TRANSLATION_SPEED = 600;
    private List<Card> cards;
    private TextView textView;
    private boolean isTranslate = true;
    private int currentCardIndex = 0;
    private CardRepository cardRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cardRepository = new CardRepository(this);
        cards = cardRepository.findActiveCards();
        if(!cards.isEmpty()) {
            Collections.shuffle(cards);
            textView = ((TextView)findViewById(R.id.card));
            printWord();
            setOnTouchListener();
        } else {
            Utils.showToast(getApplicationContext(), "No cards");
        }
    }

    private void setOnTouchListener() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        ((TextView)textView).setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                getNext();
                Utils.translation(point.x, textView, () -> printWord());
                isTranslate = true;
            }

            public void onSwipeLeft() {
                getPrev();
                Utils.translation(-point.x, textView, () -> printWord());
                isTranslate = true;
            }

            public void onClick() {
                isTranslate = !isTranslate;
                ((TextView)textView).animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) textView).setRotationX(isTranslate ? 270 : -270);
                        printWord();
                        ((TextView) textView).animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED);
                    }
                });
            }
        });
    }

    private void getNext() {
        if(currentCardIndex == cards.size()-1)
            currentCardIndex = 0;
        else
            currentCardIndex++;
    }

    private void getPrev() {
        if(currentCardIndex == 0)
            currentCardIndex = cards.size()-1;
        else
            currentCardIndex--;
    }

    private void printWord() {
        ((TextView)textView).setText(isTranslate ? cards.get(currentCardIndex).getWordA().getWord() : cards.get(currentCardIndex).getWordB().getWord());
    }

}
