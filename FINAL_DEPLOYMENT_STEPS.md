# Final Deployment Steps - H2 Database Configuration

## ✅ What's Already Fixed:
1. **struts-config.xml** - Element ordering corrected
2. **jboss-deployment-structure.xml** - Updated for H2 (no Oracle dependency)

## 🔧 What You Need to Do Now:

### Step 1: Fix standalone.xml Datasource Configuration

**Location**: `C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\configuration\standalone.xml`

**Find this section** (around line 150-160):
```xml
<datasource jndi-name="java:/jdbc/FirstBankCCDS" pool-name="FirstBankCCDS" enabled="true">
    <connection-url>jdbc:h2:tcp://localhost:9092/C:/Users/VaibhavPagar/creditcard;MODE=Oracle</connection-url>
    <driver>h2</driver>
    <security>
        <user-name>sa</user-name>
        <password>sa</password>
    </security>
</datasource>
```

**Replace the `<security>` section with this single line**:
```xml
<datasource jndi-name="java:/jdbc/FirstBankCCDS" pool-name="FirstBankCCDS" enabled="true">
    <connection-url>jdbc:h2:tcp://localhost:9092/C:/Users/VaibhavPagar/creditcard;MODE=Oracle</connection-url>
    <driver>h2</driver>
    <security user-name="sa" password="sa"/>
</datasource>
```

**The ONLY change**: 
- ❌ WRONG: `<security><user-name>sa</user-name><password>sa</password></security>`
- ✅ CORRECT: `<security user-name="sa" password="sa"/>`

### Step 2: Start H2 Database Server

Open a **NEW** command prompt and run:
```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
java -cp ..\modules\system\layers\base\com\h2database\h2\main\h2-*.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 9092 -baseDir C:\Users\VaibhavPagar
```

**Keep this window open!** The H2 server must be running.

You should see:
```
TCP server running at tcp://localhost:9092 (others can connect)
```

### Step 3: Create Database Tables (Optional - First Time Only)

If you want to create tables manually:

1. Open H2 Console in browser:
   ```cmd
   java -cp C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base\com\h2database\h2\main\h2-*.jar org.h2.tools.Console
   ```

2. Connect with:
   - JDBC URL: `jdbc:h2:tcp://localhost:9092/C:/Users/VaibhavPagar/creditcard;MODE=Oracle`
   - User: `sa`
   - Password: `sa`

3. Run your SQL scripts:
   - `sql/schema.sql`
   - `sql/procedures.sql`

### Step 4: Rebuild Application

```cmd
cd C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app
build.bat
```

Wait for:
```
[INFO] BUILD SUCCESS
```

### Step 5: Deploy to WildFly

```cmd
copy target\creditcard.war C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\
```

### Step 6: Start WildFly

Open a **NEW** command prompt:
```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
standalone.bat
```

### Step 7: Verify Deployment

Watch the console for these messages:
```
✅ INFO  [org.jboss.as.connector.subsystems.datasources] WFLYJCA0001: Bound data source [java:/jdbc/FirstBankCCDS]
✅ INFO  [org.wildfly.extension.undertow] WFLYUT0021: Registered web context: '/creditcard'
✅ INFO  [org.jboss.as.server] WFLYSRV0010: Deployed "creditcard.war"
```

**NO MORE ERRORS** about:
- ❌ struts-config.xml validation
- ❌ Oracle JDBC driver ClassNotFoundException
- ❌ DataSource initialization

### Step 8: Test Application

Open browser:
- **Home**: http://localhost:8081/creditcard/
- **Apply for Card**: http://localhost:8081/creditcard/showPersonalInfo.do
- **Check Status**: http://localhost:8081/creditcard/showStatusInquiry.do

## 🚀 Quick All-in-One Script

Save this as `deploy.bat` in your project folder:

```batch
@echo off
echo ========================================
echo Credit Card Application Deployment
echo ========================================

echo.
echo Step 1: Rebuilding application...
call build.bat
if errorlevel 1 (
    echo Build failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Copying WAR to WildFly...
copy /Y target\creditcard.war C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\

echo.
echo ========================================
echo Deployment Complete!
echo ========================================
echo.
echo IMPORTANT: Make sure these are running:
echo 1. H2 Database Server (port 9092)
echo 2. WildFly Server (port 8081)
echo.
echo Access application at:
echo http://localhost:8081/creditcard/
echo.
pause
```

## 📋 Checklist

Before testing, ensure:
- [ ] standalone.xml has correct `<security user-name="sa" password="sa"/>` format
- [ ] H2 database server is running on port 9092
- [ ] Application rebuilt with `build.bat`
- [ ] WAR file copied to deployments folder
- [ ] WildFly started and shows successful deployment
- [ ] No errors in WildFly console

## 🐛 Troubleshooting

### Issue: "Connection refused" or "Database not found"
**Solution**: Start H2 server first (Step 2)

### Issue: Still getting Oracle driver error
**Solution**: Make sure you updated `jboss-deployment-structure.xml` (already done)

### Issue: Struts validation error
**Solution**: Already fixed in `struts-config.xml`

### Issue: Port 9092 already in use
**Solution**: 
```cmd
netstat -ano | findstr :9092
taskkill /PID <process_id> /F
```

### Issue: Port 8081 already in use
**Solution**: Change port in standalone.xml or stop other service

## 📝 Summary

**You only need to**:
1. Fix one line in standalone.xml (security element)
2. Start H2 database server
3. Rebuild with build.bat
4. Deploy WAR file
5. Start WildFly
6. Test!

All code issues are already fixed! 🎉