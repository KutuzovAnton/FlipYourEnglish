package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.builders.TextViewBuilder;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.Utils;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

public class CardRemover extends AppCompatActivity {

    private List<Card> cards;
    private CardRepository cardRepository;

    private void init() {
        cardRepository = new CardRepository(this);
        cards = cardRepository.findAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
        lParams.setMargins(0,2,0,0);
        for (Card card: cards) {
            ((LinearLayout)findViewById(R.id.list_cards_for_remove)).addView(createTextViewCard(card, point), lParams);
        }
    }

    private TextView createTextViewCard(Card card, Point point) {
        TextViewBuilder textViewBuilder = new TextViewBuilder(this);
        TextView textView = textViewBuilder
                .setSize(25)
                .setHint(card.getHint())
                .setColor(getResources().getColor(R.color.white))
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
        ((LinearLayout) findViewById(R.id.list_cards_for_remove)).removeView(textView);
        Optional<Card> findCard = cards.stream().filter(card -> textView.getText().equals(card.toString())).findFirst();
        if(findCard.isPresent()) {
            cardRepository.removeById(findCard.get().getId());
            cards.remove(findCard.get());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cardRepository.close();
    }

}
