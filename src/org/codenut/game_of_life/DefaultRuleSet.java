package org.codenut.game_of_life;

import java.util.List;

public class DefaultRuleSet implements RuleSet {

    @Override
    public void apply(final World world, Cell cell) {
        final List<Cell> livingNeighbours = world.getLivingNeighboursOf(cell);
        if (cell.isDead() && livingNeighbours.size() == 3) {
            world.markAlive(cell);
        } else if (cell.isAlive() && livingNeighbours.size() < 2 ) {
            world.markDead(cell);
        } else if (cell.isAlive() && livingNeighbours.size() > 3) {
            world.markDead(cell);
        }
    }
}
