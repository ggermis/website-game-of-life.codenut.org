package org.codenut.game_of_life;


public class Cell {

    public enum State {
        DEAD, ALIVE
    }

    private State state;
    private State mark;
    private Position position;


    public Cell(final Position position) {
        this(position, State.DEAD);
    }

    public Cell(final Position position, final State state) {
        this.position = position;
        markAs(state);
        transition();
    }


    public Position getPosition() {
        return position;
    }

    public boolean isAlive() {
        return state == State.ALIVE;
    }

    public boolean isDead() {
        return state == State.DEAD;
    }

    public boolean isDirty() {
        return mark != state;
    }


    public Cell revive() {
        markAlive();
        return transition();
    }

    public Cell markAlive() {
        markAs(State.ALIVE);
        return this;
    }

    public Cell kill() {
        markDead();
        return transition();
    }

    public Cell markDead() {
        markAs(State.DEAD);
        return this;
    }


    public Cell transition() {
        state = mark;
        return this;
    }


    private void markAs(final State state) {
        this.mark = state;
    }


    @Override
    public String toString() {
        return String.format(isAlive() ? " X " : " - ");
    }
}
