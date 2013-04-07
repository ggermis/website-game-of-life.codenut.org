package org.codenut.game_of_life.pattern;

import org.codenut.game_of_life.World;

public class BeeHive implements Pattern {
    @Override
    public void draw(World world, int top, int left) {
        world.markAlive(top + 1, left);
        world.markAlive(top, left + 1);
        world.markAlive(top + 2, left + 1);
        world.markAlive(top, left + 2);
        world.markAlive(top + 2, left + 2);
        world.markAlive(top + 1, left + 3);
    }
}
