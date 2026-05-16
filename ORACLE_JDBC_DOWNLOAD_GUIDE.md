# Oracle JDBC Driver Download Guide

## Quick Download Options

### Option 1: Maven Central (Recommended - No Oracle Account Required)

**Direct Download Links:**

For **Java 8**:
- https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/21.9.0.0/ojdbc8-21.9.0.0.jar

For **Java 11+**:
- https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc11/21.9.0.0/ojdbc11-21.9.0.0.jar

**Steps:**
1. Click the link above (choose ojdbc8 for Java 8-11, or ojdbc11 for Java 11+)
2. Your browser will download the JAR file
3. Save it to `C:\temp\ojdbc8.jar` (or any location you prefer)
4. Continue with WildFly installation steps

---

### Option 2: Oracle Official Website (Requires Oracle Account)

**URL:** https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html

**Steps:**

1. **Go to Oracle JDBC Downloads**
   - Open: https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html

2. **Choose Your Version**
   - For Oracle Database 19c: Click "Oracle Database 19c (19.3) JDBC Driver & UCP Downloads"
   - For Oracle Database 21c: Click "Oracle Database 21c (21.3) JDBC Driver & UCP Downloads"

3. **Select the Right JAR**
   - **ojdbc8.jar** - For JDK 8, JDK 9, JDK 10, JDK 11
   - **ojdbc10.jar** - For JDK 10, JDK 11
   - **ojdbc11.jar** - For JDK 11, JDK 12, JDK 13, JDK 14, JDK 15, JDK 16, JDK 17

4. **Accept License Agreement**
   - Check "I reviewed and accept the Oracle License Agreement"

5. **Sign In (if required)**
   - You may need to create a free Oracle account
   - Click "Create Account" if you don't have one
   - Fill in the registration form

6. **Download**
   - Click the download link for your chosen JAR file
   - Save to `C:\temp\ojdbc8.jar`

---

### Option 3: Using Maven (Add to pom.xml)

If you want Maven to handle the download automatically:

```xml
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc8</artifactId>
    <version>21.9.0.0</version>
</dependency>
```

Then run:
```cmd
mvn dependency:copy-dependencies
```

The JAR will be in: `target/dependency/ojdbc8-21.9.0.0.jar`

---

### Option 4: Using PowerShell (Windows)

Open PowerShell and run:

```powershell
# Create temp directory if it doesn't exist
New-Item -ItemType Directory -Force -Path C:\temp

# Download ojdbc8.jar
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/21.9.0.0/ojdbc8-21.9.0.0.jar" -OutFile "C:\temp\ojdbc8.jar"

Write-Host "Downloaded to C:\temp\ojdbc8.jar"
```

---

### Option 5: Using curl (Linux/Mac/Windows)

```bash
# Create temp directory
mkdir -p /tmp

# Download ojdbc8.jar
curl -o /tmp/ojdbc8.jar https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/21.9.0.0/ojdbc8-21.9.0.0.jar

echo "Downloaded to /tmp/ojdbc8.jar"
```

---

## Which Version Should I Use?

| Your Java Version | Recommended JDBC Driver | Download Link |
|-------------------|------------------------|---------------|
| Java 8 | ojdbc8.jar | [Download](https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/21.9.0.0/ojdbc8-21.9.0.0.jar) |
| Java 11 | ojdbc8.jar or ojdbc11.jar | [Download ojdbc8](https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/21.9.0.0/ojdbc8-21.9.0.0.jar) or [Download ojdbc11](https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc11/21.9.0.0/ojdbc11-21.9.0.0.jar) |
| Java 17+ | ojdbc11.jar | [Download](https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc11/21.9.0.0/ojdbc11-21.9.0.0.jar) |

**Check your Java version:**
```cmd
java -version
```

---

## Verification

After downloading, verify the file:

### Windows:
```cmd
dir C:\temp\ojdbc8.jar
```

You should see a file around 4-5 MB in size.

### Linux/Mac:
```bash
ls -lh /tmp/ojdbc8.jar
```

---

## Next Steps After Download

Once you have the JAR file, follow these steps from `WILDFLY_ORACLE_SETUP.md`:

1. **Create Module Directory:**
   ```cmd
   cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base
   mkdir com\oracle\ojdbc\main
   ```

2. **Copy JAR File:**
   ```cmd
   copy C:\temp\ojdbc8.jar C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base\com\oracle\ojdbc\main\
   ```

3. **Create module.xml** in the same directory with this content:
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <module xmlns="urn:jboss:module:1.9" name="com.oracle.ojdbc">
       <resources>
           <resource-root path="ojdbc8.jar"/>
       </resources>
       <dependencies>
           <module name="javax.api"/>
           <module name="javax.transaction.api"/>
       </dependencies>
   </module>
   ```

4. **Continue with WildFly configuration** as described in `WILDFLY_ORACLE_SETUP.md`

---

## Troubleshooting Downloads

### Issue: Download blocked by firewall
- Try using a different network
- Use Maven Central option (usually not blocked)
- Ask your IT department to whitelist repo1.maven.org

### Issue: File corrupted
- Re-download the file
- Verify file size (should be 4-5 MB)
- Try a different download method

### Issue: Can't access Oracle website
- Use Maven Central option instead (no account required)
- Use PowerShell/curl download method

---

## Alternative: Use H2 Database (No Oracle Driver Needed)

If you just want to test the application without Oracle:

1. **Skip Oracle driver installation completely**
2. **Use H2 database** (already included in WildFly)
3. **Update datasource** in `standalone.xml`:
   ```xml
   <datasource jndi-name="java:/jdbc/FirstBankCCDS" 
               pool-name="FirstBankCCDS" 
               enabled="true">
       <connection-url>jdbc:h2:~/creditcard;MODE=Oracle</connection-url>
       <driver>h2</driver>
       <security user-name="sa" password=""/>
   </datasource>
   ```
4. **Comment out Oracle dependency** in `WebContent/WEB-INF/jboss-deployment-structure.xml`:
   ```xml
   <!-- <module name="com.oracle.ojdbc" export="true"/> -->
   ```

---

## Quick Start Command (PowerShell - All in One)

Copy and paste this into PowerShell to download and install in one go:

```powershell
# Download
New-Item -ItemType Directory -Force -Path C:\temp
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/21.9.0.0/ojdbc8-21.9.0.0.jar" -OutFile "C:\temp\ojdbc8.jar"

# Create module directory
$modulePath = "C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base\com\oracle\ojdbc\main"
New-Item -ItemType Directory -Force -Path $modulePath

# Copy JAR
Copy-Item "C:\temp\ojdbc8.jar" -Destination $modulePath

# Create module.xml
$moduleXml = @"
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.9" name="com.oracle.ojdbc">
    <resources>
        <resource-root path="ojdbc8.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>
"@
$moduleXml | Out-File -FilePath "$modulePath\module.xml" -Encoding UTF8

Write-Host "Oracle JDBC driver installed successfully!"
Write-Host "Next: Configure datasource in standalone.xml"
```

---

## Summary

**Easiest Method:** Click this link and save the file:
👉 https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/21.9.0.0/ojdbc8-21.9.0.0.jar

Then follow the installation steps in `WILDFLY_ORACLE_SETUP.md`

Good luck! 🚀