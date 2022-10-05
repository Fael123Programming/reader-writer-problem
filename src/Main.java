import models.buffer.Buffer;
import models.process.Process;
import models.scheduler.Scheduler;
import util.ProcessFactory;

public class Main {
    public static void main(String[] args) {
        final int READERS = 1;
        final int WRITERS = 1;
        final int BUFFER_SIZE = 5;
        Process[] processes = ProcessFactory.create(READERS, WRITERS, BUFFER_SIZE);
        Buffer buffer = new Buffer(BUFFER_SIZE);
        Scheduler scheduler = new Scheduler(buffer, processes);
        scheduler.schedule(30);
    }
}
