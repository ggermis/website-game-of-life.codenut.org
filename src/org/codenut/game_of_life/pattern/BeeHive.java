package org.codenut.game_of_life.pattern;

import org.codenut.game_of_life.World;

public class BeeHive implements Pattern {
    @Override
    public void draw(World world, int top, int left) {
        world.markAliveAt(top+1, left);
        world.markAliveAt(top, left+1);
        world.markAliveAt(top+2, left+1);
        world.markAliveAt(top, left+2);
        world.markAliveAt(top+2, left+2);
        world.markAliveAt(top+1, left+3);
    }
}
