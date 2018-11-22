package com.kristijan_pajtasev.assignment02.views.runnables;

import com.kristijan_pajtasev.assignment02.models.Card;
import com.kristijan_pajtasev.assignment02.views.MemoryCards;

import java.util.ArrayList;

/**
 * Separate thread for flipping back card used if unmatched cards are selected.
 */
public class FlipBack extends Thread {
    private ArrayList<Card> cards;
    MemoryCards view;

    /**
     * Constructor accepting array of cards and custom view instance.
     * @param cards displayed cards
     * @param view view containing game
     */
    public FlipBack(ArrayList<Card> cards, MemoryCards view) {
        this.cards = cards;
        this.view = view;
    }

    /**
     * Waits for 0.5s and then flips back unmatched cards.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(500);
            for(Card card: cards) if(card.cardTemporaryFlipped()) card.flipBack();
            view.enableClick();
            view.invalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}