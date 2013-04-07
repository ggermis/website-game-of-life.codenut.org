package org.codenut.game_of_life.pattern;

import org.codenut.game_of_life.World;

public class Blinker implements Pattern {
    @Override
    public void draw(World world, int top, int left) {
        world.markAlive(top, left + 1);
        world.markAlive(top + 1, left + 1);
        world.markAlive(top + 2, left + 1);
    }
}
