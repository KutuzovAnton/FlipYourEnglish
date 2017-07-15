package com.fye.flipyourenglish.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
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
    private Random random;
    private Context context;


    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(context.getDrawable(R.drawable.ground));
        this.context = context;
        random = new Random();
        innerCircle = createCircle(Paint.Style.FILL_AND_STROKE);
    }

    private Paint createCircle(Paint.Style style) {
        Paint circle = new Paint();
        circle.setStyle(style);
        return circle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int numOfCircles = 60;
        int maxRadius = 85;
        int minRadius = 25;
        for (int i = 0; i < numOfCircles; i++) {
            int radius = random.nextInt(maxRadius) + minRadius;
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            innerCircle.setShader(new RadialGradient(x, y,
                    radius, ContextCompat.getColor(context, R.color.less_bright_green), ContextCompat.getColor(context, R.color.bright_green), Shader.TileMode.MIRROR));
            canvas.drawCircle(x, y, radius, innerCircle);
        }
    }
}
