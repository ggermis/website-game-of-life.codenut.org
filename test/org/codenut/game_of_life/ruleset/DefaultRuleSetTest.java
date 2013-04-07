package org.codenut.game_of_life.ruleset;


import org.codenut.game_of_life.Cell;
import org.codenut.game_of_life.World;
import org.testng.Assert;
import org.testng.annotations.Test;


public class DefaultRuleSetTest {

    @Test
    public void livingCellWithFewerThanTwoNeighboursDies() {
        final RuleSet rules = new DefaultRuleSet();
        final World world = new World(10, 10);
        final Cell cell = world.markAlive(5, 2);
        world.transition();
        Assert.assertTrue(cell.isAlive());
        rules.apply(world, cell);
        world.transition();
        Assert.assertTrue(cell.isDead());
    }

    @Test
    public void livingCellWithMoreThanThreeNeighboursDies() {
        final RuleSet rules = new DefaultRuleSet();
        final World world = new World(10, 10);
        final Cell cell = world.markAlive(5, 2);
        world.markAlive(5, 3);
        world.markAlive(6, 3);
        world.markAlive(6, 2);
        world.markAlive(6, 1);
        world.transition();
        Assert.assertTrue(cell.isAlive());
        rules.apply(world, cell);
        world.transition();
        Assert.assertTrue(cell.isDead());
    }

    @Test
    public void deadCellWithThreeLivingNeighboursRevives() {
        final RuleSet rules = new DefaultRuleSet();
        final World world = new World(10, 10);
        final Cell cell = world.getCellAt(5, 2);
        world.markAlive(5, 3);
        world.markAlive(6, 3);
        world.markAlive(6, 2);
        world.transition();
        Assert.assertTrue(cell.isDead());
        rules.apply(world, cell);
        world.transition();
        Assert.assertTrue(cell.isAlive());
    }
}
