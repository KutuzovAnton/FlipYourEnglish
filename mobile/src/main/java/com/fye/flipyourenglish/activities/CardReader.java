package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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

    private static final int ROTATION_SPEED = 1000;
    private static final int TRANSLATION_SPEED = 600;
    private List<Card> cards;
    private TextView textView;
    private boolean isTranslate = true;
    private int currentCardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cards = FileWorker.readCards(getFilesDir(), true);
        if (!cards.isEmpty()) {
            Collections.shuffle(cards);
            textView = ((TextView) findViewById(R.id.card));
            printWord();
            setOnTouchListener();
        } else {
            Utils.showSnackBar(this, "No cards", R.drawable.ic_done);
        }

        new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    ViewSwitcher viewSwitcher = switchTextView(textView);
                    ((TextView) viewSwitcher.findViewById(R.id.card)).setText(((EditText) viewSwitcher.findViewById(R.id.card_edit_view)).getText().toString());
                }
                return true;
            }
        };
    }

    private void setOnTouchListener() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        ((TextView) textView).setOnTouchListener(new OnSwipeTouchListener(this) {
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
                ((TextView) textView).animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED).withEndAction(() -> {
                    ((TextView) textView).setRotationX(isTranslate ? 270 : -270);
                    printWord();
                    ((TextView) textView).animate().rotationXBy(isTranslate ? 90 : -90).setDuration(ROTATION_SPEED);
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
        ((TextView) textView).setText(isTranslate ? cards.get(currentCardIndex).getWord1() : cards.get(currentCardIndex).getWord2());
    }

    public void onClickEditCard(View view) {
        ViewSwitcher switcher = switchTextView(view);
        EditText cardEditView = (EditText) switcher.findViewById(R.id.card_edit_view);
        TextView cardView = (TextView) switcher.findViewById(R.id.card);
        cardEditView.setText(cardView.getText());
    }

    private ViewSwitcher switchTextView(View view) {
        ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.cardTextViewSwitcher);
        switcher.showNext();
        return switcher;
    }
}
