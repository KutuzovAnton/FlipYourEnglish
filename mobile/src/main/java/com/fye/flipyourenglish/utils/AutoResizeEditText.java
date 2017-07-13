package com.fye.flipyourenglish.utils;

/**
 * Created by Igor_Filler on 5/10/2017.
 */

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

import static com.fye.flipyourenglish.utils.Utils.autoResizer;

public class AutoResizeEditText extends EditText {


    public AutoResizeEditText(Context context) {
        super(context);
        initialize();
    }

    public AutoResizeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public AutoResizeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        autoResizer.setmPaint(new TextPaint(getPaint()));
        autoResizer.setmMaxTextSize(getTextSize());
        if (autoResizer.getmMaxLines() == 0) {
            autoResizer.setmMaxLines(AutoResize.NO_LINE_LIMIT);
        }
    }

    @Override
    public void setTextSize(float size) {
        if (autoResizer != null) {
            autoResizer.setmMaxTextSize(size);
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, autoResizer.adjustTextSize(getText()));
        }
    }


    @Override
    public void setTextSize(int unit, float size) {
        Context c = getContext();
        Resources r;

        if (c == null) {
            r = Resources.getSystem();
        } else {
            r = c.getResources();
        }
        if (autoResizer != null) {
            autoResizer.setmMaxTextSize(TypedValue.applyDimension(unit, size, r.getDisplayMetrics()));
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, autoResizer.adjustTextSize(getText()));
        }
    }

    @Override
    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        if (autoResizer != null) {
            autoResizer.setmSpacingMult(mult);
            autoResizer.setmSpacingAdd(add);
        }
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start,
                                 final int before, final int after) {
        super.onTextChanged(text, start, before, after);
        if (autoResizer != null) {
            int textSize = autoResizer.adjustTextSize(getText());
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldwidth,
                                 int oldheight) {
        super.onSizeChanged(width, height, oldwidth, oldheight);
        if (autoResizer != null) {
            if (width != oldwidth || height != oldheight) {
                super.setTextSize(TypedValue.COMPLEX_UNIT_PX, autoResizer.adjustTextSize(getText()));
            }
        }
    }
}
