package com.kristijan_pajtasev.assignment02.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.kristijan_pajtasev.assignment02.MainActivity;
import com.kristijan_pajtasev.assignment02.R;
import com.kristijan_pajtasev.assignment02.models.Card;
import com.kristijan_pajtasev.assignment02.views.runnables.FlipBack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * MemoryCards, custom view class used for displaying cards and gameplay.
 */
public class MemoryCards extends View {
    private ArrayList<Rect> cardsPlaces;
    private Paint hiddenCardBackgroundPaint;
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

    /**
     * Initializes all initial variable state.
     */
    public void initialize() {
        firstPlayerPlaying = true;
        isGameOver = false;
        playerOneScore = 0;
        playerTwoScore = 0;

        cardsPlaces = new ArrayList<>();
        cards = new ArrayList<>();
        firstCardFlipped = false;
        clickDisabled = false;

        hiddenCardBackgroundPaint = getPaint(0xff37474F);

        setWindowDimensions();
        rectSize = windowHeight < windowWidth ? windowHeight / 4 : windowWidth / 4;
        rectSize -= 10;

        Paint colorPallet[] = getColors();
        int[] cardImages = {
            R.drawable.ic_android, R.drawable.ic_beach, R.drawable.ic_child,
            R.drawable.ic_directions_bike, R.drawable.ic_directions_boat, R.drawable.ic_fire,
            R.drawable.ic_person, R.drawable.ic_train
        };

        for (int i = 0; i < 16; i++) {
            cardsPlaces.add(new Rect(0, 0, rectSize, rectSize));
            cards.add(new Card(colorPallet[i / 2], cardImages[i / 2]));
        }
        Collections.shuffle(cards);
    }

    /**
     * Creates all required card colors
     * @return array of paints used as card backgrounds
     */
    private Paint[] getColors() {
        Paint black = getPaint(0xFF000000);
        Paint blue = getPaint(0xff0277BD);
        Paint green = getPaint(0xff2E7D32);
        Paint deepPurple = getPaint(0xff4527A0);
        Paint red = getPaint(0xffC62828);
        Paint pink = getPaint(0xffAD1457);
        Paint sunflower = getPaint(0xfff1c40f);
        Paint grey = getPaint(0xff424242);
        Paint[] paints =  {black, blue, red, green, deepPurple, pink, sunflower, grey};
        return paints;
    }

    /**
     * Calls parent to set active player label.
     * @param activePlayerNumber number or player to be shown as active.
     */
    private void setActivePlayerLabel(int activePlayerNumber) {
        ((MainActivity)context).setActivePlayerMessage(activePlayerNumber);
    }

