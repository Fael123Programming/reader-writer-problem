package models.cpu;

import models.buffer.Buffer;
import models.enums.Situation;
import models.enums.State;
import models.process.Process;

public class CPU {
    private Process p;

    private Buffer b;

    public Process getProcess() { return p; }

    public void setProcess(Process p) {
        if (p.getSituation() == Situation.SLEEP) {
            p.setSituation(Situation.WAKEUP);
        }
        this.p = p;
    }

    public Buffer getBuffer() { return b; }

    public void setBuffer(Buffer b) { this.b = b; }

    public boolean process() {
        if (this.p == null) {
            throw new IllegalStateException("The underlying process must be not null.");
        }
        if (this.b == null) {
            throw new IllegalStateException("The underlying buffer must be not null.");
        }
        if (this.p.getState() != State.EXECUTING) {
            this.p.setState(State.EXECUTING);
        }
        return this.p.performAction(b);
    }
}
