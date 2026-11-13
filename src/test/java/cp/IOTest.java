package cp;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class IOTest {

    @Test
    public void testFastScanner() {
        String input = "10 -25 1000000000000 3.14 hello\nWorld!\n5 1 2 3 4 5\n99 88\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        FastScanner fs = new FastScanner(in);

        assertEquals(10, fs.nextInt());
        assertEquals(-25, fs.nextInt());
        assertEquals(1_000_000_000_000L, fs.nextLong());
        assertEquals(3.14, fs.nextDouble(), 1e-6);
        assertEquals("hello", fs.next());
        assertEquals("World!", fs.next());

        int n = fs.nextInt();
        assertEquals(5, n);
        int[] arr = fs.nextIntArray(n);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);

        IntPair p = fs.nextIntPair();
        assertEquals(99, p.first);
        assertEquals(88, p.second);
    }

    @Test
    public void testFastWriter() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FastWriter fw = new FastWriter(out);

        fw.println("YES");
        fw.println(42);
        fw.println(new int[]{10, 20, 30});
        fw.println(new IntPair(5, 15));
        fw.flush();

        String expected = "YES\n42\n10 20 30\n(5, 15)\n";
        // Normalize newlines for cross-platform consistency
        String actual = out.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals(expected, actual);
    }
}

