package cp;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PairTest {

    @Test
    public void testDirectFieldAccessAndMutation() {
        Pair<Integer, String> p = new Pair<>(10, "Apple");
        assertEquals(10, p.first);
        assertEquals("Apple", p.second);

        // C++ style mutation
        p.first = 25;
        p.second = "Orange";
        assertEquals(25, p.first);
        assertEquals("Orange", p.second);
    }

    @Test
    public void testStaticFactoryAndSwap() {
        Pair<Integer, Integer> p = Pair.of(1, 2);
        Pair<Integer, Integer> swapped = p.swap();

        assertEquals(1, p.first);
        assertEquals(2, p.second);
        assertEquals(2, swapped.first);
        assertEquals(1, swapped.second);
    }

    @Test
    public void testLexicographicalSorting() {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        list.add(new Pair<>(5, 20));
        list.add(new Pair<>(2, 50));
        list.add(new Pair<>(5, 10));
        list.add(new Pair<>(1, 100));

        Collections.sort(list);

        assertEquals(Pair.of(1, 100), list.get(0));
        assertEquals(Pair.of(2, 50), list.get(1));
        assertEquals(Pair.of(5, 10), list.get(2)); // Tie-break on second
        assertEquals(Pair.of(5, 20), list.get(3));
    }

    @Test
    public void testPriorityQueue() {
        PriorityQueue<Pair<Integer, String>> pq = new PriorityQueue<>();
        pq.add(Pair.of(5, "Medium"));
        pq.add(Pair.of(1, "Highest"));
        pq.add(Pair.of(10, "Lowest"));

        assertEquals(Pair.of(1, "Highest"), pq.poll());
        assertEquals(Pair.of(5, "Medium"), pq.poll());
        assertEquals(Pair.of(10, "Lowest"), pq.poll());
    }

    @Test
    public void testCustomComparators() {
        List<Pair<String, Integer>> list = new ArrayList<>();
        list.add(Pair.of("Alice", 90));
        list.add(Pair.of("Bob", 75));
        list.add(Pair.of("Charlie", 85));

        // Sort by second (score) ascending
        list.sort(Pair.comparingSecond());
        assertEquals("Bob", list.get(0).first);
        assertEquals("Charlie", list.get(1).first);
        assertEquals("Alice", list.get(2).first);

        // Sort by second descending
        list.sort(Pair.<String, Integer>comparingSecond().reversed());
        assertEquals("Alice", list.get(0).first);
    }

    @Test
    public void testHashSetAndHashMap() {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        set.add(Pair.of(10, 20));
        set.add(Pair.of(10, 20)); // duplicate
        set.add(Pair.of(20, 10));

        assertEquals(2, set.size());
        assertTrue(set.contains(new Pair<>(10, 20)));

        Map<Pair<Integer, Integer>, String> map = new HashMap<>();
        map.put(Pair.of(0, 0), "Origin");
        assertEquals("Origin", map.get(new Pair<>(0, 0)));
    }

    @Test
    public void testToString() {
        Pair<Integer, Integer> p = new Pair<>(42, 99);
        assertEquals("(42, 99)", p.toString());
    }
}

