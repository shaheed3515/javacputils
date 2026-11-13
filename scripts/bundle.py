#!/usr/bin/env python3
"""
JavaCPUtils Bundler
-------------------
Inlines required utility classes into your solution file for competitive programming.
"""

import sys
import os
import re

SCRIPT_DIR = os.path.dirname(os.path.realpath(__file__))
PROJECT_DIR = os.path.abspath(os.path.join(SCRIPT_DIR, ".."))
SRC_DIR = os.path.join(PROJECT_DIR, "src", "main", "java", "cp")

AVAILABLE_CLASSES = {
    "Pair": "Pair.java",
    "IntPair": "IntPair.java",
    "LongPair": "LongPair.java",
    "FastScanner": "FastScanner.java",
    "FastWriter": "FastWriter.java",
    "FastIO": "FastIO.java"
}

CLASS_DEPENDENCIES = {
    "FastIO": ["FastScanner", "FastWriter"],
    "FastScanner": ["IntPair", "LongPair"]
}

def clean_class_for_inlining(code: str, class_name: str) -> str:
    """Strips package declarations, imports, javadocs, and makes the class package-private."""
    code = re.sub(r"/\*\*.*?\*/", "", code, flags=re.DOTALL)
    code = re.sub(r"/\*.*?\*/", "", code, flags=re.DOTALL)

    lines = code.splitlines()
    cleaned = []
    for line in lines:
        stripped = line.strip()
        if stripped.startswith("package "):
            continue
        if stripped.startswith("import "):
            continue
        if "serialVersionUID" in stripped:
            continue
        if re.match(r"^public\s+class\s+" + class_name + r"\b", stripped):
            line = line.replace("public class", "class", 1)
        if re.match(r"^public\s+final\s+class\s+" + class_name + r"\b", stripped):
            line = line.replace("public final class", "final class", 1)
        cleaned.append(line)

    result = "\n".join(cleaned)
    result = re.sub(r"\n\s*\n\s*\n+", "\n\n", result).strip()
    return result

def bundle(input_path: str, output_path: str = None):
    if not os.path.isfile(input_path):
        print(f"Error: File not found: {input_path}")
        sys.exit(1)

    if output_path is None:
        base, ext = os.path.splitext(input_path)
        output_path = f"{base}_bundled{ext}"

    out_file_basename = os.path.splitext(os.path.basename(output_path))[0]

    with open(input_path, "r", encoding="utf-8") as f:
        content = f.read()

    # Remove cp imports
    content_cleaned = re.sub(r"^\s*import\s+cp\.[^;]+;\s*\n?", "", content, flags=re.MULTILINE)
    content_cleaned = re.sub(r"^\s*import\s+cp\.\*;\s*\n?", "", content_cleaned, flags=re.MULTILINE)

    # If output filename doesn't match public class name, remove 'public' so javac doesn't complain about filename
    def adjust_public_class(match):
        cname = match.group(1)
        if cname != out_file_basename:
            return f"class {cname}"
        return match.group(0)

    content_cleaned = re.sub(r"\bpublic\s+class\s+([A-Za-z0-9_]+)", adjust_public_class, content_cleaned)

    # Determine needed classes from source
    needed_classes = set()
    for class_name in AVAILABLE_CLASSES:
        if re.search(r"\b" + class_name + r"\b", content_cleaned):
            needed_classes.add(class_name)

    # Resolve dependencies recursively
    changed = True
    while changed:
        changed = False
        for c in list(needed_classes):
            if c in CLASS_DEPENDENCIES:
                for dep in CLASS_DEPENDENCIES[c]:
                    if dep not in needed_classes:
                        needed_classes.add(dep)
                        changed = True

    # Order classes gracefully
    order = ["Pair", "IntPair", "LongPair", "FastScanner", "FastWriter", "FastIO"]
    inlined_code = []
    for class_name in order:
        if class_name in needed_classes:
            file_name = AVAILABLE_CLASSES[class_name]
            file_path = os.path.join(SRC_DIR, file_name)
            if os.path.isfile(file_path):
                with open(file_path, "r", encoding="utf-8") as cf:
                    raw_code = cf.read()
                cleaned = clean_class_for_inlining(raw_code, class_name)
                inlined_code.append(cleaned)

    # Ensure necessary java standard imports are at the top
    standard_imports = []
    if "import java.util." not in content_cleaned:
        standard_imports.append("import java.util.*;")
    if "import java.io." not in content_cleaned and inlined_code:
        standard_imports.append("import java.io.*;")

    import_block = "\n".join(standard_imports)
    if import_block:
        import_block += "\n"

    final_output = import_block + content_cleaned.strip() + "\n\n"
    if inlined_code:
        final_output += "\n\n".join(inlined_code) + "\n"

    with open(output_path, "w", encoding="utf-8") as f:
        f.write(final_output)

    print(f"Bundled -> {output_path}")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: bundle Solution.java [output.java]")
        sys.exit(1)
    
    in_file = sys.argv[1]
    out_file = sys.argv[2] if len(sys.argv) > 2 else None
    bundle(in_file, out_file)
