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
    "LongPair": "LongPair.java"
}

def clean_class_for_inlining(code: str, class_name: str) -> str:
    """Strips package declarations, imports, javadocs, and makes the class package-private."""
    # Remove block comments / Javadocs
    code = re.sub(r"/\*\*.*?\*/", "", code, flags=re.DOTALL)
    code = re.sub(r"/\*.*?\*/", "", code, flags=re.DOTALL)

    lines = code.splitlines()
    cleaned = []
    for line in lines:
        stripped = line.strip()
        # Skip package statement
        if stripped.startswith("package "):
            continue
        # Skip ANY import statements
        if stripped.startswith("import "):
            continue
        # Skip serialVersionUID
        if "serialVersionUID" in stripped:
            continue
        # Convert public class to package-private class
        if re.match(r"^public\s+class\s+" + class_name + r"\b", stripped):
            line = line.replace("public class", "class", 1)
        cleaned.append(line)

    result = "\n".join(cleaned)
    # Clean up empty lines
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

    # Detect which classes are needed
    inlined_code = []
    for class_name, file_name in AVAILABLE_CLASSES.items():
        if re.search(r"\b" + class_name + r"\b", content_cleaned):
            file_path = os.path.join(SRC_DIR, file_name)
            if os.path.isfile(file_path):
                with open(file_path, "r", encoding="utf-8") as cf:
                    raw_code = cf.read()
                cleaned = clean_class_for_inlining(raw_code, class_name)
                inlined_code.append(cleaned)

    # Ensure java.util.* and java.io.* imports are at the top if needed
    needed_imports = []
    if "import java.util." not in content_cleaned:
        needed_imports.append("import java.util.*;")
    if "import java.io." not in content_cleaned and inlined_code:
        needed_imports.append("import java.io.*;")

    import_block = "\n".join(needed_imports)
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
