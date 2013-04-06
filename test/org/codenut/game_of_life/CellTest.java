package org.codenut.game_of_life;

import org.testng.Assert;
import org.testng.annotations.Test;


public class CellTest {

    @Test
    public void newCellIsDead() {
        Assert.assertFalse(new Cell(new Position(0,0)).isAlive());
    }

    @Test
    public void deadCell() {
        Cell deadCell = createDeadCell();
        Assert.assertFalse(deadCell.isAlive());
        Assert.assertTrue(deadCell.isDead());
    }

    @Test
    public void livingCell() {
        Cell livingCell = createLivingCell();
        Assert.assertTrue(livingCell.isAlive());
        Assert.assertFalse(livingCell.isDead());
    }

    @Test
    public void deadCellCanBeRevived() {
        Cell deadCell = createDeadCell();
        Assert.assertTrue(deadCell.revive().isAlive());
    }

    @Test
    public void livingCellCanBeKilled() {
        Cell livingCell = createLivingCell();
        Assert.assertTrue(livingCell.kill().isDead());
    }

    @Test
    public void cellRetainsPosition() {
        Cell cell = new Cell(new Position(4, 9));
        final Position position = cell.getPosition();
        Assert.assertEquals(4, position.getX());
        Assert.assertEquals(9, position.getY());
    }


    @Test
    public void markAlive() {
        Cell deadCell = createDeadCell();
        Assert.assertFalse(deadCell.isDirty());
        deadCell.markAlive();
        Assert.assertTrue(deadCell.isDirty());
        Assert.assertTrue(deadCell.isDead());
        deadCell.transition();
        Assert.assertTrue(deadCell.isAlive());
    }

    @Test
    public void markDead() {
        Cell livingCell = createLivingCell();
        Assert.assertFalse(livingCell.isDirty());
        livingCell.markDead();
        Assert.assertTrue(livingCell.isDirty());
        Assert.assertTrue(livingCell.isAlive());
        livingCell.transition();
        Assert.assertTrue(livingCell.isDead());
    }


    private Cell createDeadCell() {
        return new Cell(new Position(0,0), Cell.State.DEAD);
    }

    private Cell createLivingCell() {
        return new Cell(new Position(0,0), Cell.State.ALIVE);
    }
}