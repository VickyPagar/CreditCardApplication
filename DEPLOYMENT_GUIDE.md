# Complete Deployment Guide - FirstBank Credit Card Application

## Table of Contents
1. [Required Software](#required-software)
2. [Installation Steps](#installation-steps)
3. [Database Setup](#database-setup)
4. [Application Server Configuration](#application-server-configuration)
5. [Building the Application](#building-the-application)
6. [Deployment](#deployment)
7. [Testing](#testing)
8. [Troubleshooting](#troubleshooting)

---

## Required Software

### 1. Java Development Kit (JDK)
- **Version**: JDK 8 or higher (JDK 11 recommended)
- **Download**: https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/
- **Why**: Required to compile and run Java applications

### 2. Apache Maven
- **Version**: 3.6.0 or higher
- **Download**: https://maven.apache.org/download.cgi
- **Why**: Build tool for compiling and packaging the application

### 3. Database (Choose ONE)

#### Option A: Oracle Database (Production)
- **Version**: Oracle 11g or higher
- **Download**: https://www.oracle.com/database/technologies/oracle-database-software-downloads.html
- **Why**: Original database for this application

#### Option B: H2 Database (Testing/Development)
- **Version**: Latest
- **Download**: http://www.h2database.com/
- **Why**: Lightweight, easy to set up for testing

### 4. Application Server (Choose ONE)

#### Option A: Apache Tomcat (Recommended for beginners)
- **Version**: Tomcat 9.0 or higher
- **Download**: https://tomcat.apache.org/download-90.cgi
- **Why**: Lightweight, easy to configure

#### Option B: JBoss/WildFly (Full Java EE support)
- **Version**: WildFly 26 or higher
- **Download**: https://www.wildfly.org/downloads/
- **Why**: Full EJB support, enterprise features

### 5. Optional Tools
- **Git**: For version control
- **DBeaver** or **SQL Developer**: Database management GUI
- **Postman** or **SoapUI**: For testing web services

---

## Installation Steps

### Step 1: Install JDK

#### Windows:
1. Download JDK installer from https://adoptium.net/
2. Run the installer (e.g., `OpenJDK11U-jdk_x64_windows_hotspot_11.0.XX.msi`)
3. Follow installation wizard (default settings are fine)
4. Set JAVA_HOME environment variable:
   ```cmd
   setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-11.0.XX-hotspot"
   setx PATH "%PATH%;%JAVA_HOME%\bin"
   ```
5. Verify installation:
   ```cmd
   java -version
   javac -version
   ```

#### Linux/Mac:
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-11-jdk

# Mac (using Homebrew)
brew install openjdk@11

# Verify
java -version
javac -version
```

### Step 2: Install Maven

#### Windows:
1. Download Maven from https://maven.apache.org/download.cgi
2. Extract to `C:\Program Files\Apache\maven`
3. Set environment variables:
   ```cmd
   setx MAVEN_HOME "C:\Program Files\Apache\maven"
   setx PATH "%PATH%;%MAVEN_HOME%\bin"
   ```
4. Verify:
   ```cmd
   mvn -version
   ```

#### Linux/Mac:
```bash
# Ubuntu/Debian
sudo apt install maven

# Mac
brew install maven

# Verify
mvn -version
```

### Step 3: Install Database

#### Option A: H2 Database (Quick Setup)

1. Download H2 from http://www.h2database.com/
2. Extract the zip file
3. Start H2 Console:
   ```cmd
   # Windows
   cd h2\bin
   h2.bat

   # Linux/Mac
   cd h2/bin
   ./h2.sh
   ```
4. Browser will open at http://localhost:8082
5. Use these connection settings:
   - **JDBC URL**: `jdbc:h2:~/creditcard;MODE=Oracle`
   - **User**: `sa`
   - **Password**: (leave empty)

#### Option B: Oracle Database

1. Download Oracle Database Express Edition (XE)
2. Install following the wizard
3. Remember the SYS password you set
4. Start SQL*Plus:
   ```cmd
   sqlplus sys/your_password@localhost:1521/XE as sysdba
   ```

### Step 4: Install Apache Tomcat

#### Windows:
1. Download Tomcat 9 from https://tomcat.apache.org/download-90.cgi
   - Choose "32-bit/64-bit Windows Service Installer"
2. Run installer
3. Set installation directory (e.g., `C:\Program Files\Apache Software Foundation\Tomcat 9.0`)
4. Choose port (default 8080)
5. Set admin username/password
6. Complete installation
7. Set CATALINA_HOME:
   ```cmd
   setx CATALINA_HOME "C:\Program Files\Apache Software Foundation\Tomcat 9.0"
   ```

#### Linux/Mac:
```bash
# Download and extract
wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.XX/bin/apache-tomcat-9.0.XX.tar.gz
tar -xzf apache-tomcat-9.0.XX.tar.gz
sudo mv apache-tomcat-9.0.XX /opt/tomcat

# Set permissions
sudo chmod +x /opt/tomcat/bin/*.sh

# Set environment variable
echo 'export CATALINA_HOME=/opt/tomcat' >> ~/.bashrc
source ~/.bashrc
```

---

## Database Setup

### Step 1: Create Database User

#### For Oracle:
```sql
-- Connect as SYSDBA
sqlplus sys/password@localhost:1521/XE as sysdba

-- Create user
CREATE USER ccapp_user IDENTIFIED BY ccapp_password;
GRANT CONNECT, RESOURCE TO ccapp_user;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE PROCEDURE TO ccapp_user;
ALTER USER ccapp_user QUOTA UNLIMITED ON USERS;

-- Exit and reconnect as new user
exit
sqlplus ccapp_user/ccapp_password@localhost:1521/XE
```

#### For H2:
```sql
-- H2 automatically creates users, just connect with:
-- JDBC URL: jdbc:h2:~/creditcard;MODE=Oracle
-- User: ccapp_user
-- Password: ccapp_password
```

### Step 2: Run Schema Script

```sql
-- In SQL*Plus or H2 Console
@C:/Users/VaibhavPagar/Documents/workspacefinaljboss/credit_card_app/sql/schema.sql
```

Or copy-paste the contents of `sql/schema.sql` into the SQL console.

### Step 3: Run Stored Procedures

```sql
@C:/Users/VaibhavPagar/Documents/workspacefinaljboss/credit_card_app/sql/procedures.sql
```

### Step 4: Verify Database Setup

```sql
-- Check tables
SELECT table_name FROM user_tables;

-- Should see:
-- CC_CUSTOMER
-- CC_APPLICATION
-- CC_APPLICANT_FINANCIAL
-- CC_APPLICANT_EMPLOYMENT
-- CC_CARD_TYPE
-- CC_STATUS_CODE
-- CC_REJECTION_REASON
-- CC_CREDIT_CARD
-- CC_APPLICATION_AUDIT

-- Check seed data
SELECT * FROM CC_CARD_TYPE;
SELECT * FROM CC_STATUS_CODE;
```

---

## Application Server Configuration

### For Apache Tomcat

#### Step 1: Configure JNDI DataSource

Edit `%CATALINA_HOME%\conf\context.xml` (or `/opt/tomcat/conf/context.xml`):

```xml
<Context>
    <!-- Add this inside <Context> tag -->
    <Resource name="jdbc/FirstBankCCDS"
              auth="Container"
              type="javax.sql.DataSource"
              driverClassName="oracle.jdbc.driver.OracleDriver"
              url="jdbc:oracle:thin:@localhost:1521:XE"
              username="ccapp_user"
              password="ccapp_password"
              maxTotal="10"
              maxIdle="5"
              maxWaitMillis="10000"/>
</Context>
```

**For H2 Database**, use:
```xml
<Resource name="jdbc/FirstBankCCDS"
          auth="Container"
          type="javax.sql.DataSource"
          driverClassName="org.h2.Driver"
          url="jdbc:h2:~/creditcard;MODE=Oracle"
          username="ccapp_user"
          password="ccapp_password"
          maxTotal="10"
          maxIdle="5"
          maxWaitMillis="10000"/>
```

#### Step 2: Add JDBC Driver

**For Oracle:**
1. Download Oracle JDBC driver (ojdbc8.jar) from https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html
2. Copy to `%CATALINA_HOME%\lib\` (or `/opt/tomcat/lib/`)

**For H2:**
1. Download H2 jar from http://www.h2database.com/
2. Copy `h2-*.jar` to `%CATALINA_HOME%\lib\`

#### Step 3: Configure Users (Optional)

Edit `%CATALINA_HOME%\conf\tomcat-users.xml`:

```xml
<tomcat-users>
  <role rolename="manager-gui"/>
  <role rolename="admin-gui"/>
  <user username="admin" password="admin123" roles="manager-gui,admin-gui"/>
</tomcat-users>
```

---

## Building the Application

### Step 1: Navigate to Project Directory

```cmd
cd C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app
```

### Step 2: Clean and Build

#### Windows:
```cmd
# Using provided script
build.bat

# Or manually
mvn clean package
```

#### Linux/Mac:
```bash
# Using provided script
chmod +x build.sh
./build.sh

# Or manually
mvn clean package
```

### Step 3: Verify Build

Check for successful build output:
```
[INFO] BUILD SUCCESS
[INFO] Building war: ...\target\creditcard.war
```

The WAR file will be at: `target\creditcard.war`

---

## Deployment

### Method 1: Manual Deployment (Recommended)

#### Windows:
```cmd
# Copy WAR to Tomcat webapps
copy target\creditcard.war "%CATALINA_HOME%\webapps\"

# Start Tomcat
"%CATALINA_HOME%\bin\startup.bat"
```

#### Linux/Mac:
```bash
# Copy WAR to Tomcat webapps
cp target/creditcard.war $CATALINA_HOME/webapps/

# Start Tomcat
$CATALINA_HOME/bin/startup.sh
```

### Method 2: Tomcat Manager (GUI)

1. Start Tomcat
2. Open browser: http://localhost:8080/manager/html
3. Login with admin credentials
4. Scroll to "WAR file to deploy"
5. Click "Choose File" and select `target\creditcard.war`
6. Click "Deploy"

### Method 3: Hot Deployment

Simply copy the WAR file to `webapps` folder while Tomcat is running. Tomcat will automatically deploy it.

---

## Testing

### Step 1: Verify Deployment

1. Check Tomcat logs:
   ```cmd
   # Windows
   type "%CATALINA_HOME%\logs\catalina.out"
   
   # Linux/Mac
   tail -f $CATALINA_HOME/logs/catalina.out
   ```

2. Look for successful deployment messages

### Step 2: Access Application

Open browser and navigate to:
- **Home Page**: http://localhost:8080/creditcard/
- **Apply for Card**: http://localhost:8080/creditcard/showPersonalInfo.do
- **Check Status**: http://localhost:8080/creditcard/showStatusInquiry.do

### Step 3: Test Web Service

**WSDL URL**: http://localhost:8080/creditcard/services/CreditCardService?wsdl

**Test with SoapUI or Postman:**

```xml
POST http://localhost:8080/creditcard/services/CreditCardService
Content-Type: text/xml

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:cred="http://service.creditcard.firstbank.com">
   <soapenv:Header/>
   <soapenv:Body>
      <cred:getAvailableCardTypes/>
   </soapenv:Body>
</soapenv:Envelope>
```

### Step 4: Test Application Flow

1. **Apply for Credit Card**:
   - Go to http://localhost:8080/creditcard/showPersonalInfo.do
   - Fill in personal information
   - Click "Continue"
   - Fill in financial information
   - Submit application
   - Note the application ID

2. **Check Application Status**:
   - Go to http://localhost:8080/creditcard/showStatusInquiry.do
   - Enter application ID
   - View status

---

## Troubleshooting

### Issue 1: Port 8080 Already in Use

**Solution:**
```cmd
# Windows - Find process using port 8080
netstat -ano | findstr :8080
taskkill /PID <process_id> /F

# Linux/Mac
lsof -i :8080
kill -9 <process_id>

# Or change Tomcat port in server.xml
```

### Issue 2: Database Connection Failed

**Check:**
1. Database is running
2. JNDI resource name matches: `jdbc/FirstBankCCDS`
3. Connection URL is correct
4. Username/password are correct
5. JDBC driver is in Tomcat lib folder

**Test connection:**
```sql
-- Try connecting manually
sqlplus ccapp_user/ccapp_password@localhost:1521/XE
```

### Issue 3: ClassNotFoundException

**Solution:**
- Ensure all JARs are in `WEB-INF/lib` or Tomcat's `lib` folder
- Rebuild with: `mvn clean package`

### Issue 4: 404 Error

**Check:**
1. WAR file deployed correctly in `webapps` folder
2. Tomcat extracted the WAR (should see `creditcard` folder)
3. Context path is correct: `/creditcard`
4. Check Tomcat logs for deployment errors

### Issue 5: EJB Lookup Failed

**Solution:**
- This application uses EJB 2.0 which requires full Java EE server
- For Tomcat (servlet container only), EJB features won't work
- Use JBoss/WildFly for full EJB support
- Or modify code to remove EJB dependencies

### Issue 6: Maven Build Fails

**Solution:**
```cmd
# Clear Maven cache
mvn dependency:purge-local-repository

# Rebuild
mvn clean install -U
```

---

## Quick Reference Commands

### Start/Stop Tomcat

```cmd
# Windows
%CATALINA_HOME%\bin\startup.bat
%CATALINA_HOME%\bin\shutdown.bat

# Linux/Mac
$CATALINA_HOME/bin/startup.sh
$CATALINA_HOME/bin/shutdown.sh
```

### View Logs

```cmd
# Windows
type "%CATALINA_HOME%\logs\catalina.out"
type "%CATALINA_HOME%\logs\localhost.*.log"

# Linux/Mac
tail -f $CATALINA_HOME/logs/catalina.out
tail -f $CATALINA_HOME/logs/localhost.*.log
```

### Rebuild Application

```cmd
cd C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app
mvn clean package
copy target\creditcard.war "%CATALINA_HOME%\webapps\"
```

---

## Production Deployment Checklist

- [ ] Use production database (not H2)
- [ ] Change default passwords
- [ ] Enable HTTPS/SSL
- [ ] Configure firewall rules
- [ ] Set up database backups
- [ ] Configure logging
- [ ] Remove debug settings
- [ ] Disable Tomcat manager in production
- [ ] Set up monitoring
- [ ] Configure load balancing (if needed)
- [ ] Test disaster recovery procedures

---

## Support and Resources

- **Tomcat Documentation**: https://tomcat.apache.org/tomcat-9.0-doc/
- **Maven Documentation**: https://maven.apache.org/guides/
- **Oracle Documentation**: https://docs.oracle.com/en/database/
- **Project README**: See README.md in project root
- **Database Setup**: See database-setup.md

---

## Summary

You now have everything needed to:
1. ✅ Install all required software
2. ✅ Set up the database
3. ✅ Configure the application server
4. ✅ Build the application
5. ✅ Deploy and test

**Next Step**: Follow the installation steps in order, starting with JDK installation.

Good luck! 🚀