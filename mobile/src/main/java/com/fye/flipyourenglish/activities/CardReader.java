package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.utils.FileWorker;
import com.fye.flipyourenglish.utils.Utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardReader extends AppCompatActivity {

    private static final int ROTATION_SPEED = 400;
    private static final int TRANSLATION_SPEED = 400;
    private List<Card> cards;
    private TextView textView;
    private boolean isTranslate = true;
    private int currentCardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cards = FileWorker.readCards(getFilesDir());
        Collections.shuffle(cards);
        textView = ((TextView)findViewById(R.id.card));
        printWord();
        setOnTouchListener();
    }

    private void setOnTouchListener() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        textView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                getNext();
                Utils.translation(point.x, textView, () -> printWord());
            }

            public void onSwipeLeft() {
                getPrev();
                Utils.translation(-point.x, textView, () -> printWord());
            }

            public void onClick() {
                isTranslate = !isTranslate;
                textView.animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED).withEndAction(() -> {
                    textView.setRotationX(isTranslate ? 270 : -270);
                    printWord();
                    textView.animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED);
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
        textView.setText(isTranslate ? cards.get(currentCardIndex).getWord1() : cards.get(currentCardIndex).getWord2());
    }

}
