package com.fye.flipyourenglish.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.utils.FileWorker;

import java.util.ArrayList;
import java.util.List;

import static com.fye.flipyourenglish.R.layout.activity_selecter_cards;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */
public class CardSelector extends AppCompatActivity {

    private List<Card> passiveCards;
    private List<Card> activeCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_selecter_cards);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        passiveCards = FileWorker.readCards(getFilesDir(), false);
        activeCards = FileWorker.readCards(getFilesDir(), true);
        ArrayAdapter<Card> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.card, passiveCards);
        ((ListView)findViewById(R.id.list_cards_for_select)).setAdapter(adapter);
        ((ListView) findViewById(R.id.list_cards_for_select)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.isActivated()) {
                    view.setBackgroundColor(getResources().getColor(R.color.bright_green));
                    view.setActivated(false);
                    activeCards.remove(adapter.getItem(position));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.gray));
                    view.setActivated(true);
                    activeCards.add(adapter.getItem(position));
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileWorker.writeCard(getFilesDir(), activeCards, true);
    }
}
