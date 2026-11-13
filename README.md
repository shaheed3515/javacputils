# JavaCPUtils (Java Competitive Programming Utilities) 🚀

A high-performance, lightweight Java utility library designed specifically for competitive programming, LeetCode, Codeforces, and algorithmic problem solving.

Brings the seamless developer experience of C++ (`std::pair`, `make_pair`, fast I/O) into Java with zero overhead.

[![JitPack](https://jitpack.io/v/shaheed3515/javacputils.svg)](https://jitpack.io/#shaheed3515/javacputils)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## ⚡ What's New in v1.1.0

- **High-Speed `FastIO` Gateway:** Zero-object static I/O (`FastIO.nextInt()`, `FastIO.println()`, `FastIO.flush()`).
- **`FastScanner`:** Up to **10x faster** than `java.util.Scanner` by bypassing regex parsing. Reads arrays in one line (`in.nextIntArray(n)`).
- **`FastWriter`:** Up to **30x faster** than `System.out.println()` with 64KB memory buffering. Prints arrays in one line (`FastIO.println(arr)`).
- **Online Judge Bundler Updated:** Automatically inlines only the classes you use into a clean, human-style submission file for Codeforces and LeetCode.

---

## Features

- **C++ Style Syntax:** Direct public fields (`p.first`, `p.second`) — zero `getFirst()` boilerplate.
- **Natural Ordering:** Implements `Comparable` with lexicographical sorting out-of-the-box (`first` first, tie-breaks on `second`). Works directly in `Collections.sort()` and `PriorityQueue`.
- **Primitive Specialization (`IntPair` & `LongPair`):** Up to **3x faster** and **60% less memory** by eliminating boxing and cache misses.
- **Online Judge Ready (`bundle` tool):** Inlines library classes so you can copy-paste straight into Codeforces, LeetCode, CSES, and HackerRank.
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
```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.shaheed3515:javacputils:v1.1.1'
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
        <version>v1.1.1</version>
    </dependency>
</dependencies>
```

---

## 💻 Quick Code Examples

### 1. High-Speed I/O + Arrays + Pairs
```java
import cp.*;

public class Main {
    public static void main(String[] args) {
        // Zero object creation: fast static I/O
        int n = FastIO.nextInt();
        int[] arr = FastIO.nextIntArray(n);     // Read array in 1 line!
        IntPair edge = FastIO.nextIntPair();   // Read pair in 1 line!

        FastIO.println("Result:");
        FastIO.println(arr);                   // Prints space-separated with newline!
        FastIO.println(edge);                  // Prints (u, v) with newline!
        FastIO.flush();                        // Flush buffered output in 0.04s!
    }
}
```

### 2. High-Speed IntPair Sorting
```java
import cp.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<IntPair> list = new ArrayList<>();
        list.add(new IntPair(5, 10));
        list.add(new IntPair(2, 50));
        list.add(new IntPair(5, 2));

        Collections.sort(list);
        // Result: (2, 50), (5, 2), (5, 10)
    }
}
```

---

## 🏆 Submitting to Codeforces / LeetCode / CSES

When your solution is ready to submit:
```bash
bundle Solution.java
```
This produces `Solution_bundled.java` with inlined, clean classes. Copy-paste directly into the online judge!

---

## License

MIT License. Copyright (c) 2026 shaheed shaik.
