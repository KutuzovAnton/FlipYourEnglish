package com.fye.flipyourenglish;

import android.app.Instrumentation;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.fye.flipyourenglish.repositories.CardRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

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