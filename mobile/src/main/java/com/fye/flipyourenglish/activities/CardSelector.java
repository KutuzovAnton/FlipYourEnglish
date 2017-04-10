package com.fye.flipyourenglish.activities;

import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
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

public class CardSelector extends AppCompatActivity {

    private List<Card> cards;
    private List<Card> selectCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecter_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cards = FileWorker.readCards(getFilesDir());
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        for(Card card: cards){
            ((LinearLayout)findViewById(R.id.list_cards)).addView(createCard(card, point), lParams);
        }
    }

    private TextView createCard(Card card, Point point) {
        TextViewBuilder textViewBuilder = new TextViewBuilder(this);
        TextView textView = textViewBuilder
                .setText(card.getWord1() + " - " + card.getWord2())
                .setSize(30)
                .build();
        textView.setOnClickListener(v -> {
            int selectColor = getResources().getColor(R.color.blue);
            if(((ColorDrawable) textView.getBackground()).getColor() == selectColor)
            {
                if (Build.VERSION.SDK_INT < 23) {
                    textView.setTextAppearance(getApplicationContext(), R.style.MyButton);
                } else {
                    textView.setTextAppearance(R.style.MyButton);
                }
                // selectCards.remove(card);
            } else {
                textView.setBackgroundColor(selectColor);
                //  selectCards.add(card);
            }
        });
        return textView;
    }

   /* private void saveCard(TextView textView) {
        ((LinearLayout) findViewById(R.id.list_cards)).removeView(textView);
        cards.removeIf(card -> textView.getText().equals(card.getWord1() + " - " + card.getWord2()));
    }*/

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        FileWorker.writeCard(getFilesDir(), cards);
    }*/


}
