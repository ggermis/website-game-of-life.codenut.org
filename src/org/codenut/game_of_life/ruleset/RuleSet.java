package org.codenut.game_of_life.ruleset;


import org.codenut.game_of_life.Cell;
import org.codenut.game_of_life.World;

public interface RuleSet {
    public void apply(final World world, Cell cell);
}
