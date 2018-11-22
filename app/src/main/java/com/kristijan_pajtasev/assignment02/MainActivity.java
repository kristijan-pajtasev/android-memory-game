package com.kristijan_pajtasev.assignment02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kristijan_pajtasev.assignment02.views.MemoryCards;


/**
 * MainActivity class
 * This is main activity of application. It starts game and sets all labels outside of
 * MemoryCards custom view.
 */
public class MainActivity extends AppCompatActivity {
    private int windowHeight, windowWidth;
    private TextView playerOneScore, playerTwoScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWindowDimensions();

        final MemoryCards memoryCardsView = (MemoryCards)findViewById(R.id.memoryCardsView);
        RelativeLayout.LayoutParams layoutParams;
        if(windowHeight > windowWidth)
            layoutParams = new RelativeLayout.LayoutParams(windowWidth, windowWidth);
        else
            layoutParams = new RelativeLayout.LayoutParams(windowHeight, windowHeight);

        memoryCardsView.setLayoutParams(layoutParams);

        initializeTextViews();
        setActivePlayerMessage(1);

        Button gameResetButton = (Button)findViewById(R.id.gameResetButton);
        gameResetButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                memoryCardsView.reset();
                setPlayerOneScore(0);
                setPlayerTwoScore(0);
            }
        });
    }

    /**
     * Initializes score TextView to initial value of 0.
     */
    public void initializeTextViews() {
        playerOneScore = (TextView)findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView)findViewById(R.id.playerTwoScore);

        setPlayerOneScore(0);
        setPlayerTwoScore(0);
    }

    /**
     * Sets player 1 score TextView to given value.
     * @param score current score of player 1 to be shown.
     */
    public void setPlayerOneScore(int score) {
        playerOneScore.setText("" + score);
    }

    /**
     * Sets player 2 score TextView to given value.
     * @param score current score of player 2 to be shown.
     */
    public void setPlayerTwoScore(int score) {
        playerTwoScore.setText("" + score);
    }

    /**
     * Sets status TextView to show winning player.
     * @param playerOrder number of player that won. If draw then 0.
     */
    public void setWinningPlayerStatus(int playerOrder) {
        TextView statusLabel = findViewById(R.id.gameStatus);
        switch(playerOrder) {
            case 0:
                statusLabel.setText("It is a draw");
                break;
            case 1:
            case 2:
                statusLabel.setText("Player " + playerOrder + " won");
                break;
        }
    }

    /**
     * Sets status TextView to show which player is active.
     * @param playerNumber number to be shown as active player.
     */
    public void setActivePlayerMessage(int playerNumber) {
        TextView statusLabel = findViewById(R.id.gameStatus);
        statusLabel.setText("Player " + playerNumber + " playing");
    }

    /**
     * Gets window size and sets class properties to those values.
     */
    private void setWindowDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        windowHeight = displayMetrics.heightPixels;
        windowWidth = displayMetrics.widthPixels;
    }
}
