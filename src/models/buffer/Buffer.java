package models.buffer;

public class Buffer {
    private final String[] data;

    private String cache;
    private int full;

    public Buffer(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        this.data = new String[size];
    }

    public int getSize() { return data.length; }

    public int getFull() { return full; }

    public boolean push(String data) {
        if (full == this.data.length) {
            return false; // Buffer is full of elements.
        }
        this.data[full++] = data;
        this.cache = data;
        return true;
    }

    public boolean pop() {
        if (full == 0) {
            return false; // Buffer is out of elements.
        }
        cache = data[--full];
        data[full] = null;
        return true;
    }

    public String view() {
        StringBuilder str = new StringBuilder("| Buffer |> ");
        for (int i = 0; i < full; i++) {
            str.append(data[i]);
            if (i < full - 1) {
                str.append(" ");
            } else {
                str.append("\n");
            }
        }
        return str.toString();
    }

    public String getCache() { return cache; }
}
