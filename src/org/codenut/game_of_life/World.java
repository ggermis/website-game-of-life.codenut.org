package org.codenut.game_of_life;


import org.codenut.game_of_life.ruleset.DefaultRuleSet;
import org.codenut.game_of_life.ruleset.RuleSet;
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
        final Position normalizedPosition = normalizePosition(position);
        Cell cell = livingCells.get(normalizedPosition);
        return cell == null ? new Cell(normalizedPosition) : cell;
    }

    public Cell getCellAt(int x, int y) {
        return getCellAt(new Position(x, y));
    }


    public synchronized void toggle(Cell cell) {
        if (cell.isAlive()) {
            setDead(cell);
        } else {
            setAlive(cell);
        }
    }

    private Cell setAlive(Cell cell) {
        return trackLivingCell(cell.setAlive());
    }

    public synchronized Cell markAlive(int x, int y) {
        return markAlive(getCellAt(x, y));
    }

    public Cell markAlive(Cell cell) {
        return trackDirtyCell(cell.markAlive());
    }

    private Cell setDead(Cell cell) {
        return trackLivingCell(cell.setDead());
    }

    public Cell markDead(int x, int y) {
        return markDead(getCellAt(x, y));
    }

    public Cell markDead(Cell cell) {
        return trackDirtyCell(cell.markDead());
    }


    private List<Cell> getNeighboursOf(final Cell cell) {
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


    public synchronized void applyRules() {
        for (Cell cell : getCellsToApplyRulesTo()) {
            ruleSet.apply(this, cell);
        }
    }

    public synchronized boolean transition() {
        boolean wasDirty = isDirty();
        for (Cell cell : getDirtyCells()) {
            trackLivingCell(cell.transition());
        }
        dirtyCells = new HashMap<Position, Cell>();
        return wasDirty;
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


    private Cell trackLivingCell(final Cell cell) {
        if (cell.isAlive()) {
            livingCells.put(cell.getPosition(), cell);
        } else {
            livingCells.remove(cell.getPosition());
        }
        return cell;
    }

    private Cell trackDirtyCell(final Cell cell) {
        if (cell.isDirty()) {
            dirtyCells.put(cell.getPosition(), cell);
        }
        return cell;
    }

    private Position normalizePosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        int widthDivider = x / width;
        int heightDivider = y / height;

        return new Position(x < 0 ? width + x + (width * widthDivider) : x - widthDivider * width, y < 0 ? height + y + (height * heightDivider) : y - heightDivider * height);
    }

    @Override
    public String toString() {
        return String.format("[World: %d, %d]", getWidth(), getHeight());
    }

}
