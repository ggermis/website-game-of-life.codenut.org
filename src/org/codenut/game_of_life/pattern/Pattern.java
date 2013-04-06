package org.codenut.game_of_life.pattern;

import org.codenut.game_of_life.World;

public interface Pattern {
    public void draw(World world, int top, int left);
}
