#!/usr/bin/env python3
import os
import re
import html
import subprocess

SCRIPT_DIR = os.path.dirname(os.path.realpath(__file__))
PROJECT_DIR = os.path.abspath(os.path.join(SCRIPT_DIR, ".."))
DOCS_DIR = os.path.join(PROJECT_DIR, "docs")
MD_PATH = os.path.join(DOCS_DIR, "JavaCPUtils_Complete_Guide.md")
HTML_PATH = os.path.join(DOCS_DIR, "JavaCPUtils_Complete_Guide.html")
PDF_PATH = os.path.join(DOCS_DIR, "JavaCPUtils_Complete_Guide.pdf")

with open(MD_PATH, "r", encoding="utf-8") as f:
    lines = f.readlines()

html_lines = []
in_code = False
code_lang = ""
code_buf = []
table_buf = []

css = """
<style>
@page {
    size: A4;
    margin: 18mm 14mm 18mm 14mm;
}
body {
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
    line-height: 1.55;
    color: #24292e;
    max-width: 900px;
    margin: 0 auto;
    font-size: 10.5pt;
}
h1 {
    color: #0969da;
    border-bottom: 2px solid #d0d7de;
    padding-bottom: 0.3em;
    font-size: 20pt;
    margin-top: 0;
}
h2 {
    color: #1f2328;
    border-bottom: 1px solid #d0d7de;
    padding-bottom: 0.25em;
    font-size: 14pt;
    margin-top: 1.4em;
    page-break-after: avoid;
}
h3 {
    color: #32383f;
    font-size: 12pt;
    margin-top: 1.1em;
    page-break-after: avoid;
}
code {
    background-color: #eff1f3;
    padding: 0.2em 0.4em;
    border-radius: 4px;
    font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, monospace;
    font-size: 88%;
    color: #cf222e;
}
pre {
    background-color: #f6f8fa;
    border: 1px solid #d0d7de;
    border-radius: 6px;
    padding: 10px 14px;
    font-family: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, monospace;
    font-size: 9pt;
    line-height: 1.4;
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
    margin: 1em 0;
    font-size: 9.5pt;
    page-break-inside: avoid;
}
table, th, td {
    border: 1px solid #d0d7de;
}
th {
    background-color: #f6f8fa;
    padding: 6px 10px;
    font-weight: 600;
    text-align: left;
}
td {
    padding: 6px 10px;
}
tr:nth-child(even) {
    background-color: #fcfdfe;
}
hr {
    border: 0;
    height: 1px;
    background: #d0d7de;
    margin: 1.2em 0;
}
ul, ol {
    padding-left: 1.8em;
}
li {
    margin: 0.25em 0;
}
</style>
"""

html_lines.append("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>JavaCPUtils Guide</title>" + css + "</head><body>")

def process_inline(text):
    text = html.escape(text)
    text = re.sub(r"\*\*(.+?)\*\*", r"<strong>\1</strong>", text)
    text = re.sub(r"`([^`]+)`", r"<code>\1</code>", text)
    text = re.sub(r"\[([^\]]+)\]\(([^)]+)\)", r'<a href="\2">\1</a>', text)
    return text

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
            code_lang = line_str[3:].strip()
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
    elif line_str.startswith("- [ ] ") or line_str.startswith("- [x] "):
        checked = "checked" if "[x]" in line_str else ""
        content = process_inline(line_str[6:])
        html_lines.append(f"<div style=\"margin: 3px 0;\"><input type=\"checkbox\" {checked} disabled> {content}</div>")
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

with open(HTML_PATH, "w", encoding="utf-8") as f:
    f.write("\n".join(html_lines))

print("HTML generated at:", HTML_PATH)

# Run LibreOffice to produce PDF
cmd = ["libreoffice", "--headless", "--convert-to", "pdf", HTML_PATH, "--outdir", DOCS_DIR]
res = subprocess.run(cmd, capture_output=True, text=True)
print("LibreOffice output:", res.stdout, res.stderr)
if os.path.isfile(PDF_PATH):
    print("PDF successfully generated at:", PDF_PATH)
