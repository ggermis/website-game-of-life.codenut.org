package org.codenut.game_of_life;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import org.codenut.game_of_life.pattern.Glider;

public class GameOfLife extends Activity {

    private World world;
    private GameOfLifeView gameOfLifeView;

    private class GameLoop extends Thread {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "HELLO!", Toast.LENGTH_LONG).show();
            world.tick();
            gameOfLifeView.postInvalidate();
            world.tick();
            Toast.makeText(getApplicationContext(), "HELLO AGAIN!", Toast.LENGTH_LONG).show();
//            Canvas c = null;
//            for (int i=0; i<10; i++) {
//                try {
//                    world.tick();
//                    sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        world = new World(20,20);
        new Glider().draw(world, 5, 3);

        gameOfLifeView = (GameOfLifeView) findViewById(R.id.game_of_life_view);
        gameOfLifeView.setWorld(world);

        new GameLoop().run();
    }
}
