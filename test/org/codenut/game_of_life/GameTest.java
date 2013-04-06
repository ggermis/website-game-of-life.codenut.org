package org.codenut.game_of_life;


import org.testng.annotations.Test;

public class GameTest {
    @Test
    public void gameApi() {
        World world = new World(4, 10);
        world.show();

//        Game game = new Game(new World(100, 100), new DefaultRuleSet());
//        game.start();
    }
}
