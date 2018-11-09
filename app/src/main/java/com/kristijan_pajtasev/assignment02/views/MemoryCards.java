package com.kristijan_pajtasev.assignment02.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class MemoryCards extends View {
    private ArrayList<Rect> cardsPlaces = new ArrayList<>();

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
        for (int i = 0; i < 16; i++) {
            cardsPlaces.set(i, new Rect(100, 100, 100, 100));
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 16; i++) {
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setColor(0xff0000);
            canvas.drawRect(cardsPlaces.get(i), p);
        }
    }
}
