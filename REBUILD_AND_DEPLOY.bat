@echo off
echo ========================================
echo Rebuild and Redeploy Credit Card App
echo ========================================

echo.
echo Step 1: Stopping WildFly (if running)...
echo Press Ctrl+C in the WildFly window to stop it
echo.
pause

echo.
echo Step 2: Cleaning old deployment...
del "C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\creditcard.war" 2>nul
del "C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\creditcard.war.deployed" 2>nul
del "C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\creditcard.war.failed" 2>nul
del "C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\creditcard.war.isdeploying" 2>nul
echo Old deployment files removed.

echo.
echo Step 3: Rebuilding application...
call build.bat
if errorlevel 1 (
    echo.
    echo BUILD FAILED!
    pause
    exit /b 1
)

echo.
echo Step 4: Deploying new WAR file...
copy /Y "target\creditcard.war" "C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\"
if errorlevel 1 (
    echo.
    echo DEPLOYMENT FAILED!
    pause
    exit /b 1
)

echo.
echo ========================================
echo SUCCESS!
echo ========================================
echo.
echo Now start WildFly:
echo cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
echo standalone.bat
echo.
echo Then test at: http://localhost:8081/creditcard/
echo.
pause

@REM Made with Bob
