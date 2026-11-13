package cp;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class IntPairTest {

    @Test
    public void testIntPairSortingAndOverflowSafety() {
        List<IntPair> list = new ArrayList<>();
        list.add(new IntPair(10, 2_000_000_000));
        list.add(new IntPair(10, -2_000_000_000));
        list.add(new IntPair(5, 100));

        Collections.sort(list);

        assertEquals(new IntPair(5, 100), list.get(0));
        // Demonstrates overflow safety: -2B comes before +2B without wrapping around
        assertEquals(new IntPair(10, -2_000_000_000), list.get(1));
        assertEquals(new IntPair(10, 2_000_000_000), list.get(2));
    }

    @Test
    public void testLongPair() {
        LongPair lp1 = new LongPair(1_000_000_000_000L, 50L);
        LongPair lp2 = new LongPair(1_000_000_000_000L, 100L);

        assertTrue(lp1.compareTo(lp2) < 0);
        assertEquals("(1000000000000, 50)", lp1.toString());
    }

    @Test
    public void testSetAndSwap() {
        IntPair p = new IntPair(10, 20);
        p.set(30, 40);
        assertEquals(30, p.first);
        assertEquals(40, p.second);

        IntPair swapped = p.swap();
        assertEquals(40, swapped.first);
        assertEquals(30, swapped.second);
    }
}

