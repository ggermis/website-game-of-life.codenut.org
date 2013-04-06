package org.codenut.game_of_life.pattern;

import org.codenut.game_of_life.World;

public class Glider implements Pattern {
    @Override
    public void draw(World world, int left, int top) {
        world.getCellAt(left, top+2).revive();
        world.getCellAt(left+1, top).revive();
        world.getCellAt(left+1, top+2).revive();
        world.getCellAt(left+2, top+1).revive();
        world.getCellAt(left+2, top+2).revive();
    }
}