    /**
     * Reads and sets screen dimensions used to calculate card size.
     */
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
            drawRect(canvas, i);
        }
    }

    /**
     * Draws card for given position
     * @param canvas where card will be drawn
     * @param index order of card in a list
     */
    private void drawRect(Canvas canvas, int index) {
        canvas.save();
        Card card = cards.get(index);
        canvas.translate((index % 4) * (rectSize + 10) + 5, (index/4) * (rectSize + 10) + 5);
        Paint paint = card.cardFlipped() || card.cardTemporaryFlipped() ?
                card.getColor() : hiddenCardBackgroundPaint;
        canvas.drawRect(cardsPlaces.get(index), paint);

        int cardImage = !(card.cardTemporaryFlipped() || card.cardFlipped()) ?
                R.drawable.ic_flip_to_front : card.getImage();
        drawCardIcon(canvas, cardImage);

        canvas.restore();
    }

    /**
     * Draws image over given card.
     * @param canvas where image is draw
     * @param image resource that is being draw over card
     */
    public void drawCardIcon(Canvas canvas, int image) {
        int left = 20,
            right = rectSize - 20,
            bottom = rectSize - 20,
            top = 20;

        Drawable d = getResources().getDrawable(image);
        d.setBounds(left, top, right, bottom);
        d.draw(canvas);
    }

    /**
     * Creates paint for given hex code
     * @param color hex code of color
     * @return new created Paint for given hex
     */
    private Paint getPaint(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        return paint;
    }

    /**
     * Handles ACTION_DOWN event. Calculates card clicked based on click location and calls card
     * select handler.
     * @param event occurred.
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        if(isGameOver || clickDisabled) return super.onTouchEvent(event);

        int actionMasked = event.getActionMasked();

        if (actionMasked == MotionEvent.ACTION_DOWN) {
            int pointerID = event.getPointerId(event.getActionIndex());
            int x  = (int)event.getX(pointerID) / rectSize;
            int y = (int)event.getY(pointerID) / rectSize;

            Log.d("MemoryCardView: ", "Action down event at (" + x + ", " + y + ")");
            handleClickAction(x, y);
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    /**
     * Handler for card click. If valid click tries to flip it.
     * @param x card row for click
     * @param y card column for click
     */
    public void handleClickAction(int x, int y) {
        int cardIndex = x + y * 4;
        if(cardIndex < cards.size())
            flipCard(cards.get(cardIndex));
    }

    /**
     * Flips card if not already flipped.
     * @param card card to be flipped.
     */
    public void flipCard(Card card) {
        if(!card.cardFlipped() && !card.cardTemporaryFlipped()) {
            card.temporaryFlipCard();
            if(firstCardFlipped) {
                handleSecondCardFlip();
            } else firstCardFlipped = true;
        }
    }

    /**
     * Flips second card. If second is match permanently flips them otherwise flips them back.
     * Checks for game over.
     */
    public void handleSecondCardFlip() {
        firstCardFlipped = false;
        if(isMatch()) {
            flipTemporaryFlippedCards();
            updateScore();
        } else {
            clickDisabled = true;
            new FlipBack(cards, this).start();
        }
        checkForEndGame();

        if(!isGameOver) {
            firstPlayerPlaying = !firstPlayerPlaying;
            setActivePlayerLabel(firstPlayerPlaying ? 1 : 2);
        }
    }

    /**
     * Checking for end game. If any player has score > 5 or both have 4, game is over.
     */
    private void checkForEndGame() {
        if(playerOneScore > 4 || playerTwoScore > 4 ||
                (playerOneScore == 4 && playerOneScore == playerTwoScore)) {
            isGameOver = true;
            if(playerOneScore == playerTwoScore) ((MainActivity)context).setWinningPlayerStatus(0);
            else {
                int winningPlayer = playerOneScore > playerTwoScore ? 1 : 2;
                ((MainActivity)context).setWinningPlayerStatus(winningPlayer);
            }

        }
    }

    /**
     * Increments score of current player.
     */
    public void updateScore() {
        if(firstPlayerPlaying) {
            playerOneScore++;
            ((MainActivity)context).setPlayerOneScore(playerOneScore);
        } else {
            playerTwoScore++;
            ((MainActivity)context).setPlayerTwoScore(playerTwoScore);
        }
    }

    /**
     * Temporary flips card before before checking if there is match.
     */
    public void flipTemporaryFlippedCards() {
        for(Card card: cards) if(card.cardTemporaryFlipped()) card.flipCard();
    }

    /**
     * Check if temporary flipped cards are match.
     * @return boolean if there is match
     */
    public boolean isMatch() {
        Paint paint = null;
        for(Card card: cards) {
            if(card.cardTemporaryFlipped() && null == paint) paint = card.getColor();
            else if(card.cardTemporaryFlipped() && null != paint) return paint.equals(card.getColor());
        }
        return false;
    }

    /**
     * Resets game.
     */
    public void reset() {
        initialize();
        setActivePlayerLabel(1);
        invalidate();
    }

    /**
     * Enables or disables allowed click.
     */
    public void enableClick() {
        clickDisabled = false;
    }
}



