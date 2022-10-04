package models.process;

import models.buffer.Buffer;
import models.enums.*;

public abstract class Process {
    private final String name;
    private Situation situation; // 'W' for Wakeup or 'S' Sleep.
    private State state; // 'E' for Executing or 'D' for Done (waiting).

    protected final Buffer buffer;

    protected int quantum;

    public Process(String name, Situation situation, State state, Buffer buffer, int quantum) {
        this.name = name;
        this.situation = situation;
        this.state = state;
        this.buffer = buffer;
        this.quantum = quantum;
    }

    public String getName() { return name; }

    public Situation getSituation() { return situation; }

    public State getState() { return state; }

    public int getQuantum() { return quantum; }

    public void setSituation(Situation s) { situation = s; }

    public void setState(State s) { state = s; }

    public abstract void performAction();

    @Override
    public String toString() {
        String type = this.getClass().getName();
        return String.format("%-20s %-20s %-20s %-20s", this.name, type, this.situation.name(), this.state.name());
    }
}
