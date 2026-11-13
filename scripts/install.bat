@echo off
setlocal enabledelayedexpansion

echo =============================================
echo    Installing JavaCPUtils for Windows
echo =============================================

set "INSTALL_DIR=%USERPROFILE%\.javacputils"
set "BIN_DIR=%INSTALL_DIR%\bin"
set "JAR_PATH=%INSTALL_DIR%\javacputils.jar"

if not exist "%INSTALL_DIR%" mkdir "%INSTALL_DIR%"
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

echo Downloading javacputils.jar from GitHub...
curl -fsSL "https://github.com/shaheed3515/javacputils/releases/download/v1.0.0/javacputils-1.0.0.jar" -o "%JAR_PATH%"
curl -fsSL "https://raw.githubusercontent.com/shaheed3515/javacputils/main/scripts/bundle.py" -o "%BIN_DIR%\bundle.py"

:: Create batch wrapper for bundle command
(
echo @echo off
echo python "%%~dp0bundle.py" %%*
) > "%BIN_DIR%\bundle.bat"

echo Configuring user environment variables...
:: Add to CLASSPATH
for /f "tokens=2*" %%A in ('reg query "HKCU\Environment" /v CLASSPATH 2^>nul') do set "CUR_CP=%%B"
if defined CUR_CP (
    echo !CUR_CP! | findstr /i "%JAR_PATH%" >nul
    if errorlevel 1 (
        setx CLASSPATH ".;%JAR_PATH%;!CUR_CP!" >nul
    )
) else (
    setx CLASSPATH ".;%JAR_PATH%" >nul
)

:: Add bin to PATH
for /f "tokens=2*" %%A in ('reg query "HKCU\Environment" /v Path 2^>nul') do set "CUR_PATH=%%B"
echo !CUR_PATH! | findstr /i "%BIN_DIR%" >nul
if errorlevel 1 (
    setx Path "%BIN_DIR%;!CUR_PATH!" >nul
)

echo.
echo [OK] JavaCPUtils successfully installed!
echo 1. Open a NEW Command Prompt or PowerShell window.
echo 2. You can now use: import cp.*; in any Java file.
echo 3. To bundle for Codeforces: bundle Solution.java
echo =============================================
pause
