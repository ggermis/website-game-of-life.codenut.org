package org.codenut.game_of_life;

import android.app.Activity;
import android.os.Bundle;
import org.codenut.game_of_life.pattern.Glider;

public class GameOfLife extends Activity {

    private World world;
    private GameOfLifeView gameOfLifeView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        world = new World();
        new Glider().draw(world, 5, 3);

        gameOfLifeView = (GameOfLifeView) findViewById(R.id.game_of_life_view);
        gameOfLifeView.setWorld(world);
    }
}
