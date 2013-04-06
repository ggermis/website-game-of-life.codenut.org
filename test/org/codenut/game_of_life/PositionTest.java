package org.codenut.game_of_life;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


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
                { position, Position.Border.NORTH, new Position(position.getX(), position.getY()-1)},
                { position, Position.Border.NORTH_EAST, new Position(position.getX()+1, position.getY()-1)},
                { position, Position.Border.EAST, new Position(position.getX()+1, position.getY())},
                { position, Position.Border.SOUTH_EAST, new Position(position.getX()+1, position.getY()+1)},
                { position, Position.Border.SOUTH, new Position(position.getX(), position.getY()+1)},
                { position, Position.Border.SOUTH_WEST, new Position(position.getX()-1, position.getY()+1)},
                { position, Position.Border.WEST, new Position(position.getX()-1, position.getY())},
                { position, Position.Border.NORTH_WEST, new Position(position.getX()-1, position.getY()-1)},
        };
    }

    @Test(dataProvider = "neighbourPositionDataProvider")
    public void neighbourPosition(final Position position, final Position.Border border, final Position expectedPosition) {
        Assert.assertEquals(expectedPosition, position.getNeighbourPosition(border));
    }

}
