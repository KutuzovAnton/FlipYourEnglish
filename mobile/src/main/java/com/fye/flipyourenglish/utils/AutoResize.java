package com.fye.flipyourenglish.utils;

import android.content.res.Resources;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.fye.flipyourenglish.R;

/**
 * Created by Igor_Filler on 5/12/2017.
 */

class AutoResize {
    static final int NO_LINE_LIMIT = -1;
    private RectF mTextRect = new RectF(0.0f, 0.0f, 300.0f, 180.0f);

    private RectF mAvailableSpaceRect = new RectF(0.0f, 0.0f, 300.0f, 180.0f);

    private TextPaint mPaint = new TextPaint();

    private float mMaxTextSize = 180.0f;

    private float mSpacingMult = 1.0f;

    private float mSpacingAdd = 0.0f;

    private float mMinTextSize = 60.0f;

    private int mWidthLimit = 300;

    private int mMaxLines = 10;

    private int onTestSize(int suggestedSize, RectF availableSPace, String textCharsSeq) {
        mPaint.setTextSize(suggestedSize);
        if (mMaxLines == 1) {
            mTextRect.bottom = mPaint.getFontSpacing();
            mTextRect.right = mPaint.measureText(textCharsSeq);
        } else {
            StaticLayout layout = new StaticLayout(textCharsSeq, mPaint,
                    mWidthLimit, Layout.Alignment.ALIGN_NORMAL, mSpacingMult,
                    mSpacingAdd, true);

            // Return early if we have more lines
            if (layout.getLineCount() > mMaxLines) {
                return 1;
            }
            mTextRect.bottom = layout.getHeight();
            int maxWidth = -1;
            for (int i = 0; i < layout.getLineCount(); i++) {
                if (maxWidth < layout.getLineWidth(i)) {
                    maxWidth = (int) layout.getLineWidth(i);
                }
            }
            mTextRect.right = maxWidth;
        }

        mTextRect.offsetTo(0, 0);
        if (availableSPace.contains(mTextRect)) {

            // May be too small, don't worry we will find the best match
            return -1;
        } else {
            // too big
            return 1;
        }
    }

    private int binarySearch(int start, int end,
                             RectF availableSpace, String text) {
        int lastBest = start;
        int lo = start;
        int hi = end - 1;
        int mid = 0;
        while (lo <= hi) {
            mid = (lo + hi) >>> 1;
            int midValCmp = onTestSize(mid, availableSpace, text);
            if (midValCmp < 0) {
                lastBest = lo;
                lo = mid + 1;
            } else if (midValCmp > 0) {
                hi = mid - 1;
                lastBest = hi;
            } else {
                return mid;
            }
        }
        // Make sure to return the last best.
        // This is what should always be returned.
        return lastBest;

    }

    <T extends CharSequence> int adjustTextSize(T text) {
        return binarySearch((int) mMinTextSize, (int) mMaxTextSize, mAvailableSpaceRect, text.toString());
    }

    public RectF getmTextRect() {
        return mTextRect;
    }

    public void setmTextRect(RectF mTextRect) {
        this.mTextRect = mTextRect;
    }

    public RectF getmAvailableSpaceRect() {
        return mAvailableSpaceRect;
    }

    public void setmAvailableSpaceRect(RectF mAvailableSpaceRect) {
        this.mAvailableSpaceRect = mAvailableSpaceRect;
    }

    public TextPaint getmPaint() {
        return mPaint;
    }

    void setmPaint(TextPaint mPaint) {
        this.mPaint = mPaint;
    }

    public float getmMaxTextSize() {
        return mMaxTextSize;
    }

    void setmMaxTextSize(float mMaxTextSize) {
        this.mMaxTextSize = mMaxTextSize;
    }

    public float getmSpacingMult() {
        return mSpacingMult;
    }

    void setmSpacingMult(float mSpacingMult) {
        this.mSpacingMult = mSpacingMult;
    }

    public float getmSpacingAdd() {
        return mSpacingAdd;
    }

    void setmSpacingAdd(float mSpacingAdd) {
        this.mSpacingAdd = mSpacingAdd;
    }

    public float getmMinTextSize() {
        return mMinTextSize;
    }

    void setmMinTextSize(float mMinTextSize) {
        this.mMinTextSize = mMinTextSize;
    }

    public int getmWidthLimit() {
        return mWidthLimit;
    }

    public void setmWidthLimit(int mWidthLimit) {
        this.mWidthLimit = mWidthLimit;
    }

    public static int getNoLineLimit() {
        return NO_LINE_LIMIT;
    }

    int getmMaxLines() {
        return mMaxLines;
    }

    void setmMaxLines(int mMaxLines) {
        this.mMaxLines = mMaxLines;
    }
}
