package cp;

import java.io.InputStream;
import java.io.IOException;
import java.util.InputMismatchException;

/**
 * High-performance byte-stream scanner for Competitive Programming.
 * <p>
 * Bypasses regex parsing overhead in {@link java.util.Scanner}, achieving up to 10x faster token extraction.
 */
public class FastScanner {

    private final InputStream stream;
    private final byte[] buffer = new byte[1 << 16]; // 64KB buffer
    private int head = 0;
    private int tail = 0;

    /**
     * Constructs a FastScanner reading from {@link System#in}.
     */
    public FastScanner() {
        this(System.in);
    }

    /**
     * Constructs a FastScanner reading from a custom {@link InputStream}.
     */
    public FastScanner(InputStream stream) {
        this.stream = stream;
    }

    private int readByte() {
        if (head >= tail) {
            head = 0;
            try {
                tail = stream.read(buffer, 0, buffer.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (tail <= 0) return -1;
        }
        return buffer[head++];
    }

    private static boolean isWhitespace(int c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    /**
     * Checks if there are more tokens available.
     */
    public boolean hasNext() {
        int c = readByte();
        while (c != -1 && isWhitespace(c)) {
            c = readByte();
        }
        if (c == -1) return false;
        head--; // Push back non-whitespace byte
        return true;
    }

    /**
     * Reads the next non-whitespace string token.
     */
    public String next() {
        int c = readByte();
        while (c != -1 && isWhitespace(c)) {
            c = readByte();
        }
        if (c == -1) {
            throw new InputMismatchException("End of input reached");
        }
        StringBuilder sb = new StringBuilder();
        while (c != -1 && !isWhitespace(c)) {
            sb.append((char) c);
            c = readByte();
        }
        return sb.toString();
    }

    /**
     * Reads the rest of the current line.
     */
    public String nextLine() {
        int c = readByte();
        while (c != -1 && (c == '\n' || c == '\r')) {
            c = readByte();
        }
        if (c == -1) {
            throw new InputMismatchException("End of input reached");
        }
        StringBuilder sb = new StringBuilder();
        while (c != -1 && c != '\n' && c != '\r') {
            sb.append((char) c);
            c = readByte();
        }
        return sb.toString();
    }

    /**
     * Reads the next integer.
     */
    public int nextInt() {
        int c = readByte();
        while (c != -1 && isWhitespace(c)) {
            c = readByte();
        }
        if (c == -1) {
            throw new InputMismatchException("End of input reached");
        }
        int sign = 1;
        if (c == '-') {
            sign = -1;
            c = readByte();
        }
        int res = 0;
        while (c >= '0' && c <= '9') {
            res = res * 10 + (c - '0');
            c = readByte();
        }
        if (c != -1 && !isWhitespace(c)) {
            throw new InputMismatchException("Invalid character in integer token");
        }
        return res * sign;
    }

    /**
     * Reads the next 64-bit long integer.
     */
    public long nextLong() {
        int c = readByte();
        while (c != -1 && isWhitespace(c)) {
            c = readByte();
        }
        if (c == -1) {
            throw new InputMismatchException("End of input reached");
        }
        long sign = 1;
        if (c == '-') {
            sign = -1;
            c = readByte();
        }
        long res = 0;
        while (c >= '0' && c <= '9') {
            res = res * 10 + (c - '0');
            c = readByte();
        }
        if (c != -1 && !isWhitespace(c)) {
            throw new InputMismatchException("Invalid character in long token");
        }
        return res * sign;
    }

    /**
     * Reads the next double floating-point number.
     */
    public double nextDouble() {
        return Double.parseDouble(next());
    }

    /**
     * Reads {@code n} integers into an array in a single line call.
     */
    public int[] nextIntArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nextInt();
        }
        return arr;
    }

    /**
     * Reads {@code n} longs into an array in a single line call.
     */
    public long[] nextLongArray(int n) {
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nextLong();
        }
        return arr;
    }

    /**
     * Reads the next two integers into an {@link IntPair}.
     */
    public IntPair nextIntPair() {
        return new IntPair(nextInt(), nextInt());
    }

    /**
     * Reads the next two longs into a {@link LongPair}.
     */
    public LongPair nextLongPair() {
        return new LongPair(nextLong(), nextLong());
    }
}

