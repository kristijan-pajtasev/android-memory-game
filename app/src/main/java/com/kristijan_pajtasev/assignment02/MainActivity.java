package com.kristijan_pajtasev.assignment02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kristijan_pajtasev.assignment02.views.MemoryCards;

public class MainActivity extends AppCompatActivity {
    private int windowHeight, windowWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWindowDimensions();

        final MemoryCards memoryCardsView = (MemoryCards)findViewById(R.id.memoryCardsView);
        memoryCardsView.setLayoutParams(new RelativeLayout.LayoutParams(windowWidth, windowHeight));

        Button gameResetButton = (Button)findViewById(R.id.gameResetButton);
        gameResetButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                memoryCardsView.reset();
            }
        });
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
