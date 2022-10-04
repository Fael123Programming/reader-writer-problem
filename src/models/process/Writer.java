package models.process;

import models.buffer.Buffer;
import models.enums.Situation;
import models.enums.State;

public class Writer extends Process {
    private int i;

    public Writer(String name, Situation situation, State state, Buffer buffer, int quantum) {
        super(name, situation, state, buffer, quantum);
        this.i = 1;
    }

    @Override
    public void performAction() {
        String data = "D" + i;
        if (this.buffer.push(data)) {
            System.out.println("| '" + data + "' written to |");
            i++;
        } else {
            System.out.println("| Buffer is full |");
        }
    }
}
