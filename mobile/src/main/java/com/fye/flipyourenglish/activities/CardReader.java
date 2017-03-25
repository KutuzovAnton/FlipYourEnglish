package com.fye.flipyourenglish.activities;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.utils.FileWorker;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardReader extends AppCompatActivity {


    private static final int ROTATION_SPID = 700;
    private List<Card> cards;
    private boolean isTranslate = true;
    private int i = 0;
    private TextView textView;

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
                translation(point.x);
            }

            public void onSwipeLeft() {
                getPrev();
                translation(-point.x);
            }

            public void onClick() {
                isTranslate = !isTranslate;
                ((TextView) findViewById(R.id.card)).animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPID).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) findViewById(R.id.card)).setRotationX(isTranslate ? 270 : -270);
                        printWord();
                        ((TextView) findViewById(R.id.card)).animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPID);
                    }
                });
            }
        });
    }

    private void getNext() {
        if(i == cards.size()-1)
            i = 0;
        else
            i++;
    }

    private void getPrev() {
        if(i == 0)
            i = cards.size()-1;
        else
            i--;
    }

    private void translation(int size) {
        float x = textView.getX();
        ((TextView)findViewById(R.id.card)).animate().translationXBy(size).setDuration(600).withEndAction(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.card)).setX(-size);
                printWord();
                ((TextView)findViewById(R.id.card)).animate().translationXBy(size+x).setDuration(600);
            }
        });
    }

    private void printWord() {
        ((TextView) findViewById(R.id.card)).setText(isTranslate ? cards.get(i).getWord1() : cards.get(i).getWord2());
    }

}
