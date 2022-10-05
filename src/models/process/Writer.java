package models.process;

import models.buffer.Buffer;

public class Writer extends Process {
    private int i;

    public Writer(String name) {
        super(name);
        this.i = 1;
    }

    @Override
    public boolean performAction(Buffer b) {
        String data = getName() + "(" + i + ")";
        if (b.push(data)) {
            if (i == 10) {
                i = 0;
            } else {
                i++;
            }
            return true;
        }
        return false;
    }
}
