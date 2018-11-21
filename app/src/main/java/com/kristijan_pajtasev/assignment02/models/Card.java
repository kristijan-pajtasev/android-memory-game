package com.kristijan_pajtasev.assignment02.models;

import android.graphics.Paint;

public class Card {
    Paint color;
    int image;
    private boolean isFlipped = false, isTemporaryFlipped;

    public Card(Paint color, int image) {
        this.color = color;
        this.image = image;
    }

    public boolean cardFlipped() {
        return isFlipped;
    }

    public boolean cardTemporaryFlipped() {
        return isTemporaryFlipped;
    }

    public void flipCard() {
        isTemporaryFlipped = false;
        isFlipped = true;
    }

    public void temporaryFlipCard() {
        isTemporaryFlipped = true;
    }

    public void flipBack() {
        isTemporaryFlipped = false;
        isFlipped = false;
    }

    public Paint getColor() {
        return color;
    }

    public int getImage() {
        return image;
    }
}