package org.codenut.game_of_life;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class WorldTest {

    @Test
    public void hasASize() {
        World world = new World(15, 25);
        Assert.assertEquals(15, world.getWidth());
        Assert.assertEquals(25, world.getHeight());
    }

    @Test
    public void defaultWorldSize() {
        World world = new World();
        Assert.assertEquals(100, world.getWidth());
        Assert.assertEquals(100, world.getHeight());
    }

    @Test
    public void getSpecificCell() {
        final World world = new World(10, 10);
        final Cell cell = world.getCellAt(3, 4);
        Assert.assertEquals(3, cell.getPosition().getX());
        Assert.assertEquals(4, cell.getPosition().getY());
    }

    @DataProvider(name = "neighbourCountDataProvider")
    public Object[][] neighbourCountDataProvider() {
        final World world = new World(10, 10);
        return new Object[][] {
                { world, new Cell(new Position(3, 2)), 8 }, // center
                { world, new Cell(new Position(0, 0)), 8 }, // top left
                { world, new Cell(new Position(3, 0)), 8 }, // top
                { world, new Cell(new Position(world.getWidth()-1, 0)), 8 }, // top right
                { world, new Cell(new Position(world.getWidth()-1, 3)), 8 }, // right
                { world, new Cell(new Position(world.getWidth()-1, world.getHeight()-1)), 8 }, // bottom right
                { world, new Cell(new Position(3, world.getHeight()-1)), 8 }, // bottom
                { world, new Cell(new Position(0, world.getHeight()-1)), 8 }, // bottom left
                { world, new Cell(new Position(0, 3)), 8 }, // left
        };
    }

    @Test(dataProvider = "neighbourCountDataProvider")
    public void countNeighbours(final World world, final Cell cell, final int expectedNeighbours) {
        Assert.assertEquals(expectedNeighbours, world.getNeighboursOf(cell).size());
    }

    @Test
    public void countLivingCells() {
        final World world = new World();
        world.markAlive(5, 3);
        world.markAlive(2, 1);
        world.markAlive(7, 2);
        world.transition();
        Assert.assertEquals(3, world.getLivingCells().size());
    }

    @Test
    public void dirtyCellsAreResetWhenWorldIsTransitioned() {
        final World world = new World();
        Assert.assertFalse(world.isDirty());
        world.markAlive(5, 2);
        world.markAlive(2, 1);
        Assert.assertTrue(world.isDirty());
        world.transition();
        Assert.assertFalse(world.isDirty());
    }

    @Test
    public void markingTheSameCellTwiceOnlyAddsItToDirtyCellsListOnce() {
        final World world = new World();
        world.markAlive(5, 2);
        world.markAlive(5, 2);
        Assert.assertEquals(1, world.getDirtyCells().size());
    }

    @Test
    public void positionsWrapWidth() {
        World world = new World(5, 5);
        Assert.assertEquals(world.getCellAt(6, 3).getPosition(), world.getCellAt(1, 3).getPosition());
        Assert.assertEquals(world.getCellAt(-2, 3).getPosition(), world.getCellAt(3, 3).getPosition());
    }

    @Test void positionsWrapHeight() {
        World world = new World(5, 5);
        Assert.assertEquals(world.getCellAt(3, 6).getPosition(), world.getCellAt(3, 1).getPosition());
        Assert.assertEquals(world.getCellAt(3, -2).getPosition(), world.getCellAt(3, 3).getPosition());
    }

}
