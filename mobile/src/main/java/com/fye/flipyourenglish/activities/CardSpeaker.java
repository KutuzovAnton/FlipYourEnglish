package com.fye.flipyourenglish.activities;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Locale;

/**
 * Created by Anton_Kutuzau on 7/13/2017.
 */

@EBean
public class CardSpeaker {

    @RootContext
    protected Context context;
    private TextToSpeech speaker;

    @AfterInject
    public void init() {
        speaker = new TextToSpeech(context, new SpeakListener());
    }

    public TextToSpeech getSpeaker() {
        return speaker;
    }

    private class SpeakListener implements TextToSpeech.OnInitListener {

        @Override
        public void onInit(int status) {
            speaker.setLanguage(new Locale("en"));
        }
    }
}
