package com.kristijan_pajtasev.assignment02.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kristijan_pajtasev.assignment02.MainActivity;
import com.kristijan_pajtasev.assignment02.models.Card;
import com.kristijan_pajtasev.assignment02.views.runnables.FlipBack;

import java.util.ArrayList;

public class MemoryCards extends View {
    private ArrayList<Rect> cardsPlaces;
    private Paint blue, red, carrot, green, purple, black, midnightBlue, concrete, sunflower;
    private int windowHeight, windowWidth, rectSize, playerOneScore, playerTwoScore;
    private ArrayList<Card> cards;
    private boolean firstCardFlipped, clickDisabled, firstPlayerPlaying, isGameOver;
    private Context context;

    public MemoryCards(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public MemoryCards(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    public MemoryCards(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initialize();
    }

    public void initialize() {
        firstPlayerPlaying = true;
        isGameOver = false;
        playerOneScore = 0;
        playerTwoScore = 0;

        cardsPlaces = new ArrayList<>();
        cards = new ArrayList<>();
        firstCardFlipped = false;
        clickDisabled = false;

        black = getPaint(0xFF000000);
        blue = getPaint(0xff0000ff);
        green = getPaint(0xff00ff00);
        red = getPaint(0xffff0000);
        purple = getPaint(0xffff00ff);
        midnightBlue = getPaint(0xff2c3e50);
        concrete = getPaint(0xff95a5a6);
        sunflower = getPaint(0xfff1c40f);
        carrot = getPaint(0xffe67e22);

        setWindowDimensions();
        rectSize = windowHeight < windowWidth ? windowHeight / 4 : windowWidth / 4;
        rectSize -= 10;

        Paint colorPalet[] = {black, blue, midnightBlue, green, purple, concrete, sunflower, carrot};

        for (int i = 0; i < 16; i++) {
            cardsPlaces.add(new Rect(0, 0, rectSize, rectSize));
            cards.add(new Card(colorPalet[i / 2]));
        }
    }

    private void setActivePlayerLabel(int activePlayerNumber) {
        ((MainActivity)context).setActivePlayerMessage(activePlayerNumber);
    }


    private void setWindowDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        windowHeight = displayMetrics.heightPixels;
        windowWidth = displayMetrics.widthPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 16; i++) {
            canvas.save();
            canvas.translate((i % 4) * (rectSize + 10) + 5, (i/4) * (rectSize + 10) + 5);
            Paint paint = cards.get(i).cardFlipped() || cards.get(i).cardTemporaryFlipped() ? cards.get(i).getColor() : red;
            canvas.drawRect(cardsPlaces.get(i), paint);
            canvas.restore();
        }
    }

    private Paint getPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        return paint;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(isGameOver) return super.onTouchEvent(event);

        int actionMasked = event.getActionMasked();

        if (actionMasked == MotionEvent.ACTION_DOWN) {
            int pointerID = event.getPointerId(event.getActionIndex());
            if(clickDisabled) return super.onTouchEvent(event);
            int x  = (int)event.getX(pointerID) / rectSize;
            int y = (int)event.getY(pointerID) / rectSize;
            Log.d("MemoryCardView: ", "Action down event at (" + x + ", " + y + ")");
            int cardIndex = x + y * 4;
            if(cardIndex < cards.size())
                flipCard(cards.get(cardIndex));
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    public void flipCard(Card card) {
        if(!card.cardFlipped() && !card.cardTemporaryFlipped()) {
            card.temporaryFlipCard();
            if(firstCardFlipped) {
                firstCardFlipped = false;
                if(isMatch()) {
                    flipTemporaryFlippedCards();
                    updateScore();
                } else {
                    clickDisabled = true;
                    new FlipBack(cards, this).start();
                }
                firstPlayerPlaying = !firstPlayerPlaying;
                checkForEndGame();

                if(!isGameOver)
                    setActivePlayerLabel(firstPlayerPlaying ? 1 : 2);
            } else firstCardFlipped = true;
        }
    }

    private void checkForEndGame() {
        if(playerOneScore > 4 || playerTwoScore > 4 ||
                (playerOneScore == 4 && playerOneScore == playerTwoScore)) {
            isGameOver = true;
            if(playerOneScore == playerTwoScore) ((MainActivity)context).setWinningPlayerStatus(0);
            else {
                int winningPlayer = playerOneScore > playerTwoScore ? 1 : -1;
                ((MainActivity)context).setWinningPlayerStatus(winningPlayer);
            }

        }
    }

    public void updateScore() {
        if(firstPlayerPlaying) {
            playerOneScore++;
            ((MainActivity)context).setPlayerOneScore(playerOneScore);
        } else {
            playerTwoScore++;
            ((MainActivity)context).setPlayerTwoScore(playerTwoScore);
        }
    }

    public void flipTemporaryFlippedCards() {
        for(Card card: cards) if(card.cardTemporaryFlipped()) card.flipCard();
    }

    public boolean isMatch() {
        Paint paint = null;
        for(Card card: cards) {
            if(card.cardTemporaryFlipped() && null == paint) paint = card.getColor();
            else if(card.cardTemporaryFlipped() && null != paint) return paint.equals(card.getColor());
        }
        return false;
    }

    public void reset() {
        initialize();
        setActivePlayerLabel(1);
        invalidate();
    }

    public void enableClick() {
        clickDisabled = false;
    }
}



