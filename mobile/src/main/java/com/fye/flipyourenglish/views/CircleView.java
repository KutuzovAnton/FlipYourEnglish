package com.fye.flipyourenglish.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.UiThread;
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
    Paint paint = null;
    Paint paint1 = null;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint1 = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Random random = new Random();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.bright_greenOpacity));
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint1.setAntiAlias(true);
        for(int i = 0; i < 45; i++) {
            int radius;
            radius = random.nextInt(100)+10;
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            canvas.drawCircle(x, y, radius, paint);
            canvas.drawCircle(x, y, radius, paint1);
        }

    }
}
