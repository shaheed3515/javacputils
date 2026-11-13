package cp;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A high-performance, primitive-specialized Pair of {@code long} values for Competitive Programming.
 * <p>
 * Ideal for weighted graphs, coordinate systems, prefix sums, and algorithms with large numbers up to 10^18.
 */
public class LongPair implements Comparable<LongPair>, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    /** The first long value in the pair. */
    public long first;

    /** The second long value in the pair. */
    public long second;

    /**
     * Constructs a new LongPair initialized to {@code (0L, 0L)}.
     */
    public LongPair() {
        this.first = 0L;
        this.second = 0L;
    }

    /**
     * Constructs a new LongPair with the given values.
     *
     * @param first  the first long
     * @param second the second long
     */
    public LongPair(long first, long second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Copy constructor.
     */
    public LongPair(LongPair other) {
        if (other != null) {
            this.first = other.first;
            this.second = other.second;
        }
    }

    /**
     * Static factory method to create a LongPair.
     */
    public static LongPair of(long first, long second) {
        return new LongPair(first, second);
    }

    /**
     * Updates both values in-place.
     */
    public void set(long first, long second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns a new LongPair with values reversed.
     */
    public LongPair swap() {
        return new LongPair(this.second, this.first);
    }

    /**
     * Lexicographically compares two LongPairs.
     * Compares {@link #first} first; breaks ties using {@link #second}.
     * Immune to overflow (uses {@link Long#compare(long, long)}).
     */
    @Override
    public int compareTo(LongPair other) {
        if (this.first != other.first) {
            return Long.compare(this.first, other.first);
        }
        return Long.compare(this.second, other.second);
    }

    /**
     * Returns a comparator ordering LongPairs by {@link #first} ascending.
     */
    public static Comparator<LongPair> comparingFirst() {
        return (a, b) -> Long.compare(a.first, b.first);
    }

    /**
     * Returns a comparator ordering LongPairs by {@link #second} ascending.
     */
    public static Comparator<LongPair> comparingSecond() {
        return (a, b) -> Long.compare(a.second, b.second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LongPair other)) return false;
        return this.first == other.first && this.second == other.second;
    }

    @Override
    public int hashCode() {
        return 31 * Long.hashCode(first) + Long.hashCode(second);
    }

    @Override
    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }

    @Override
    public LongPair clone() {
        try {
            return (LongPair) super.clone();
        } catch (CloneNotSupportedException e) {
            return new LongPair(this.first, this.second);
        }
    }
}

