package org.codenut.game_of_life;


import java.util.*;

public class World {


    private Map<Position, Cell> livingCells;
    private Set<Cell> dirtyCells;

    private RuleSet ruleSet;
    private int width;
    private int height;


    public World() {
        this(100, 100);
    }

    public World(int width, int height) {
        this(width, height, new DefaultRuleSet());
    }

    public World(int width, int height, final RuleSet ruleSet) {
        this.width = width;
        this.height = height;
        this.ruleSet = ruleSet;
        this.dirtyCells = new HashSet<Cell>();
        this.livingCells = new HashMap<Position, Cell>();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDirty() {
        return getDirtyCells().size() > 0;
    }

    public Cell getCellAt(int x, int y) {
        return new Cell(new Position(x, y));
    }

    public Cell markAliveAt(int x, int y) {
        return markAlive(getCellAt(x, y));
    }

    public Cell markAlive(Cell cell) {
        Cell ret = cell.markAlive();
        if (cell.isDirty()) {
            getDirtyCells().add(cell);
        }
        return ret;
    }

    public Cell markDeadAt(int x, int y) {
        return markDead(getCellAt(x, y));
    }

    public Cell markDead(Cell cell) {
        Cell ret = cell.markDead();
        if (cell.isDirty()) {
            getDirtyCells().add(cell);
        }
        return ret;
    }


    public List<Cell> getNeighboursOf(final Cell cell) {
        List<Cell> neighbours = new ArrayList<Cell>();
        for (Position neighbourPosition : cell.getPosition().getAllNeighbourPositions()) {
            Cell neighbour = livingCells.get(neighbourPosition);
            if (neighbour == null) {
                neighbour = getCellAt(neighbourPosition.getX(), neighbourPosition.getY());
            }
            neighbours.add(neighbour);
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


    public Collection<Cell> getLivingCells() {
        return livingCells.values();
    }

    public Collection<Cell> getDirtyCells() {
        return dirtyCells;
    }

    public Collection<Cell> getTrackedCells() {
        Collection<Cell> trackedCells = new HashSet<Cell>();
        trackedCells.addAll(getLivingCells());
        trackedCells.addAll(getDirtyCells());
        return trackedCells;
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
        for (Cell cell : getTrackedCells()) {
            cells.add(cell);
            for (Position neighbourPosition : cell.getPosition().getAllNeighbourPositions()) {
                cells.add(getCellAt(neighbourPosition.getX(), neighbourPosition.getY()));
            }
        }
        return cells;
    }

    public void transition() {
        for (Cell cell : getDirtyCells()) {
            cell.transition();
            if (cell.isAlive()) {
                livingCells.put(cell.getPosition(), cell);
            } else {
                livingCells.remove(cell.getPosition());
            }
        }
        dirtyCells = new HashSet<Cell>();
    }


    @Override
    public String toString() {
        return String.format("[World: %d, %d]", getWidth(), getHeight());
    }

}
