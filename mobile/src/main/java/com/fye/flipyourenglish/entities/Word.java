package com.fye.flipyourenglish.entities;

/**
 * Created by Anton_Kutuzau on 4/19/2017.
 */

public class Word{

    private Long id;
    private String word;

    public Word() { }

    public Word(String word) {
        this.word = word;
    }

    public Word(Long id, String word) {
        this.id = id;
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }
}
