package com.fye.flipyourenglish.listeners;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Anton_Kutuzau on 7/3/2017.
 */

public class AfterTextChangedListner implements TextWatcher {

    Runnable function;

    public AfterTextChangedListner(Runnable function) {
        this.function = function;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        function.run();
    }
}
