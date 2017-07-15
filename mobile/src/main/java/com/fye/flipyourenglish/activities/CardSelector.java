package com.fye.flipyourenglish.activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fye.flipyourenglish.R;
import com.fye.flipyourenglish.entities.Card;
import com.fye.flipyourenglish.entities.Cards;
import com.fye.flipyourenglish.repositories.CardRepository;
import com.fye.flipyourenglish.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.fye.flipyourenglish.R.layout.activity_selecter_cards;

/**
 * Created by Anton_Kutuzau on 3/22/2017.
 */

@EActivity(activity_selecter_cards)
public class CardSelector extends AppCompatActivity {

    private Cards cards;
    @Bean
    protected CardRepository cardRepository;
    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewById(R.id.list_cards_for_select)
    protected ListView listView;

    @AfterViews
    protected void init() {
        setSupportActionBar(toolbar);
        cards = cardRepository.findAll();
        createArrayAdapter();
    }

    @Click(R.id.go_back)
    protected void goBack() {
        finish();
    }

    private void createArrayAdapter() {
        ArrayAdapter<Card> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.card, cards.getAsList());
        listView.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener((parent, view, position, id) -> updateStatus(view, adapter, position, view.isActivated()));
    }

    private void updateStatus(View view, ArrayAdapter<Card> adapter, int position, boolean isActive) {
        if(adapter.getItem(position) != null) {
            view.setBackgroundColor(ContextCompat.getColor(this, isActive ? R.color.bright_green : R.color.blue));
            view.setActivated(isActive);
            adapter.getItem(position).setActive(isActive ? 1 : 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cardRepository.updateAll(cards);
        cardRepository.close();
    }
}
