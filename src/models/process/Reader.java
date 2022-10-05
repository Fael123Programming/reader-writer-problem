package models.process;

import models.buffer.Buffer;

public class Reader extends Process {
    public Reader(String name) {
        super(name);
    }

    @Override
    public boolean performAction(Buffer b) {
        return b.pop();
    }
}
