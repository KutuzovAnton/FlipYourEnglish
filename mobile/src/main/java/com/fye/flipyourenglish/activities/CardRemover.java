package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.builders.TextViewBuilder;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.FileWorker;
import com.fye.flipyourenglish.utils.Utils;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardRemover extends AppCompatActivity {

    private List<Card> cards;
    private CardRepository cardRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cardRepository = new CardRepository(this);
        cards = cardRepository.findAll();
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        for (Card card: cards) {
            ((LinearLayout)findViewById(R.id.list_cards_for_remove)).addView(createTextViewCard(card, point), lParams);
        }
    }

    private TextView createTextViewCard(Card card, Point point) {
        TextViewBuilder textViewBuilder = new TextViewBuilder(this);
        TextView textView = textViewBuilder
                .setText(card.getWordA() + " - " + card.getWordB())
                .setSize(30)
                .build();
        ((TextView)textView).setOnTouchListener(new OnSwipeTouchListener(this) {
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
        ((LinearLayout) findViewById(R.id.list_cards_for_remove)).removeView(textView);
        Optional<Card> findCard = cards.stream().filter(card -> textView.getText().equals(card.getWordA() + " - " + card.getWordB())).findFirst();
        cardRepository.removeById(findCard.get().getId());
        cards.remove(findCard.get());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
