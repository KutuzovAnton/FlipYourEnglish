package com.fye.flipyourenglish.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RemoteViews.RemoteView;

import com.fye.flipyourenglish.R;

import java.util.Random;

/**
 * Created by Anton_Kutuzau on 4/1/2017.
 */
@RemoteView
public class CircleView extends View {

    private Paint innerCircle;
    private Paint outerCircle;
    private Random random;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        random = new Random();
        innerCircle = createCircle(context, Paint.Style.FILL, R.color.bright_greenOpacity);
        outerCircle = createCircle(context, Paint.Style.STROKE, R.color.colorAccent);
    }

    private Paint createCircle(Context context, Paint.Style style, int color) {
        Paint circle = new Paint();
        circle.setStyle(style);
        circle.setColor(ContextCompat.getColor(context, color));
        circle.setAntiAlias(true);
        return circle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int numOfCircles = 45;
        int maxRadius = 100;
        int minRadius = 20;

        for (int i = 0; i < numOfCircles; i++) {
            int radius = random.nextInt(maxRadius) + minRadius;
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            canvas.drawCircle(x, y, radius, innerCircle);
            canvas.drawCircle(x, y, radius, outerCircle);
        }

    }
}
