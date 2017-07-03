package com.fye.flipyourenglish.entities;

import java.util.List;

/**
 * Created by Anton_Kutuzau on 7/1/2017.
 */

public class Cards {

    private List<Card> cards;
    private int indexOfCurrentCard;

    public Cards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCurrent() {
        return getCard();
    }

    public Card getNext() {
        if (indexOfCurrentCard == cards.size() - 1)
            indexOfCurrentCard = 0;
        else
            indexOfCurrentCard++;
        return getCard();
    }

    public Card getPrev() {
        if (indexOfCurrentCard == 0)
            indexOfCurrentCard = cards.size() - 1;
        else
            indexOfCurrentCard--;
        return getCard();
    }

    private Card getCard() {
        if(cards == null || cards.isEmpty()) {
            return null;
        }
        return getCard();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public List<Card> getAsList() {
        return cards;
    }
}
