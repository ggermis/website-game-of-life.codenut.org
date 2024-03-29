package org.codenut.game_of_life;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class GameOfLife extends Activity implements View.OnClickListener {

    private GameOfLifeView gameOfLifeView;
    private Button stopButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gameOfLifeView = (GameOfLifeView) findViewById(R.id.game_of_life_view);
        gameOfLifeView.setOnTouchListener(gameOfLifeView);

        stopButton = (Button) findViewById(R.id.button);
        stopButton.setOnClickListener(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        gameOfLifeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameOfLifeView.resume();
    }


    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        if (button.getText().equals("Stop")) {
            gameOfLifeView.pause();
            button.setText("Start");
        } else {
            gameOfLifeView.resume();
            button.setText("Stop");
        }
    }
}
