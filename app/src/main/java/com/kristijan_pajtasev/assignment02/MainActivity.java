package com.kristijan_pajtasev.assignment02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kristijan_pajtasev.assignment02.views.MemoryCards;

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

    public void initializeTextViews() {
        playerOneScore = (TextView)findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView)findViewById(R.id.playerTwoScore);

        setPlayerOneScore(0);
        setPlayerTwoScore(0);
    }

    public void setPlayerOneScore(int score) {
        playerOneScore.setText("Player 1: " + score);
    }

    public void setPlayerTwoScore(int score) {
        playerTwoScore.setText("Player 2: " + score);
    }

    public void setActivePlayerMessage(int playerNumber) {
        TextView statusLabel = findViewById(R.id.gameStatus);
        statusLabel.setText("Player " + playerNumber + " playing.");
    }

    private void setWindowDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        windowHeight = displayMetrics.heightPixels;
        windowWidth = displayMetrics.widthPixels;
    }
}
