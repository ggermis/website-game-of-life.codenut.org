package org.codenut.game_of_life;


import java.util.ArrayList;
import java.util.List;

public class World {


    private Cell[][] cells;
    private RuleSet ruleSet;


    public World() {
        this(100, 100);
    }

    public World(int x, int y) {
        this(x, y, new DefaultRuleSet());
    }

    public World(int width, int height, final RuleSet ruleSet) {
        cells = new Cell[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x][y] = new Cell(new Position(x, y));
            }
        }
        this.ruleSet = ruleSet;
    }


    public int getWidth() {
        return cells.length;
    }

    public int getHeight() {
        return cells[0].length;
    }


    public Cell getCellAt(int x, int y) {
        return cells[x][y];
    }

    public List<Cell> getNeighboursOf(final Cell cell) {
        final Position position = cell.getPosition();
        List<Cell> neighbours = new ArrayList<Cell>();
        for (Position.Border border : Position.Border.values()) {
            if (position.hasNeighbourAt(border, this)) {
                Position neighbourPosition = position.getNeighbourPosition(border);
                neighbours.add(getCellAt(neighbourPosition.getX(), neighbourPosition.getY()));
            }
        }
        return neighbours;
    }

    public List<Cell> getLivingNeighboursOf(final Cell cell) {
        List<Cell> livingNeighbours = new ArrayList<Cell>();
        for (Cell neighbour : getNeighboursOf(cell)) {
            if (neighbour.isAlive()) {
                livingNeighbours.add(neighbour);
            }
        }
        return livingNeighbours;
    }

    public List<Cell> getLivingCells() {
        List<Cell> livingCells = new ArrayList<Cell>();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Cell cell = cells[x][y];
                if (cell.isAlive()) {
                    livingCells.add(cell);
                }
            }
        }
        return livingCells;
    }

    public void tick() {
        List<Cell> dirtyCells = applyRules();
        transition(dirtyCells.toArray(new Cell[dirtyCells.size()]));
    }

    public List<Cell> applyRules() {
        List<Cell> dirtyCells = new ArrayList<Cell>();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Cell cell = cells[x][y];
                ruleSet.apply(this, cell);
                if (cell.isDirty()) {
                    dirtyCells.add(cell);
                }
            }
        }
        return dirtyCells;
    }

    public void transition(Cell... cells) {
        for (Cell cell : cells) {
            cell.transition();
        }
    }

    public void show() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                System.err.print(cells[x][y].toString());
            }
            System.err.print("\n");
        }
        System.err.println("");
    }


    @Override
    public String toString() {
        return String.format("[World: %d, %d]", getWidth(), getHeight());
    }


    public static void main(String[] args) {
        final World world = new World(20, 10);

        world.getCellAt(5, 3).revive();
        world.getCellAt(6, 3).revive();
        world.getCellAt(6, 2).revive();
        world.getCellAt(6, 1).revive();

        world.show();
        world.tick();
        world.show();
    }
}
