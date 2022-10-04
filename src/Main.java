import models.buffer.Buffer;
import models.enums.Situation;
import models.enums.State;
import models.process.*;
import models.process.Process;
import models.scheduler.Scheduler;

public class Main {
    public static void main(String[] args) {
        Buffer b = new Buffer(5);
        Process w1 = new Writer("w1", Situation.WAKEUP, State.DONE, b, 3);
        Process r1 = new Reader("r1", Situation.WAKEUP, State.DONE, b, 1);
        Scheduler scheduler = new Scheduler(w1, r1);
        scheduler.schedule();
    }
}
