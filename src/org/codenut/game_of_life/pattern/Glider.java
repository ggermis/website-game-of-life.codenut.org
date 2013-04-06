package org.codenut.game_of_life.pattern;

import org.codenut.game_of_life.World;

public class Glider implements Pattern {
    @Override
    public void draw(World world, int left, int top) {
        world.markAliveAt(left, top + 2);
        world.markAliveAt(left + 1, top);
        world.markAliveAt(left + 1, top + 2);
        world.markAliveAt(left + 2, top + 1);
        world.markAliveAt(left + 2, top + 2);
    }
}
