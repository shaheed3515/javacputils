package cp;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * High-performance output writer for Competitive Programming.
 * <p>
 * Buffers data in memory before writing to stdout, avoiding costly per-line OS flushes.
 * Delivers up to 20x-30x faster output than {@link System#out}.
 */
public class FastWriter {

    private final PrintWriter writer;

    /**
     * Constructs a FastWriter writing to {@link System#out}.
     */
    public FastWriter() {
        this(System.out);
    }

    /**
     * Constructs a FastWriter writing to a custom {@link OutputStream}.
     */
    public FastWriter(OutputStream out) {
        this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out), 1 << 16));
    }

    public void print(Object o) { writer.print(o); }
    public void print(int x) { writer.print(x); }
    public void print(long x) { writer.print(x); }
    public void print(double d) { writer.print(d); }
    public void print(char c) { writer.print(c); }
    public void print(String s) { writer.print(s); }

    public void println() { writer.println(); }
    public void println(Object o) { writer.println(o); }
    public void println(int x) { writer.println(x); }
    public void println(long x) { writer.println(x); }
    public void println(double d) { writer.println(d); }
    public void println(String s) { writer.println(s); }

    /**
     * Prints an array separated by spaces without a trailing newline.
     */
    public void print(int[] arr) {
        if (arr == null) return;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) writer.print(' ');
            writer.print(arr[i]);
        }
    }

    /**
     * Prints an array separated by spaces followed by a newline.
     */
    public void println(int[] arr) {
        print(arr);
        writer.println();
    }

    /**
     * Prints an array of longs separated by spaces without a trailing newline.
     */
    public void print(long[] arr) {
        if (arr == null) return;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) writer.print(' ');
            writer.print(arr[i]);
        }
    }

    /**
     * Prints an array of longs separated by spaces followed by a newline.
     */
    public void println(long[] arr) {
        print(arr);
        writer.println();
    }

    /**
     * Flushes all buffered output to stdout.
     */
    public void flush() {
        writer.flush();
    }

    /**
     * Closes the writer, flushing first.
     */
    public void close() {
        writer.close();
    }
}
