package org.codenut.game_of_life;



public class Position {

    public enum Border {
        NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
    }

    private int x;
    private int y;

    public Position(int x, int y) {
//        if (x < 0 || y < 0) {
//            throw new PositionException("Invalid position [" + x + ", " + y + "]");
//        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean hasNeighbourAt(final Border border, final World world) {
        return true;
    }

    public Position getNeighbourPosition(final Border border) {
        Position position = null;
        switch (border) {
            case NORTH:
                position = new Position(getX(), getY()-1);
                break;
            case NORTH_EAST:
                position = new Position(getX()+1, getY()-1);
                break;
            case EAST:
                position = new Position(getX()+1, getY());
                break;
            case SOUTH_EAST:
                position = new Position(getX()+1, getY()+1);
                break;
            case SOUTH:
                position = new Position(getX(), getY()+1);
                break;
            case SOUTH_WEST:
                position = new Position(getX()-1, getY()+1);
                break;
            case WEST:
                position = new Position(getX()-1, getY());
                break;
            case NORTH_WEST:
                position = new Position(getX()-1, getY()-1);
                break;
        }
        return position;
    }


    @Override
    public int hashCode() {
        return x * 32713 + y; // taken from android.graphics.Point
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Position) {
            Position otherPosition = (Position)other;
            return x == otherPosition.x && y == otherPosition.y;

        }
        return false;
    }
}
