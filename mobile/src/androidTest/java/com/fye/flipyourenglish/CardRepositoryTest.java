package com.fye.flipyourenglish;

import com.fye.flipyourenglish.repositories.CardRepository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;

@RunWith(AndroidJUnit4.class)
public class CardRepositoryTest {

    private CardRepository cardRepository;

    @Before
    public void init() {
        cardRepository = new CardRepository(getContext());
    }

    @Test
    public void saveAndReadCard() throws Exception {

    }
}
