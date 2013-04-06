package org.codenut.game_of_life;


import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultRuleSetTest {
    @Test
    public void livingCellWithFewerThanTwoNeighboursDies() {
        final RuleSet rules = new DefaultRuleSet();
        final World world = new World(10, 10);
        final Cell cell = world.getCellAt(5, 2).revive();
        Assert.assertTrue(cell.isAlive());
        rules.apply(world, cell);
        world.transition(cell);
        Assert.assertTrue(cell.isDead());
    }

    @Test
    public void livingCellWithMoreThanThreeNeighboursDies() {
        final RuleSet rules = new DefaultRuleSet();
        final World world = new World(10, 10);
        final Cell cell = world.getCellAt(5, 2).revive();
        world.getCellAt(5, 3).revive();
        world.getCellAt(6, 3).revive();
        world.getCellAt(6, 2).revive();
        world.getCellAt(6, 1).revive();
        Assert.assertTrue(cell.isAlive());
        rules.apply(world, cell);
        world.transition(cell);
        Assert.assertTrue(cell.isDead());
    }

    @Test
    public void deadCellWithThreeLivingNeighboursRevives() {
        final RuleSet rules = new DefaultRuleSet();
        final World world = new World(10, 10);
        final Cell cell = world.getCellAt(5, 2);
        world.getCellAt(5, 3).revive();
        world.getCellAt(6, 3).revive();
        world.getCellAt(6, 2).revive();
        Assert.assertTrue(cell.isDead());
        rules.apply(world, cell);
        world.transition(cell);
        Assert.assertTrue(cell.isAlive());
    }
}
