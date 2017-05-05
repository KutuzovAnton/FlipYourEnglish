package com.fye.flipyourenglish.entities;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by Anton_Kutuzau on 3/20/2017.
 */

public class Card {

    private Long id;
    private Word wordA;
    private Word wordB;
    private Integer active;

    public Card() {
        wordA = new Word();
        wordB = new Word();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Word getWordA() {
        return wordA;
    }

    public void setWordA(Word wordA) {
        this.wordA = wordA;
    }

    public Word getWordB() {
        return wordB;
    }

    public void setWordB(Word wordB) {
        this.wordB = wordB;
    }

    public Spanned getHint() {
        return Html.fromHtml(wordA + " <i>" + "(" + wordB + ")" + "</i>");
    }

    @Override
    public String toString() {
        return wordA + " (" + wordB + ")";
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
