package cp;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * A generic Pair utility class modeled after C++'s {@code std::pair}.
 * <p>
 * Provides direct public field access via {@link #first} and {@link #second},
 * natural lexicographical comparison, and custom sorting utilities.
 *
 * @param <F> the type of the first element
 * @param <S> the type of the second element
 */
public class Pair<F, S> implements Comparable<Pair<F, S>>, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    /** The first element in the pair. */
    public F first;

    /** The second element in the pair. */
    public S second;

    /**
     * Constructs a new Pair with {@code null} values.
     */
    public Pair() {
        this.first = null;
        this.second = null;
    }

    /**
     * Constructs a new Pair with the specified values.
     *
     * @param first  the first value
     * @param second the second value
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Copy constructor. Creates a new Pair copying values from another pair.
     *
     * @param other the pair to copy from
     */
    public Pair(Pair<? extends F, ? extends S> other) {
        if (other != null) {
            this.first = other.first;
            this.second = other.second;
        }
    }

    /**
     * Static factory method to create a new Pair.
     * <p>
     * Syntactic equivalent of C++ {@code std::make_pair(a, b)}.
     *
     * @param first  the first value
     * @param second the second value
     * @param <F>    the type of the first element
     * @param <S>    the type of the second element
     * @return a new Pair containing first and second
     */
    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }

    /**
     * Alias for {@link #of(Object, Object)} for C++ developers accustomed to {@code make_pair}.
     */
    public static <F, S> Pair<F, S> make(F first, S second) {
        return new Pair<>(first, second);
    }

    /**
     * Sets both values of the pair simultaneously.
     *
     * @param first  the new first value
     * @param second the new second value
     */
    public void set(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns a new Pair with the elements reversed.
     *
     * @return a new {@code Pair<S, F>} with {@code second} as first and {@code first} as second
     */
    public Pair<S, F> swap() {
        return new Pair<>(this.second, this.first);
    }

    /**
     * Lexicographically compares this pair to another pair.
     * <p>
     * Compares {@code first} values first. If they are equal, compares {@code second} values.
     * {@code null} values are considered smaller than non-null values.
     *
     * @param other the other pair to compare to
     * @return a negative integer, zero, or a positive integer as this pair is less than, equal to, or greater than the specified pair
     * @throws ClassCastException if the elements do not implement {@link Comparable}
     */
    @Override
    public int compareTo(Pair<F, S> other) {
        if (other == null) {
            return 1;
        }
        int cmp = compareElement(this.first, other.first);
        if (cmp != 0) {
            return cmp;
        }
        return compareElement(this.second, other.second);
    }

    @SuppressWarnings("unchecked")
    private static int compareElement(Object o1, Object o2) {
        if (o1 == o2) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        return ((Comparable<Object>) o1).compareTo(o2);
    }

    /**
     * Returns a {@link Comparator} that orders pairs by their {@code first} element in natural ascending order.
     */
    public static <F extends Comparable<? super F>, S> Comparator<Pair<F, S>> comparingFirst() {
        return (p1, p2) -> compareElement(p1.first, p2.first);
    }

    /**
     * Returns a {@link Comparator} that orders pairs by their {@code second} element in natural ascending order.
     */
    public static <F, S extends Comparable<? super S>> Comparator<Pair<F, S>> comparingSecond() {
        return (p1, p2) -> compareElement(p1.second, p2.second);
    }

    /**
     * Returns a {@link Comparator} that orders pairs using a custom comparator on the {@code first} element.
     */
    public static <F, S> Comparator<Pair<F, S>> comparingFirst(Comparator<? super F> cmp) {
        return (p1, p2) -> cmp.compare(p1.first, p2.first);
    }

    /**
     * Returns a {@link Comparator} that orders pairs using a custom comparator on the {@code second} element.
     */
    public static <F, S> Comparator<Pair<F, S>> comparingSecond(Comparator<? super S> cmp) {
        return (p1, p2) -> cmp.compare(p1.second, p2.second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair<?, ?> pair)) return false;
        return Objects.equals(this.first, pair.first) && Objects.equals(this.second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    @Override
    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }

    @Override
    public Pair<F, S> clone() {
        try {
            @SuppressWarnings("unchecked")
            Pair<F, S> cloned = (Pair<F, S>) super.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            return new Pair<>(this.first, this.second);
        }
    }
}

