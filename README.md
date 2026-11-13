# JavaCPUtils (Java Competitive Programming Utilities) 🚀

A high-performance, lightweight Java utility library designed specifically for competitive programming, LeetCode, Codeforces, and algorithmic problem solving.

Brings the seamless developer experience of C++ (`std::pair`, `make_pair`, lexicographical sorting) into Java, with zero overhead.

[![JitPack](https://jitpack.io/v/shaheed3515/javacputils.svg)](https://jitpack.io/#shaheed3515/javacputils)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## Features

- **C++ Style Syntax:** Direct public fields (`p.first`, `p.second`) — zero ugly `getFirst()` boilerplate.
- **Natural Ordering:** Implements `Comparable` with lexicographical sorting out-of-the-box (`first` first, tie-breaks on `second`). Works directly in `Collections.sort()` and `PriorityQueue`.
- **Primitive Specialization (`IntPair` & `LongPair`):** Up to **3x faster** and **60% less memory** by eliminating object boxing overhead and cache misses.
- **Online Judge Ready (`bundle` tool):** Includes an automatic 1-command bundler that inlines library classes so you can copy-paste directly into Codeforces, LeetCode, CSES, and HackerRank.
- **Ultra-Clean Import:** Just write `import cp.*;`.

---

## ⚡ 1-Click Zero-Config Installation (For Beginners & CP Students)

No Maven or Gradle required! Run once, and use `import cp.*;` everywhere on your laptop.

### On Linux / macOS:
```bash
curl -fsSL https://raw.githubusercontent.com/shaheed3515/javacputils/main/scripts/install.sh | bash
```

### On Windows:
Download and run [`install.bat`](https://raw.githubusercontent.com/shaheed3515/javacputils/main/scripts/install.bat) or run in PowerShell:
```powershell
irm https://raw.githubusercontent.com/shaheed3515/javacputils/main/scripts/install.bat -OutFile install.bat; .\install.bat
```

---

## 📦 Usage with Gradle / Maven (JitPack)

### Gradle
Add JitPack to your `repositories`:
```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.shaheed3515:javacputils:v1.0.0'
}
```

### Maven (`pom.xml`)
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.shaheed3515</groupId>
        <artifactId>javacputils</artifactId>
        <version>v1.0.0</version>
    </dependency>
</dependencies>
```

---

## 💻 Quick Code Examples

### 1. High-Speed Competitive Programming (`IntPair`)
```java
import cp.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Direct public access
        IntPair p = new IntPair(10, 20);
        p.first += 5;

        // Auto lexicographical sort
        List<IntPair> list = new ArrayList<>();
        list.add(new IntPair(5, 10));
        list.add(new IntPair(2, 50));
        list.add(new IntPair(5, 2));

        Collections.sort(list);
        // Result: (2, 50), (5, 2), (5, 10)

        // Custom sort by second ascending
        list.sort(IntPair.comparingSecond());
    }
}
```

### 2. Generic Pair (`Pair<F, S>`)
```java
import cp.*;

public class Main {
    public static void main(String[] args) {
        // Syntactic equivalent of C++ make_pair
        var p = Pair.of("Score", 100);
        
        System.out.println(p.first + ": " + p.second);
        
        // Swapping
        Pair<Integer, String> swapped = p.swap();
    }
}
```

### 3. PriorityQueue / Dijkstra Min-Heap
```java
// Automatically orders by first, then second!
PriorityQueue<LongPair> pq = new PriorityQueue<>();
pq.add(new LongPair(dist[v], v));
```

---

## 🏆 Submitting to Codeforces / LeetCode / CSES

When your solution is ready to submit:
```bash
bundle Solution.java
```
This automatically produces `Solution_bundled.java` with all necessary classes inlined as package-private classes. Simply copy and paste the contents into the online judge!

---

## 🗺️ Roadmap

- [x] Generic `Pair<F, S>`
- [x] Primitive `IntPair` & `LongPair`
- [x] Auto-bundler CLI for Online Judges
- [ ] `Triple<A, B, C>` & Primitive `IntTriple`
- [ ] `FastScanner` (Ultra-fast I/O for CP)
- [ ] Algorithmic templates (Disjoint Set Union / DSU, Fenwick Tree)

---

## License

MIT License. Free to use in contests, personal projects, and commercial software.
