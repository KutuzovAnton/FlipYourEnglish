package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.builders.TextViewBuilder;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.entities.Cards;
import com.fye.flipyourenglish.listeners.OnSwipeTouchListener;
import com.fye.flipyourenglish.menu.Menu;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Optional;

import static com.fye.flipyourenglish.utils.Utils.getWindowsSize;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

@EActivity(R.layout.activity_remover_cards)
public class CardRemover extends AppCompatActivity {

    private Cards cards;
    @Bean
    protected CardRepository cardRepository;
    @ViewById(R.id.list_cards_for_remove)
    LinearLayout linearLayout;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    private void init() {
        setSupportActionBar(toolbar);
        cards = cardRepository.findAll();
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
        lParams.setMargins(0, 2, 0, 0);
        for (Card card : cards.getAsList()) {
            linearLayout.addView(createTextViewCard(card, getWindowsSize(this)), lParams);
        }
    }

    @Click(R.id.go_back)
    protected void goBack() {
        finish();
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
        linearLayout.removeView(textView);
        Optional<Card> findCard = cards.getAsList().stream().filter(card -> textView.getText().equals(card.toString())).findFirst();
        if (findCard.isPresent()) {
            cardRepository.removeById(findCard.get().getId());
            cards.getAsList().remove(findCard.get());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cardRepository.close();
    }

}
