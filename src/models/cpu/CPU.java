package models.cpu;

import models.process.Process;

public class CPU {
    private Process p;

    public void setProcess(Process p) {
        this.p = p;
    }

    public void process() {
        int quantum = p.getQuantum();
        while (quantum > 0) {
            p.performAction();
            quantum--;
        }
    }
}
