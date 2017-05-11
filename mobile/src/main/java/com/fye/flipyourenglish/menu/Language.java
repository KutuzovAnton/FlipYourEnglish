package com.fye.flipyourenglish.menu;

import java.util.Arrays;

/**
 * Created by Anton_Kutuzau on 5/11/2017.
 */

public enum Language {
    ENGLISH("Eglish","en"),
    RUSSIAN("Russian","ru");

    private String language;
    private String shortForm;

    Language(String language, String shortForm) {
        this.language = language;
        this.shortForm = shortForm;
    }

    public String getLanguage() {
        return language;
    }

    public String getShortForm() {
        return shortForm;
    }

    public static String getShortFormByLong(String longName) {
        return Arrays.asList(Language.values()).stream().filter(lan -> longName.equals(lan.getLanguage())).findFirst().get().getShortForm();
    }
}
