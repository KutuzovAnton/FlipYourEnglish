package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.builders.TextViewBuilder;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.listeners.GoBackListener;
import com.fye.flipyourenglish.menu.Menu;
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
        ImageButton goBack = (ImageButton) findViewById(R.id.go_back);
        goBack.setOnClickListener(new GoBackListener(this));
        cardRepository = new CardRepository(this);
        cards = cardRepository.findAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
        lParams.setMargins(0, 2, 0, 0);
        for (Card card : cards) {
            ((LinearLayout) findViewById(R.id.list_cards_for_remove)).addView(createTextViewCard(card, point), lParams);
        }
    }

    private TextView createTextViewCard(Card card, Point point) {
        TextViewBuilder textViewBuilder = new TextViewBuilder(this);
        TextView textView = textViewBuilder
                .setSize(30)
                .setHint(card.getHint())
                .setColor(ContextCompat.getColor(this, R.color.white))
                .build();
        textView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                Utils.translation(point.x, textView, () -> removeCard(textView), Menu.getTranslationCardSpeed());
            }

            public void onSwipeLeft() {
                Utils.translation(-point.x, textView, () -> removeCard(textView), Menu.getTranslationCardSpeed());
            }
        });
        return textView;
    }

    private void removeCard(TextView textView) {
        ((LinearLayout) findViewById(R.id.list_cards_for_remove)).removeView(textView);
        Optional<Card> findCard = cards.stream().filter(card -> textView.getText().equals(card.toString())).findFirst();
        if (findCard.isPresent()) {
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
