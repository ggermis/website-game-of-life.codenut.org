package org.codenut.game_of_life;

import android.app.Activity;
import android.os.Bundle;
import org.codenut.game_of_life.pattern.BeeHive;
import org.codenut.game_of_life.pattern.Blinker;
import org.codenut.game_of_life.pattern.Block;
import org.codenut.game_of_life.pattern.Glider;

public class GameOfLife extends Activity {

    private GameOfLifeView gameOfLifeView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gameOfLifeView = (GameOfLifeView) findViewById(R.id.game_of_life_view);
    }
}
