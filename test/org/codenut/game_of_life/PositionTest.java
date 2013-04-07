package org.codenut.game_of_life;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;


public class PositionTest {

    @Test
    public void hasCoordinates() {
        Position position = new Position(5, 8);
        Assert.assertEquals(5, position.getX());
        Assert.assertEquals(8, position.getY());
    }


    @DataProvider(name = "neighbourPositionDataProvider")
    public Object[][] neighbourPositionDataProvider() {
        final Position position = new Position(5, 3);
        return new Object[][] {
                { position, Border.NORTH, new Position(position.getX(), position.getY()-1)},
                { position, Border.NORTH_EAST, new Position(position.getX()+1, position.getY()-1)},
                { position, Border.EAST, new Position(position.getX()+1, position.getY())},
                { position, Border.SOUTH_EAST, new Position(position.getX()+1, position.getY()+1)},
                { position, Border.SOUTH, new Position(position.getX(), position.getY()+1)},
                { position, Border.SOUTH_WEST, new Position(position.getX()-1, position.getY()+1)},
                { position, Border.WEST, new Position(position.getX()-1, position.getY())},
                { position, Border.NORTH_WEST, new Position(position.getX()-1, position.getY()-1)},
        };
    }

    @Test(dataProvider = "neighbourPositionDataProvider")
    public void neighbourPosition(final Position position, final Border border, final Position expectedPosition) {
        Assert.assertEquals(expectedPosition, position.getNeighbourPosition(border));
    }

    @Test
    public void positionsAreEqual() {
        Assert.assertEquals(new Position(0,0), new Position(0,0));
        Assert.assertEquals(new Position(5,0), new Position(5,0));
        Assert.assertEquals(new Position(0,3), new Position(0,3));
        Assert.assertEquals(new Position(-2,0), new Position(-2,0));
        Assert.assertEquals(new Position(0,-4), new Position(0,-4));
        Assert.assertEquals(new Position(-29,-13), new Position(-29,-13));
        Assert.assertNotEquals(new Position(-5, 4), new Position(20, 0));
    }

    @Test
    public void allNeighbourPositions() {
        List<Position> neighbours = new Position(5, 3).getAllNeighbourPositions();
        Assert.assertEquals(8, neighbours.size());
    }

}
