package models.scheduler;

import models.cpu.CPU;
import models.process.Process;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Scheduler {
    private final CPU cpu;
    private final List<Process> processes;

    {
        processes = new ArrayList<>();
        cpu = new CPU();
    }

    public Scheduler(Process... processes) {
        this.processes.addAll(Arrays.asList(processes));
    }

    public void schedule() {

    }
}
