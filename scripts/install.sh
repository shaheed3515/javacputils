#!/usr/bin/env bash
# JavaCPUtils 1-Click Installer for Linux & macOS
set -e

INSTALL_DIR="$HOME/.javacputils"
BIN_DIR="$INSTALL_DIR/bin"
JAR_NAME="javacputils.jar"

echo "============================================="
echo "   Installing JavaCPUtils for Java CP        "
echo "============================================="

mkdir -p "$INSTALL_DIR"
mkdir -p "$BIN_DIR"

# If running from cloned repo, copy local target JAR; otherwise download from GitHub
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
LOCAL_JAR="$SCRIPT_DIR/../target/javacputils-1.0.0.jar"

if [ -f "$LOCAL_JAR" ]; then
    cp "$LOCAL_JAR" "$INSTALL_DIR/$JAR_NAME"
    cp "$SCRIPT_DIR/bundle.py" "$BIN_DIR/bundle.py"
    cp "$SCRIPT_DIR/bundle" "$BIN_DIR/bundle"
else
    echo "Downloading latest release from GitHub..."
    curl -fsSL "https://github.com/shaheed3515/javacputils/releases/download/v1.0.0/javacputils-1.0.0.jar" -o "$INSTALL_DIR/$JAR_NAME"
    curl -fsSL "https://raw.githubusercontent.com/shaheed3515/javacputils/main/scripts/bundle.py" -o "$BIN_DIR/bundle.py"
    curl -fsSL "https://raw.githubusercontent.com/shaheed3515/javacputils/main/scripts/bundle" -o "$BIN_DIR/bundle"
fi

chmod +x "$BIN_DIR/bundle" "$BIN_DIR/bundle.py"

# Configure shell profile (.bashrc / .zshrc)
SHELL_RC="$HOME/.bashrc"
if [ -n "$ZSH_VERSION" ] || [ "$SHELL" = "/bin/zsh" ]; then
    SHELL_RC="$HOME/.zshrc"
fi

CONFIG_BLOCK='
# >>> JavaCPUtils Configuration >>>
export CLASSPATH=".:'"$INSTALL_DIR"'/'"$JAR_NAME"':$CLASSPATH"
export PATH="'"$BIN_DIR"':$PATH"
# <<< JavaCPUtils Configuration <<<
'

if ! grep -q "JavaCPUtils Configuration" "$SHELL_RC" 2>/dev/null; then
    echo "$CONFIG_BLOCK" >> "$SHELL_RC"
    echo "✓ Added JavaCPUtils to $SHELL_RC"
fi

echo ""
echo "🎉 SUCCESS: JavaCPUtils is installed!"
echo "1. Restart your terminal or run: source $SHELL_RC"
echo "2. In your Java code, simply use: import cp.*;"
echo "3. When ready to submit to Codeforces/LeetCode, run: bundle Solution.java"
echo "============================================="

