# How to Run Credit Card Application - Step by Step

## 🎯 Prerequisites
- Java JDK installed
- Maven installed
- WildFly 26 installed at: `C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final`

---

## 📝 Step-by-Step Instructions

### STEP 1: Fix standalone.xml Configuration

1. **Open the file**:
   ```
   C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\configuration\standalone.xml
   ```

2. **Find this section** (search for "FirstBankCCDS"):
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

3. **Change ONLY the `<security>` part to**:
   ```xml
   <datasource jndi-name="java:/jdbc/FirstBankCCDS" pool-name="FirstBankCCDS" enabled="true">
       <connection-url>jdbc:h2:tcp://localhost:9092/C:/Users/VaibhavPagar/creditcard;MODE=Oracle</connection-url>
       <driver>h2</driver>
       <security user-name="sa" password="sa"/>
   </datasource>
   ```

4. **Save the file** (Ctrl+S)

---

### STEP 2: Start H2 Database Server

1. **Open Command Prompt #1** (Run as Administrator)

2. **Run these commands**:
   ```cmd
   cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
   
   java -cp ..\modules\system\layers\base\com\h2database\h2\main\h2-*.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 9092 -baseDir C:\Users\VaibhavPagar
   ```

3. **You should see**:
   ```
   TCP server running at tcp://localhost:9092 (others can connect)
   ```

4. **KEEP THIS WINDOW OPEN!** Don't close it.

---

### STEP 3: Rebuild the Application

1. **Open Command Prompt #2** (New window)

2. **Run these commands**:
   ```cmd
   cd C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app
   
   build.bat
   ```

3. **Wait for**:
   ```
   [INFO] BUILD SUCCESS
   [INFO] Building war: ...\target\creditcard.war
   ```

---

### STEP 4: Deploy to WildFly

1. **In the same Command Prompt #2**, run:
   ```cmd
   copy target\creditcard.war C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\
   ```

2. **You should see**:
   ```
   1 file(s) copied.
   ```

---

### STEP 5: Start WildFly Server

1. **Open Command Prompt #3** (New window)

2. **Run these commands**:
   ```cmd
   cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
   
   standalone.bat
   ```

3. **Wait for these messages** (scroll through the output):
   ```
   ✅ INFO  [org.jboss.as.connector.subsystems.datasources] WFLYJCA0001: Bound data source [java:/jdbc/FirstBankCCDS]
   ✅ INFO  [org.wildfly.extension.undertow] WFLYUT0021: Registered web context: '/creditcard'
   ✅ INFO  [org.jboss.as.server] WFLYSRV0010: Deployed "creditcard.war"
   ✅ INFO  [org.jboss.as] WFLYSRV0025: WildFly Full 26.1.3.Final started
   ```

4. **KEEP THIS WINDOW OPEN!** This is your WildFly server.

---

### STEP 6: Test the Application

1. **Open your web browser** (Chrome, Firefox, Edge)

2. **Go to**: http://localhost:8081/creditcard/

3. **You should see** the Credit Card Application home page

4. **Try these pages**:
   - Home: http://localhost:8081/creditcard/
   - Apply for Card: http://localhost:8081/creditcard/showPersonalInfo.do
   - Check Status: http://localhost:8081/creditcard/showStatusInquiry.do

---

## 🖥️ What You Should Have Running

After following all steps, you should have **3 Command Prompt windows open**:

1. **Window 1**: H2 Database Server (port 9092)
2. **Window 2**: Can be closed after deployment
3. **Window 3**: WildFly Server (port 8081)

---

## 🔄 To Restart Everything

If you need to restart:

1. **Stop WildFly**: Press `Ctrl+C` in Window 3
2. **Stop H2**: Press `Ctrl+C` in Window 1
3. **Start H2 again**: Repeat STEP 2
4. **Start WildFly again**: Repeat STEP 5

---

## 🐛 Common Issues & Solutions

### Issue 1: "Port 9092 already in use"

**Solution**:
```cmd
netstat -ano | findstr :9092
taskkill /PID <process_id> /F
```
Then restart H2 server.

---

### Issue 2: "Port 8081 already in use"

**Solution**:
```cmd
netstat -ano | findstr :8081
taskkill /PID <process_id> /F
```
Then restart WildFly.

---

### Issue 3: "BUILD FAILURE" when running build.bat

**Solution**:
```cmd
mvn clean
mvn package
```

---

### Issue 4: Application not deploying

**Solution**:
1. Stop WildFly (Ctrl+C)
2. Delete old deployment:
   ```cmd
   del C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\creditcard.war*
   ```
3. Copy WAR again
4. Start WildFly

---

### Issue 5: "Connection refused" or database errors

**Solution**:
- Make sure H2 server is running (Window 1)
- Check that you see "TCP server running at tcp://localhost:9092"

---

## 📋 Quick Checklist

Before testing, verify:
- [ ] standalone.xml has `<security user-name="sa" password="sa"/>` (not nested)
- [ ] H2 server is running (Window 1 open)
- [ ] Application built successfully (BUILD SUCCESS)
- [ ] WAR file copied to deployments folder
- [ ] WildFly started (Window 3 open)
- [ ] No errors in WildFly console
- [ ] Can access http://localhost:8081/creditcard/

---

## 🎯 Summary - The 6 Steps

1. ✏️ Fix standalone.xml (one line change)
2. ▶️ Start H2 Database Server
3. 🔨 Build application (build.bat)
4. 📦 Copy WAR to deployments
5. ▶️ Start WildFly Server
6. 🌐 Open browser and test

---

## 💡 Pro Tip: Create a Startup Script

Save this as `start-all.bat` in your project folder:

```batch
@echo off
echo ========================================
echo Starting Credit Card Application
echo ========================================

echo.
echo Starting H2 Database Server...
start "H2 Database" cmd /k "cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin && java -cp ..\modules\system\layers\base\com\h2database\h2\main\h2-*.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 9092 -baseDir C:\Users\VaibhavPagar"

echo Waiting 5 seconds for H2 to start...
timeout /t 5 /nobreak

echo.
echo Starting WildFly Server...
start "WildFly Server" cmd /k "cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin && standalone.bat"

echo.
echo ========================================
echo Servers Starting!
echo ========================================
echo.
echo Wait 30 seconds, then open:
echo http://localhost:8081/creditcard/
echo.
pause
```

Then just double-click `start-all.bat` to start everything!

---

## 🎉 That's It!

Follow these 6 steps and your application will be running!

**Need help?** Check the error messages in the WildFly console (Window 3).