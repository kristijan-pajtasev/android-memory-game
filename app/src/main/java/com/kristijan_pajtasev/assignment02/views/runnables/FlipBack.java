package com.kristijan_pajtasev.assignment02.views.runnables;

import com.kristijan_pajtasev.assignment02.models.Card;
import com.kristijan_pajtasev.assignment02.views.MemoryCards;

import java.util.ArrayList;

public class FlipBack extends Thread {
    private ArrayList<Card> cards;
    MemoryCards view;

    public FlipBack(ArrayList<Card> cards, MemoryCards view) {
        this.cards = cards;
        this.view = view;
    }

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