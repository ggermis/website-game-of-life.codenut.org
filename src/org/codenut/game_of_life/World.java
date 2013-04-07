package org.codenut.game_of_life;


import java.util.*;

public class World {

    private Map<Position, Cell> livingCells;
    private Map<Position, Cell> dirtyCells;

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
        this.dirtyCells = new HashMap<Position, Cell>();
        this.livingCells = new HashMap<Position, Cell>();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Collection<Cell> getLivingCells() {
        return livingCells.values();
    }

    public Collection<Cell> getDirtyCells() {
        return dirtyCells.values();
    }

    public boolean isDirty() {
        return getDirtyCells().size() > 0;
    }


    public Cell getCellAt(final Position position) {
        Cell cell = livingCells.get(position);
        return cell == null ? new Cell(position) : cell;
    }

    public Cell getCellAt(int x, int y) {
        return getCellAt(new Position(x, y));
    }


    public Cell markAliveAt(int x, int y) {
        return markAlive(getCellAt(x, y));
    }

    public Cell markAlive(Cell cell) {
        Cell ret = cell.markAlive();
        if (cell.isDirty()) {
            dirtyCells.put(cell.getPosition(), cell);
        }
        return ret;
    }

    public Cell markDeadAt(int x, int y) {
        return markDead(getCellAt(x, y));
    }

    public Cell markDead(Cell cell) {
        Cell ret = cell.markDead();
        if (cell.isDirty()) {
            dirtyCells.put(cell.getPosition(), cell);
        }
        return ret;
    }


    public List<Cell> getNeighboursOf(final Cell cell) {
        List<Cell> neighbours = new ArrayList<Cell>();
        for (Position neighbourPosition : cell.getPosition().getAllNeighbourPositions()) {
            neighbours.add(getCellAt(neighbourPosition));
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
        for (Cell cell : getLivingCells()) {
            cells.add(cell);
            for (Position neighbourPosition : cell.getPosition().getAllNeighbourPositions()) {
                cells.add(getCellAt(neighbourPosition));
            }
        }
        return cells;
    }

    public void transition() {
        for (Cell cell : getDirtyCells()) {
            cell.transition();
            trackLivingCell(cell);
        }
        dirtyCells = new HashMap<Position, Cell>();
    }

    private void trackLivingCell(final Cell cell) {
        if (cell.isAlive()) {
            if (isWithinBounds(cell.getPosition())) {
                livingCells.put(cell.getPosition(), cell);
            }
        } else {
            livingCells.remove(cell.getPosition());
        }
    }

    private boolean isWithinBounds(final Position position) {
        return position.getX() <= width && position.getY() <= height;
    }

    @Override
    public String toString() {
        return String.format("[World: %d, %d]", getWidth(), getHeight());
    }

}
