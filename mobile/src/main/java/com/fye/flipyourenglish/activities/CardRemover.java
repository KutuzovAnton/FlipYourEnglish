package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.builders.TextViewBuilder;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.utils.FileWorker;
import com.fye.flipyourenglish.utils.Utils;
import java.util.List;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardRemover extends AppCompatActivity {

    private List<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cards = FileWorker.readCards(getFilesDir());
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        cards.forEach(card -> ((LinearLayout)findViewById(R.id.list_cards)).addView(createCard(card, point), lParams));
    }

    private TextView createCard(Card card, Point point) {
        TextViewBuilder textViewBuilder = new TextViewBuilder(this);
        TextView textView = textViewBuilder
                .setText(card.getWord1() + " - " + card.getWord2())
                .setSize(30)
                .build();
        textView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                Utils.translation(point.x, textView, () -> removeCard(textView));
            }

            public void onSwipeLeft() {
                Utils.translation(-point.x, textView, () -> removeCard(textView));
            }
        });
        return textView;
    }

    private void removeCard(TextView textView) {
        ((LinearLayout) findViewById(R.id.list_cards)).removeView(textView);
        cards.removeIf(card -> textView.getText().equals(card.getWord1() + " - " + card.getWord2()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileWorker.writeCard(getFilesDir(), cards);
    }


}
