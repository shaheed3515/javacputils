# JavaCPUtils: FastIO API Cheat Sheet

**High-speed Input & Output Reference for Competitive Programming.**  
*Up to 10x faster reading than `Scanner`, 30x faster writing than `System.out.println`.*

---

## 1. Quick Start: The 10-Second Contest Template

```java
import cp.*;

public class Solution {
    public static void main(String[] shd) {
        int t = FastIO.nextInt();
        while (t-- > 0) {
            int n = FastIO.nextInt();
            int[] arr = FastIO.nextIntArray(n);     // Read array in 1 line!
            IntPair edge = FastIO.nextIntPair();   // Read pair in 1 line!

            // Solve problem...

            FastIO.println("YES");
            FastIO.println(arr);                   // Prints 10 20 30 with newline!
            FastIO.println(edge);                  // Prints (u, v) with newline!
        }
        FastIO.flush(); // CRITICAL: Always flush at the end of main()!
    }
}
```

---

## 2. Complete Fast Input Methods (`FastIO` / `FastScanner`)

| Operation | Method Signature | Return Type | Description |
| :--- | :--- | :--- | :--- |
| **Check Available** | `FastIO.hasNext()` | `boolean` | Returns `true` if there are more non-whitespace tokens. |
| **Read String Token** | `FastIO.next()` | `String` | Reads next whitespace-delimited word. |
| **Read Entire Line** | `FastIO.nextLine()` | `String` | Reads remainder of line until `\n`. |
| **Read 32-bit Integer**| `FastIO.nextInt()` | `int` | Reads next integer. Handles negative signs. |
| **Read 64-bit Long** | `FastIO.nextLong()` | `long` | Reads next 64-bit integer up to $10^{18}$. |
| **Read Decimal** | `FastIO.nextDouble()` | `double` | Reads next floating-point number. |
| **Read Integer Array**| `FastIO.nextIntArray(n)` | `int[]` | Reads `n` space/newline-separated ints into an array. |
| **Read Long Array** | `FastIO.nextLongArray(n)` | `long[]` | Reads `n` space/newline-separated longs into an array. |
| **Read IntPair** | `FastIO.nextIntPair()` | `IntPair` | Reads next two ints directly into an `IntPair`. |
| **Read LongPair** | `FastIO.nextLongPair()` | `LongPair` | Reads next two longs directly into a `LongPair`. |

---

## 3. Complete Fast Output Methods (`FastIO` / `FastWriter`)

*All `println()` methods automatically append a newline (`\n`). No manual `\n` needed!*

| Operation | Method Signature | Trailing Newline? | Description |
| :--- | :--- | :--- | :--- |
| **Print Value** | `FastIO.print(val)` | ❌ No | Writes `int`, `long`, `double`, `char`, `String`, or `Object`. |
| **Print with Newline** | `FastIO.println(val)` | ✅ Yes | Writes value followed by `\n`. |
| **Print Blank Line** | `FastIO.println()` | ✅ Yes | Writes an empty line (`\n`). |
| **Print Array (1 Line)**| `FastIO.println(int[] a)` | ✅ Yes | Prints elements separated by space + `\n` at the end. |
| **Print Array Inline** | `FastIO.print(int[] a)` | ❌ No | Prints elements separated by space without trailing `\n`. |
| **Print Long Array** | `FastIO.println(long[] a)`| ✅ Yes | Prints long array space-separated + `\n` at the end. |
| **Print Pair** | `FastIO.println(pair)` | ✅ Yes | Prints `(first, second)` followed by `\n`. |
| **Flush Buffer** | `FastIO.flush()` | — | **MANDATORY**: Dumps 64KB RAM buffer to terminal/judge. |

---

## 4. Key Rules to Remember in Live Contests ⚠️

1. **Always Call `FastIO.flush()` at the end of `main()`:**  
   Because `FastWriter` stores output in high-speed RAM, if you forget `FastIO.flush()`, your output may stay trapped in memory and Codeforces will show **Wrong Answer** on empty output!
2. **Online Judge Submissions:**  
   Run `bundle Solution.java` before uploading to Codeforces, LeetCode, CSES, or HackerRank.

