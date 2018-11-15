package com.kristijan_pajtasev.assignment02.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MemoryCards extends View {
    private ArrayList<Rect> cardsPlaces = new ArrayList<>();
    private Paint blue, red, green, purple, black;

    public MemoryCards(Context context) {
        super(context);
        initialize();
    }

    public MemoryCards(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MemoryCards(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public void initialize() {
        black = getPaint(0xFF000000);
        blue = getPaint(0xff0000ff);
        green = getPaint(0xff00ff00);
        red = getPaint(0xffff0000);
        purple = getPaint(0xffff00ff);

        for (int i = 0; i < 16; i++) {
            cardsPlaces.add(new Rect(0, 0, 200, 200));
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 16; i++) {
            canvas.save();
            canvas.translate((i % 4) * 250, ((int)(i/4)) * 250);
            canvas.drawRect(cardsPlaces.get(i), red);
        }
    }

    private Paint getPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        return paint;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        int pointerID = event.getPointerId(event.getActionIndex());

        if (actionMasked == MotionEvent.ACTION_DOWN) {
            float x  = event.getX(pointerID);
            float y = event.getY(pointerID);
            Log.d("MemoryCardView: ", "Action down event at (" + x + ", " + y + ")");
        } else if(actionMasked == MotionEvent.ACTION_MOVE) {
            Log.d("MemoryCardView: ", "Action move event");

        } else if(actionMasked == MotionEvent.ACTION_POINTER_DOWN){
            Log.d("MemoryCardView: ", "Action pointer down event");
        } else if(actionMasked == MotionEvent.ACTION_POINTER_UP) {
            Log.d("MemoryCardView: ", "Action pointer up event");
        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
