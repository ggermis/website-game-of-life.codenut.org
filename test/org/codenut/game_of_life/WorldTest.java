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
                { world, new Cell(new Position(0, 0)), 3 }, // top left
                { world, new Cell(new Position(3, 0)), 5 }, // top
                { world, new Cell(new Position(world.getWidth()-1, 0)), 3 }, // top right
                { world, new Cell(new Position(world.getWidth()-1, 3)), 5 }, // right
                { world, new Cell(new Position(world.getWidth()-1, world.getHeight()-1)), 3 }, // bottom right
                { world, new Cell(new Position(3, world.getHeight()-1)), 5 }, // bottom
                { world, new Cell(new Position(0, world.getHeight()-1)), 3 }, // bottom left
                { world, new Cell(new Position(0, 3)), 5 }, // left
        };
    }

    @Test(dataProvider = "neighbourCountDataProvider")
    public void countNeighbours(final World world, final Cell cell, final int expectedNeighbours) {
        Assert.assertEquals(expectedNeighbours, world.getNeighboursOf(cell).size());
    }

    @Test
    public void countLivingCells() {
        final World world = new World();
        world.getCellAt(5, 3).revive();
        world.getCellAt(2, 1).revive();
        world.getCellAt(7, 2).revive();
        Assert.assertEquals(3, world.getLivingCells().size());
    }

}
