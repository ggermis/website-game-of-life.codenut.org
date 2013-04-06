package org.codenut.game_of_life;


public interface RuleSet {
    public void apply(final World world, Cell cell);
}
