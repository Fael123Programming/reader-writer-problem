package util;

import models.process.Process;
import models.process.Reader;
import models.process.Writer;

public class ProcessFactory {
    public static Process[] create(int readers, int writers, int processingMax) {
        if (readers <= 0) {
            throw new IllegalArgumentException("At least one reader is required.");
        }
        if (writers <= 0) {
            throw new IllegalArgumentException("At least one writer is required.");
        }
        if (processingMax < 1) {
            throw new IllegalArgumentException("quantumMax must be greater than zero.");
        }
        Process.setProcessingMax(processingMax);
        Process[] processes = new Process[readers + writers];
        Process newProcess;
        for (int i = 0; i < readers + writers; i++) {
            if (i < readers) {
                newProcess = new Reader("r" + (i + 1));
            } else {
                newProcess = new Writer("w" + (i - readers + 1));
            }
            processes[i] = newProcess;
        }
        return processes;
    }
}
