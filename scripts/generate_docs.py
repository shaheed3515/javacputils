#!/usr/bin/env python3
import os
import re
import html
import subprocess

SCRIPT_DIR = os.path.dirname(os.path.realpath(__file__))
PROJECT_DIR = os.path.abspath(os.path.join(SCRIPT_DIR, ".."))
DOCS_DIR = os.path.join(PROJECT_DIR, "docs")

DOCS_TO_CONVERT = [
    "JavaCPUtils_Complete_Guide",
    "Pair_Family_CheatSheet",
    "FastIO_CheatSheet"
]

css = """
<style>
@page {
    size: A4;
    margin: 16mm 14mm 16mm 14mm;
}
body {
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
    line-height: 1.5;
    color: #24292e;
    max-width: 900px;
    margin: 0 auto;
    font-size: 10pt;
}
h1 {
    color: #0969da;
    border-bottom: 2px solid #d0d7de;
    padding-bottom: 0.25em;
    font-size: 18pt;
    margin-top: 0;
}
h2 {
    color: #1f2328;
    border-bottom: 1px solid #d0d7de;
    padding-bottom: 0.2em;
    font-size: 13pt;
    margin-top: 1.2em;
    page-break-after: avoid;
}
h3 {
    color: #32383f;
    font-size: 11pt;
    margin-top: 1em;
    page-break-after: avoid;
}
code {
    background-color: #eff1f3;
    padding: 0.15em 0.35em;
    border-radius: 4px;
    font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, monospace;
    font-size: 88%;
    color: #cf222e;
}
pre {
    background-color: #f6f8fa;
    border: 1px solid #d0d7de;
    border-radius: 6px;
    padding: 8px 12px;
    font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, monospace;
    font-size: 8.5pt;
    line-height: 1.35;
    page-break-inside: avoid;
    white-space: pre-wrap;
    word-break: break-all;
}
pre code {
    background: none;
    padding: 0;
    color: #24292e;
}
table {
    border-collapse: collapse;
    width: 100%;
    margin: 0.8em 0;
    font-size: 9pt;
    page-break-inside: avoid;
}
table, th, td {
    border: 1px solid #d0d7de;
}
th {
    background-color: #f6f8fa;
    padding: 5px 8px;
    font-weight: 600;
    text-align: left;
}
td {
    padding: 5px 8px;
}
tr:nth-child(even) {
    background-color: #fcfdfe;
}
hr {
    border: 0;
    height: 1px;
    background: #d0d7de;
    margin: 1em 0;
}
ul, ol {
    padding-left: 1.6em;
}
li {
    margin: 0.2em 0;
}
</style>
"""

def process_inline(text):
    text = html.escape(text)
    text = re.sub(r"\*\*(.+?)\*\*", r"<strong>\1</strong>", text)
    text = re.sub(r"`([^`]+)`", r"<code>\1</code>", text)
    text = re.sub(r"\[([^\]]+)\]\(([^)]+)\)", r'<a href="\2">\1</a>', text)
    return text

for doc_name in DOCS_TO_CONVERT:
    md_path = os.path.join(DOCS_DIR, f"{doc_name}.md")
    html_path = os.path.join(DOCS_DIR, f"{doc_name}.html")
    pdf_path = os.path.join(DOCS_DIR, f"{doc_name}.pdf")

    if not os.path.isfile(md_path):
        continue

    with open(md_path, "r", encoding="utf-8") as f:
        lines = f.readlines()

    html_lines = ["<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>" + doc_name + "</title>" + css + "</head><body>"]
    in_code = False
    code_buf = []
    table_buf = []

    for line in lines:
        line_str = line.rstrip("\r\n")

        if line_str.startswith("```"):
            if in_code:
                in_code = False
                code_text = html.escape("\n".join(code_buf))
                html_lines.append(f"<pre><code>{code_text}</code></pre>")
                code_buf = []
            else:
                in_code = True
                code_buf = []
            continue

        if in_code:
            code_buf.append(line_str)
            continue

        if line_str.startswith("|") and line_str.endswith("|"):
            table_buf.append(line_str)
            continue
        else:
            if table_buf:
                t_html = ["<table>"]
                is_header = True
                for t_line in table_buf:
                    if re.match(r"^\|(\s*:?-+:?\s*\|)+$", t_line):
                        is_header = False
                        continue
                    cells = [c.strip() for c in t_line.strip("|").split("|")]
                    tag = "th" if is_header else "td"
                    row_html = "".join([f"<{tag}>{process_inline(c)}</{tag}>" for c in cells])
                    t_html.append(f"<tr>{row_html}</tr>")
                t_html.append("</table>")
                html_lines.append("\n".join(t_html))
                table_buf = []

        if not line_str.strip():
            html_lines.append("")
            continue

        if line_str.startswith("# "):
            html_lines.append(f"<h1>{process_inline(line_str[2:])}</h1>")
        elif line_str.startswith("## "):
            html_lines.append(f"<h2>{process_inline(line_str[3:])}</h2>")
        elif line_str.startswith("### "):
            html_lines.append(f"<h3>{process_inline(line_str[4:])}</h3>")
        elif line_str.startswith("#### "):
            html_lines.append(f"<h4>{process_inline(line_str[5:])}</h4>")
        elif line_str == "---":
            html_lines.append("<hr>")
        elif line_str.startswith("- "):
            html_lines.append(f"<li>{process_inline(line_str[2:])}</li>")
        elif re.match(r"^\d+\.\s", line_str):
            m = re.match(r"^\d+\.\s(.*)", line_str)
            html_lines.append(f"<li>{process_inline(m.group(1))}</li>")
        else:
            html_lines.append(f"<p>{process_inline(line_str)}</p>")

    if table_buf:
        t_html = ["<table>"]
        is_header = True
        for t_line in table_buf:
            if re.match(r"^\|(\s*:?-+:?\s*\|)+$", t_line):
                is_header = False
                continue
            cells = [c.strip() for c in t_line.strip("|").split("|")]
            tag = "th" if is_header else "td"
            row_html = "".join([f"<{tag}>{process_inline(c)}</{tag}>" for c in cells])
            t_html.append(f"<tr>{row_html}</tr>")
        t_html.append("</table>")
        html_lines.append("\n".join(t_html))

    html_lines.append("</body></html>")

    with open(html_path, "w", encoding="utf-8") as f:
        f.write("\n".join(html_lines))

    cmd = ["libreoffice", "--headless", "--convert-to", "pdf", html_path, "--outdir", DOCS_DIR]
    subprocess.run(cmd, capture_output=True, text=True)
    if os.path.isfile(pdf_path):
        print(f"✓ Generated: {pdf_path}")
