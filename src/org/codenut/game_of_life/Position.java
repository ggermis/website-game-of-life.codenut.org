package org.codenut.game_of_life;


public class Position {

    public enum Border {
        NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
    }

    private int x;
    private int y;

    public Position(int x, int y) {
        if (x < 0 || y < 0) {
            throw new PositionException("Invalid position [" + x + ", " + y + "]");
        }
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
        boolean hasNeighbours = false;
        switch (border) {
            case NORTH:
                hasNeighbours = getY() > 0;
                break;
            case NORTH_EAST:
                hasNeighbours = getY() > 0 && getX() < world.getWidth()-1;
                break;
            case EAST:
                hasNeighbours = getX() < world.getWidth()-1;
                break;
            case SOUTH_EAST:
                hasNeighbours = getX() < world.getWidth()-1 && getY() < world.getHeight()-1;
                break;
            case SOUTH:
                hasNeighbours = getY() < world.getHeight()-1;
                break;
            case SOUTH_WEST:
                hasNeighbours = getX() > 0 && getY() < world.getHeight()-1;
                break;
            case WEST:
                hasNeighbours = getX() > 0;
                break;
            case NORTH_WEST:
                hasNeighbours = getX() > 0 && getY() > 0;
                break;
        }
        return hasNeighbours;
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
        return getX() + getY();
    }

    @Override
    public boolean equals(Object other) {
        return ((Position)other).getX() == getX() && ((Position)other).getY() == getY();
    }
}
