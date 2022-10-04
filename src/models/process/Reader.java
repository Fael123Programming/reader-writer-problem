package models.process;

import models.buffer.Buffer;
import models.enums.Situation;
import models.enums.State;

public class Reader extends Process {
    public Reader(String name, Situation situation, State state, Buffer buffer, int quantum) {
        super(name, situation, state, buffer, quantum);
    }

    @Override
    public void performAction() {
        if (this.buffer.pop()) {
            System.out.println("| '" + this.buffer.getCache() + "' read |");
        } else {
            System.out.println("| Empty buffer |");
        }
    }
}
