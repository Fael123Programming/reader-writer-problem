package models.scheduler;

import models.buffer.Buffer;
import models.cpu.CPU;
import models.enums.Situation;
import models.enums.State;
import models.process.Process;
import models.process.Reader;
import models.process.Writer;

public class Scheduler {
    private final CPU cpu;

    private final Process[] processes;

    private final Buffer buffer;

    private int lastWriterPos, lastReaderPos;

    {
        cpu = new CPU();
    }

    public Scheduler(Buffer b, Process... processes) {
        this.buffer = b;
        this.processes = processes;
        int writerCount = 0, readerCount = 0;
        for (Process p : processes) {
            if (p == null) {
                throw new IllegalArgumentException("None process may be null");
            }
            if (p instanceof Writer) {
                writerCount++;
            } else if (p instanceof Reader) {
                readerCount++;
            }
        }
        if (writerCount < 1) {
            throw new IllegalArgumentException("At least one writer is needed.");
        }
        if (readerCount < 1) {
            throw new IllegalArgumentException("At least one reader is needed.");
        }
        this.lastWriterPos = -1;
        this.lastReaderPos = -1;
    }

    public void schedule(int times) {
        this.displayView();
        Process currentProcess = this.nextWriter();
        cpu.setProcess(currentProcess);
//        this.currentQuantum = this.buffer.getSize();
        cpu.setBuffer(buffer);
        this.displayView();
        for (int i = 0; i < times; i++) {
//            while (this.currentQuantum > 0) {
            while (currentProcess.getProcessing() > 0) {
                if (cpu.process()) {
                    currentProcess.decreaseProcessing();
//                    this.currentQuantum--;
                    this.displayView();
                } else {
                    currentProcess.setSituation(Situation.SLEEP);
                    break;
                }
            }
            currentProcess.setState(State.DONE);
            currentProcess.setProcessing();
            if (currentProcess instanceof Writer) {
                currentProcess = this.nextReader();
            } else {
                currentProcess = this.nextWriter();
            }
            cpu.setProcess(currentProcess);
            this.displayView();
        }
    }

    public Process nextWriter() {
        Process next = null;
        boolean notFound = true;
        int i;
        if (lastWriterPos == -1 || lastWriterPos == this.processes.length - 1) {
            i = 0;
        } else  {
            i = lastWriterPos + 1;
        }
        while (notFound) {
            if (i == this.processes.length) {
                i = 0;
            }
            next = this.processes[i];
            if (next instanceof Writer) {
                notFound = false;
                this.lastWriterPos = i;
            } else {
                i++;
            }
        }
        return next;
    }

    public Process nextReader() {
        Process next = null;
        boolean notFound = true;
        int i;
        if (this.lastReaderPos == -1 || this.lastReaderPos == this.processes.length - 1) {
            i = 0;
        } else  {
            i = this.lastReaderPos + 1;
        }
        while (notFound) {
            if (i == this.processes.length) {
                i = 0;
            }
            next = this.processes[i];
            if (next instanceof Reader) {
                notFound = false;
                this.lastReaderPos = i;
            } else {
                i++;
            }
        }
        return next;
    }

    public String view() {
        StringBuilder builder = new StringBuilder();
        String bufferData, processStr = "";
        String format = String.format("%-11s  %-24s  %-14s  %-14s  %-14s\n", "-".repeat(11),
                "-".repeat(24), "-".repeat(14), "-".repeat(14), "-".repeat(14));
        builder.append(format);
        builder.append(String.format("| %-7s |  | %-20s |  | %-10s |  | %-10s |  | %-10s |\n", "Buffer", "Processes",
                "Situation", "State", "Processing"));
        builder.append(format);
        int iterationLength = Math.max(buffer.getSize(), this.processes.length + 4);
//        String s = String.format("%-14s %-10s %-14s  %-14s", "-".repeat(14), "", "-".repeat(14),
//                "-".repeat(14));
        String s = String.format("%-14s %-10s %-14s", "-".repeat(14), "", "-".repeat(14));
        for (int i = 0; i < iterationLength; i++) {
            if (i < buffer.getSize()) {
                String temp = buffer.getData()[i];
                bufferData = String.format("| %-7s |", temp == null ? "" : temp);
            } else if (i == buffer.getSize()) {
                bufferData = String.format("%-11s", "-".repeat(11));
            } else {
                bufferData = String.format("%-11s", "");
            }
            if (i < this.processes.length) {
                processStr = this.processes[i].toString();
            } else if (i == this.processes.length) {
                processStr = String.format("%-24s  %-14s  %-14s  %-14s", "-".repeat(24), "-".repeat(14),
                        "-".repeat(14), "-".repeat(14));
            } else if (i == this.processes.length + 1) {
//                processStr = String.format("| %-10s | %-10s | %-10s |  | %-10s |", "Counter", "", "CPU", "Quantum");
                processStr = String.format("| %-10s | %-10s | %-10s |", "Counter", "", "CPU");
            } else if (i == this.processes.length + 2) {
                processStr = s;;
            }else if (i == this.processes.length + 3) {
                String cpuProcessName = cpu.getProcess() == null ? "Empty" : this.cpu.getProcess().getName();
//                processStr = String.format("| %-10d | %-10s | %-10s |  | %-10s |", this.buffer.getFull(), "",
//                        cpuProcessName, this.currentQuantum);
                processStr = String.format("| %-10d | %-10s | %-10s |", this.buffer.getFull(), "", cpuProcessName);
            } else if (i == this.processes.length + 4) {
                processStr = s;
            } else {
                processStr = "";
            }
            builder.append(String.format("%s  %s\n", bufferData, processStr));
        }
        String bufferEnd = this.buffer.getSize() >= this.processes.length + 4 ? "-".repeat(11) : "";
        String counterCpuEnd = this.buffer.getSize() >= this.processes.length + 5 ? "" : s;
        builder.append(String.format("%-11s  %s\n", bufferEnd, counterCpuEnd));
        return builder.toString();
    }

    public void displayView() {
        System.out.println(this.view());
    }
}
