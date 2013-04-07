package org.codenut.game_of_life;


import java.util.ArrayList;
import java.util.List;


public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public Position getNeighbourPosition(final Direction direction) {
        Position position = null;
        switch (direction) {
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

    public List<Position> getAllNeighbourPositions() {
        List<Position> neighbours = new ArrayList<Position>();
        for (Direction direction : Direction.values()) {
            neighbours.add(getNeighbourPosition(direction));
        }
        return neighbours;
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

    @Override
    public String toString() {
        return String.format("[%d:%d]", x, y);
    }
}
