package com.kristijan_pajtasev.assignment02.models;

import android.graphics.Paint;

/**
 * Model class for each card
 */
public class Card {
    private Paint color;
    private int image;
    private boolean isFlipped = false, isTemporaryFlipped;

    /**
     * Constructor for card class
     * @param color color to be used for background of card.
     * @param image image to be shown after card is flipped.
     */
    public Card(Paint color, int image) {
        this.color = color;
        this.image = image;
    }

    /**
     * Checks if card is already flipped.
     * @return true if card is flipped, false otherwise.
     */
    public boolean cardFlipped() {
        return isFlipped;
    }

    /**
     * Checks if card is only temporary flipped.
     * @return true if card is temporary flipped, false otherwise.
     */
    public boolean cardTemporaryFlipped() {
        return isTemporaryFlipped;
    }

    /**
     * Permanently flips card.
     */
    public void flipCard() {
        isTemporaryFlipped = false;
        isFlipped = true;
    }

    /**
     * Temporary flips card
     */
    public void temporaryFlipCard() {
        isTemporaryFlipped = true;
    }

    /**
     * Flips card to hidden state.
     */
    public void flipBack() {
        isTemporaryFlipped = false;
        isFlipped = false;
    }

    /**
     * Gets card background color
     * @return Paint used for background
     */
    public Paint getColor() {
        return color;
    }

    /**
     * Image to be shown when card is flipped
     * @return image to be shown
     */
    public int getImage() {
        return image;
    }
}