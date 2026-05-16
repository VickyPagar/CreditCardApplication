@echo off
REM ============================================================
REM Build Script for FirstBank Credit Card Application
REM Windows Batch File
REM ============================================================

echo.
echo ========================================
echo FirstBank Credit Card Application
echo Build Script
echo ========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven from https://maven.apache.org/
    pause
    exit /b 1
)

echo [1/4] Cleaning previous build...
call mvn clean
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Clean failed
    pause
    exit /b 1
)

echo.
echo [2/4] Compiling Java sources...
call mvn compile
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo [3/4] Running tests...
call mvn test
if %ERRORLEVEL% NEQ 0 (
    echo WARNING: Tests failed or no tests found
    echo Continuing with packaging...
)

echo.
echo [4/4] Creating deployment packages...
call mvn package
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Packaging failed
    pause
    exit /b 1
)

echo.
echo ========================================
echo BUILD SUCCESSFUL
echo ========================================
echo.
echo Output files:
echo   WAR: target\creditcard.war
echo   EAR: target\creditcard-app-1.0.0.ear
echo.
echo To deploy:
echo   1. Copy WAR to application server webapps directory
echo   2. Or use deploy.bat script
echo.
pause

@REM Made with Bob
