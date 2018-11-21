package com.kristijan_pajtasev.assignment02.models;

import android.graphics.Paint;

public class Card {
    Paint color;
    private boolean isFlipped = false, isTemporaryFlipped;

    public Card(Paint color) {
        this.color = color;
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
}