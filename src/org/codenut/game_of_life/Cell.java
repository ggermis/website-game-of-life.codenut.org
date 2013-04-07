package org.codenut.game_of_life;


public class Cell {

    private State state;
    private State mark;
    private Position position;


    public Cell(final Position position) {
        this(position, State.DEAD);
    }

    public Cell(final Position position, final State state) {
        this.position = position;
        setDead();
    }


    public Position getPosition() {
        return position;
    }


    public boolean isAlive() {
        return state == State.ALIVE;
    }

    public boolean isMarkedAlive() {
        return mark == State.ALIVE;
    }

    public boolean isDead() {
        return state == State.DEAD;
    }

    public boolean isMarkedDead() {
        return mark == State.DEAD;
    }

    public boolean isDirty() {
        return mark != state;
    }


    public Cell setAlive() {
        return markAlive().transition();
    }

    public Cell setDead() {
        return markDead().transition();
    }

    public Cell markAlive() {
        markAs(State.ALIVE);
        return this;
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
        return String.format(isAlive() ? "+ (%d:%d)" : "- (%d:%d)", getPosition().getX(), getPosition().getY());
    }
}
