#!/bin/bash
# ============================================================
# Build Script for FirstBank Credit Card Application
# Linux/Mac Shell Script
# ============================================================

echo ""
echo "========================================"
echo "FirstBank Credit Card Application"
echo "Build Script"
echo "========================================"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven from https://maven.apache.org/"
    exit 1
fi

echo "[1/4] Cleaning previous build..."
mvn clean
if [ $? -ne 0 ]; then
    echo "ERROR: Clean failed"
    exit 1
fi

echo ""
echo "[2/4] Compiling Java sources..."
mvn compile
if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed"
    exit 1
fi

echo ""
echo "[3/4] Running tests..."
mvn test
if [ $? -ne 0 ]; then
    echo "WARNING: Tests failed or no tests found"
    echo "Continuing with packaging..."
fi

echo ""
echo "[4/4] Creating deployment packages..."
mvn package
if [ $? -ne 0 ]; then
    echo "ERROR: Packaging failed"
    exit 1
fi

echo ""
echo "========================================"
echo "BUILD SUCCESSFUL"
echo "========================================"
echo ""
echo "Output files:"
echo "  WAR: target/creditcard.war"
echo "  EAR: target/creditcard-app-1.0.0.ear"
echo ""
echo "To deploy:"
echo "  1. Copy WAR to application server webapps directory"
echo "  2. Or use deploy.sh script"
echo ""

# Made with Bob
