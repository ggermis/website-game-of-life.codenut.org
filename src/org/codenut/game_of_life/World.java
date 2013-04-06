package org.codenut.game_of_life;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class World {


    private Set<Cell> livingCells;
    private Set<Cell> dirtyCells;

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
        this.dirtyCells = new HashSet<Cell>();
        this.livingCells = new HashSet<Cell>();
    }


    public int getWidth() {
        return cells.length;
    }

    public int getHeight() {
        return cells[0].length;
    }

    public boolean isDirty() {
        return dirtyCells.size() > 0;
    }

    public Cell getCellAt(int x, int y) {
        return cells[x][y];
    }

    public Cell markAliveAt(int x, int y) {
        return markAlive(cells[x][y]);
    }

    public Cell markAlive(Cell cell) {
        Cell ret = cell.markAlive();
        if (cell.isDirty()) {
            dirtyCells.add(cell);
        }
        return ret;
    }

    public Cell markDeadAt(int x, int y) {
        return markDead(cells[x][y]);
    }

    public Cell markDead(Cell cell) {
        Cell ret = cell.markDead();
        if (cell.isDirty()) {
            dirtyCells.add(cell);
        }
        return ret;
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

    public Set<Cell> getLivingCells() {
        return livingCells;
    }

    public void tick() {
        applyRules();
        transition();
    }

    public void applyRules() {
        for (Cell cell : getCellsToApplyRulesTo()) {
            ruleSet.apply(this, cell);
        }
    }

    private Set<Cell> getCellsToApplyRulesTo() {
        Set<Cell> cells = new HashSet<Cell>();
        for (Cell cell : livingCells) {
            cells.add(cell);
            for (Position.Border border : Position.Border.values()) {
                if (cell.getPosition().hasNeighbourAt(border, this)) {
                    final Position position = cell.getPosition().getNeighbourPosition(border);
                    cells.add(getCellAt(position.getX(), position.getY()));
                }
            }
        }
        return cells;
    }

    public void transition() {
        for (Cell cell : dirtyCells) {
            cell.transition();
            if (cell.isAlive()) {
                livingCells.add(cell);
            } else {
                livingCells.remove(cell);
            }
        }
        dirtyCells = new HashSet<Cell>();
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
