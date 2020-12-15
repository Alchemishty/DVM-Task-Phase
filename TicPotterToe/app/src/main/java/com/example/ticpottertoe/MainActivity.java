package com.example.ticpottertoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int active = 0;
    boolean activeGame = true;

    Button resetGame;
    GridLayout gridLayout;
    TextView winnerStatus;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //columns
            {0, 4, 8}, {2, 4, 6} //diagonals
    };

    public void click(View view) {

        ImageView iv = (ImageView) view;

        int tagPosition = Integer.parseInt(iv.getTag().toString());

        if (gameState[tagPosition] == 2 && activeGame) {

            iv.animate().alpha(1).setDuration(500);

            gameState[tagPosition] = active;

            //0:P1, 1:P2, 2:Empty
            if (active == 1) {
                iv.setImageResource(R.drawable.voldemort);
                active = 0;
            } else if (active == 0){
                iv.setImageResource(R.drawable.harry_potter);
                active = 1;
            }

            if(isTied())
            {
                winnerStatus.setText("A tied duel");
                resetGame.setVisibility(View.VISIBLE);
            }

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {

                    activeGame = false;

                    if (active == 1) {
                        winnerStatus.setText("The Boy lives again");
                    } else if (active == 0) {
                        winnerStatus.setText("He must not be named");
                    }
                    resetGame.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    public void playAgain (View view){
        resetGame.setVisibility(View.INVISIBLE);
        activeGame = true;
        winnerStatus.setText("~Expecto Patronum~");
        active = 0;

        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.animate().alpha(0).setDuration(500);
            imageView.setImageDrawable(null);
        }

        for (int i = 0; i < gameState.length; i++) {
           gameState[i] = 2;
        }
    }

    public boolean isTied()
    {
        for (int i =0; i < gameState.length; i++)
        {
            if (gameState[i] == 2)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetGame = (Button) findViewById(R.id.resetGame);
        winnerStatus = (TextView) findViewById(R.id.winnerStatus);
    }
}