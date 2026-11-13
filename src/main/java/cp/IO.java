package cp;

/**
 * Static High-Speed I/O Gateway for Competitive Programming.
 * <p>
 * Provides instant static methods for zero-boilerplate reading and writing:
 * <pre>{@code
 * int n = IO.nextInt();
 * int[] a = IO.nextIntArray(n);
 * IO.println("YES");
 * IO.println(a);
 * IO.flush();
 * }</pre>
 */
public final class IO {

    private static final FastScanner scanner = new FastScanner();
    private static final FastWriter writer = new FastWriter();

    private IO() {} // Utility class

    // ==========================================
    // Fast Input Methods
    // ==========================================

    public static boolean hasNext() {
        return scanner.hasNext();
    }

    public static String next() {
        return scanner.next();
    }

    public static String nextLine() {
        return scanner.nextLine();
    }

    public static int nextInt() {
        return scanner.nextInt();
    }

    public static long nextLong() {
        return scanner.nextLong();
    }

    public static double nextDouble() {
        return scanner.nextDouble();
    }

    public static int[] nextIntArray(int n) {
        return scanner.nextIntArray(n);
    }

    public static long[] nextLongArray(int n) {
        return scanner.nextLongArray(n);
    }

    public static IntPair nextIntPair() {
        return scanner.nextIntPair();
    }

    public static LongPair nextLongPair() {
        return scanner.nextLongPair();
    }

    // ==========================================
    // Fast Output Methods
    // ==========================================

    public static void print(Object o) { writer.print(o); }
    public static void print(int x) { writer.print(x); }
    public static void print(long x) { writer.print(x); }
    public static void print(double d) { writer.print(d); }
    public static void print(char c) { writer.print(c); }
    public static void print(String s) { writer.print(s); }
    public static void print(int[] arr) { writer.print(arr); }
    public static void print(long[] arr) { writer.print(arr); }

    public static void println() { writer.println(); }
    public static void println(Object o) { writer.println(o); }
    public static void println(int x) { writer.println(x); }
    public static void println(long x) { writer.println(x); }
    public static void println(double d) { writer.println(d); }
    public static void println(String s) { writer.println(s); }
    public static void println(int[] arr) { writer.println(arr); }
    public static void println(long[] arr) { writer.println(arr); }

    /**
     * Flushes all buffered output to the screen.
     * Always call this at the end of {@code main()}!
     */
    public static void flush() {
        writer.flush();
    }
}
