package models.process;

import models.buffer.Buffer;
import models.enums.*;
import util.RandomNumber;

public abstract class Process {
    private static int processingMax = 10;

    private final String name;
    private Situation situation; // 'W' for Wakeup or 'S' Sleep.
    private State state; // 'E' for Executing or 'D' for Done (waiting).
    protected int processing;

    public Process(String name) {
        this.name = name;
        this.situation = Situation.WAKEUP;
        this.state = State.DONE;
        this.setProcessing();
    }

    public static void setProcessingMax(int processingMax) {
        Process.processingMax = processingMax;
    }

    public void setProcessing() {
        this.processing = RandomNumber.between(1, Process.processingMax);
    }

    public String getName() { return name; }

    public Situation getSituation() { return situation; }

    public State getState() { return state; }

    public int getProcessing() { return processing; }

    public void setSituation(Situation s) { situation = s; }

    public void setState(State s) { state = s; }

    public void decreaseProcessing() {
        if (this.processing > 0) {
            this.processing--;
        } else {
            throw new IllegalStateException("processing is already zero.");
        }
    }

    public abstract boolean performAction(Buffer b);

    @Override
    public String toString() {
        String[] typeArray = this.getClass().getName().split("\\.");
        String typeName = typeArray[typeArray.length - 1];
        return String.format("| %-6s | %-11s |  | %-10s |  | %-10s |  | %-10s |", this.name, typeName,
                this.situation.name(), this.state.name(), this.processing);
    }
}
