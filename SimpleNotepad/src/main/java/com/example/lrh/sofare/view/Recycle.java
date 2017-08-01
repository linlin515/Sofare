package com.example.lrh.sofare.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.lrh.sofare.MainActivity;

/**
 * Created by LRH on 2017/7/5.
 */

public class Recycle extends View {
    private Paint paint;
    private int oneRadio;
    private int twoRadio;
    private int threeRadio;
    private int fourRadio;
    private int halfOfHeight;
    private int halfOFWidth;
    private int heightPiexls;

    public Recycle(Context context) {
        super(context);
        initProperties();
    }

    public Recycle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProperties();
    }

    public Recycle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProperties();
    }

    private void initProperties() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#75BECB"));
        canvas.drawRect(0, 0, halfOFWidth * 2, MainActivity.height / (float) 2.5, paint);
        paint.setColor(Color.parseColor("#7AC7D3"));
        canvas.drawCircle(halfOFWidth, MainActivity.height / (float) 2.5, oneRadio, paint);
        paint.setColor(Color.parseColor("#96D6DB"));
        canvas.drawCircle(halfOFWidth, MainActivity.height / (float) 2.5, twoRadio, paint);
        paint.setColor(Color.parseColor("#B6E3E7"));
        canvas.drawCircle(halfOFWidth, MainActivity.height / (float) 2.5, threeRadio, paint);
        paint.setColor(Color.parseColor("#CFEBF0"));
        canvas.drawCircle(halfOFWidth, MainActivity.height / (float) 2.5, fourRadio, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        halfOFWidth = width >> 1;
        halfOfHeight = height >> 1;
        oneRadio = halfOFWidth;
        twoRadio = halfOFWidth / 4 * 3;
        threeRadio = halfOFWidth / 2;
        fourRadio = halfOFWidth / 4;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
