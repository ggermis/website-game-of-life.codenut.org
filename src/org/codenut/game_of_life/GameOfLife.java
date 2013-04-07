package org.codenut.game_of_life;

import android.app.Activity;
import android.os.Bundle;
import org.codenut.game_of_life.pattern.BeeHive;
import org.codenut.game_of_life.pattern.Blinker;
import org.codenut.game_of_life.pattern.Block;
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
        new Blinker().draw(world, 23, 12);
        new Block().draw(world, 27, 29);
        new BeeHive().draw(world, 13, 25);

        gameOfLifeView = (GameOfLifeView) findViewById(R.id.game_of_life_view);
        gameOfLifeView.setWorld(world);
    }
}
