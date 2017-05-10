package com.fye.flipyourenglish.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.listeners.GoBackListener;
import com.fye.flipyourenglish.repositories.CardRepository;

import java.util.List;

import static com.fye.flipyourenglish.R.layout.activity_selecter_cards;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */
public class CardSelector extends AppCompatActivity {

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
        setContentView(activity_selecter_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        createArrayAdapter();
    }

    private void createArrayAdapter() {
        ListView listView = (ListView) findViewById(R.id.list_cards_for_select);
        ArrayAdapter<Card> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.card, cards);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (adapter.getItem(position) == null) {
                return;
            }
            if (view.isActivated()) {
                view.setBackgroundColor(getResources().getColor(R.color.bright_green));
                view.setActivated(false);
                adapter.getItem(position).setActive(0);
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.blue));
                view.setActivated(true);
                adapter.getItem(position).setActive(1);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cardRepository.updateAll(cards);
        cardRepository.close();
    }
}
