# FirstBank Credit Card Application

A legacy J2EE enterprise application for processing credit card applications online. Built with EJB 2.0, Apache Struts 1.1, and H2 database, running on JBoss WildFly 26.1.3 with Java 21.

## 🏗️ Architecture

- **Presentation Layer**: JSP pages with Apache Struts 1.1 framework
- **Business Logic**: Stateless Session EJBs (EJB 2.0)
- **Data Access**: JDBC with stored procedures
- **Web Services**: Apache AXIS SOAP services
- **Database**: H2 Database (Oracle-compatible mode)
- **Application Server**: JBoss WildFly 26.1.3.Final
- **Java Version**: Java 21

## 📋 Prerequisites

### Required Software
- **JDK 21** (Java Development Kit)
- **Apache Maven 3.6+** (Build tool)
- **JBoss WildFly 26.1.3.Final** (Application Server)
- **H2 Database** (Included with WildFly)

### Installation Paths (Update as needed)
- WildFly: `C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final`
- Project: `C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app`

## 📥 Software Download and Installation

### Step 1: Install Java JDK 21

#### Windows:
1. **Download JDK 21**:
   - Visit: https://www.oracle.com/java/technologies/downloads/#java21
   - Or use OpenJDK: https://adoptium.net/temurin/releases/?version=21
   - Download the Windows x64 Installer (`.msi` file)

2. **Install JDK**:
   - Run the downloaded installer
   - Follow the installation wizard
   - Default installation path: `C:\Program Files\Java\jdk-21`

3. **Set Environment Variables**:
   ```cmd
   # Open Command Prompt as Administrator
   setx JAVA_HOME "C:\Program Files\Java\jdk-21" /M
   setx PATH "%PATH%;%JAVA_HOME%\bin" /M
   ```

4. **Verify Installation**:
   ```cmd
   java -version
   # Should show: java version "21.x.x"
   ```

#### Linux/Mac:
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-21-jdk

# Mac (using Homebrew)
brew install openjdk@21

# Verify
java -version
```

### Step 2: Install Apache Maven

#### Windows:
1. **Download Maven**:
   - Visit: https://maven.apache.org/download.cgi
   - Download `apache-maven-3.9.x-bin.zip` (Binary zip archive)

2. **Extract Maven**:
   - Extract to: `C:\Program Files\Apache\maven`

3. **Set Environment Variables**:
   ```cmd
   # Open Command Prompt as Administrator
   setx MAVEN_HOME "C:\Program Files\Apache\maven" /M
   setx PATH "%PATH%;%MAVEN_HOME%\bin" /M
   ```

4. **Verify Installation**:
   ```cmd
   mvn -version
   # Should show: Apache Maven 3.9.x
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

### Step 3: Download and Install JBoss WildFly 26.1.3.Final

#### Windows:

1. **Download WildFly**:
   - Visit: https://www.wildfly.org/downloads/
   - Click on "WildFly 26.1.3.Final"
   - Download: `wildfly-26.1.3.Final.zip`
   - Direct link: https://github.com/wildfly/wildfly/releases/download/26.1.3.Final/wildfly-26.1.3.Final.zip

2. **Extract WildFly**:
   - Extract the ZIP file to: `C:\Users\VaibhavPagar\Documents\Jboss\`
   - Final path should be: `C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final`

3. **Set Environment Variable** (Optional):
   ```cmd
   setx JBOSS_HOME "C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final" /M
   ```

4. **Verify Installation**:
   ```cmd
   cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
   standalone.bat --version
   # Should show: WildFly Full 26.1.3.Final
   ```

#### Linux/Mac:

```bash
# Download WildFly
cd ~/Downloads
wget https://github.com/wildfly/wildfly/releases/download/26.1.3.Final/wildfly-26.1.3.Final.tar.gz

# Extract
tar -xzf wildfly-26.1.3.Final.tar.gz

# Move to installation directory
sudo mv wildfly-26.1.3.Final /opt/wildfly

# Set environment variable
echo 'export JBOSS_HOME=/opt/wildfly' >> ~/.bashrc
source ~/.bashrc

# Verify
cd /opt/wildfly/bin
./standalone.sh --version
```

### Step 4: H2 Database Setup

**Good News**: H2 Database is already included with WildFly 26.1.3.Final!

**Location**: `C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base\com\h2database\h2\main\`

**No separate download needed!** The H2 JAR file is already in the WildFly modules directory.

#### Optional: Download Standalone H2 (for H2 Console GUI)

If you want the H2 Console for database management:

1. **Download H2**:
   - Visit: http://www.h2database.com/html/download.html
   - Download: "Platform-Independent Zip"
   - Latest version: http://www.h2database.com/h2-latest.zip

2. **Extract H2**:
   - Extract to any location, e.g., `C:\h2`

3. **Start H2 Console** (Optional):
   ```cmd
   cd C:\h2\bin
   h2.bat
   # Opens browser at http://localhost:8082
   ```

### Step 5: Verify All Installations

Run these commands to verify everything is installed:

```cmd
# Check Java
java -version

# Check Maven
mvn -version

# Check WildFly
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
standalone.bat --version

# Check H2 (included in WildFly)
dir C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base\com\h2database\h2\main\h2-*.jar
```

All commands should execute without errors and show version information.

## 🚀 Quick Start Guide

### Step 1: Configure WildFly DataSource

Edit `standalone.xml` in WildFly configuration directory:
```
C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\configuration\standalone.xml
```

**IMPORTANT**: Your current standalone.xml has the FirstBankCCDS datasource configured but is **MISSING the password element**.

Find the `<datasources>` section (search for "FirstBankCCDS") and you'll see:

```xml
<datasource jndi-name="java:/jdbc/FirstBankCCDS" pool-name="FirstBankCCDS" enabled="true">
    <connection-url>jdbc:h2:tcp://localhost:9092/C:/Users/VaibhavPagar/creditcard;MODE=Oracle</connection-url>
    <driver>h2</driver>
    <security>
        <user-name>sa</user-name>
    </security>
</datasource>
```

**Save the file** after making this change. Without the password element, the datasource connection will fail.

### Step 2: Start H2 Database Server

Open **Command Prompt #1** (Run as Administrator):

**Option 1 - From H2 installation directory (Recommended if you have standalone H2)**:
```cmd
cd "C:\Program Files (x86)\H2\bin"
java -cp h2-2.4.240.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 9092 -web -webAllowOthers -webPort 8082 -ifNotExists
```

**Option 2 - From H2 module directory (Using WildFly's H2)**:
```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base\com\h2database\h2\main

java -cp h2-1.4.197.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 9092
```

**Option 3 - From WildFly bin directory**:
```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin

java -cp ..\modules\system\layers\base\com\h2database\h2\main\h2-1.4.197.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 9092
```

**Option 4 - Using wildcard (works with any H2 version)**:
```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin

java -cp ..\modules\system\layers\base\com\h2database\h2\main\h2-*.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 9092
```

You should see:
```
TCP server running at tcp://localhost:9092 (others can connect)
```

**KEEP THIS WINDOW OPEN!** The H2 database server must remain running.

**Note**:
- The H2 version in WildFly 26.1.3.Final is `h2-1.4.197.jar`
- H2 version 1.4.197 does NOT support `-ifNotExists` flag (that's for newer versions)
- The database will be created automatically when the application first connects to it

**KEEP THIS WINDOW OPEN!**

### Step 3: Initialize Database (First Time Only)

Open H2 Console (optional):
```cmd
java -cp C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base\com\h2database\h2\main\h2-*.jar org.h2.tools.Console
```

Connect with:
- **JDBC URL**: `jdbc:h2:tcp://localhost:9092/C:/Users/VaibhavPagar/creditcard;MODE=Oracle`
- **User**: `sa`

Run SQL scripts:
1. Execute `sql/schema.sql`
2. Execute `sql/procedures_h2.sql`

### Step 4: Build the Application

Open **Command Prompt #2**:

```cmd
cd C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app

build.bat
```

Wait for:
```
[INFO] BUILD SUCCESS
[INFO] Building war: ...\target\creditcard.war
```

### Step 5: Deploy to WildFly

In the same Command Prompt #2:

```cmd
copy target\creditcard.war C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\
```

### Step 6: Start WildFly Server

Open **Command Prompt #3**:

```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin

standalone.bat
```

Wait for successful deployment messages:
```
✅ INFO  [org.jboss.as.connector.subsystems.datasources] WFLYJCA0001: Bound data source [java:/jdbc/FirstBankCCDS]
✅ INFO  [org.wildfly.extension.undertow] WFLYUT0021: Registered web context: '/creditcard'
✅ INFO  [org.jboss.as.server] WFLYSRV0010: Deployed "creditcard.war"
✅ INFO  [org.jboss.as] WFLYSRV0025: WildFly Full 26.1.3.Final started
```

**KEEP THIS WINDOW OPEN!**

### Step 7: Access the Application

Open your browser and navigate to:

- **Home Page**: http://localhost:8081/creditcard/
- **Apply for Card**: http://localhost:8081/creditcard/showPersonalInfo.do
- **Check Status**: http://localhost:8081/creditcard/showStatusInquiry.do
- **WSDL**: http://localhost:8081/creditcard/services/CreditCardService?wsdl

## 📁 Project Structure

```
credit_card_app/
├── sql/                         # Database scripts
│   ├── schema.sql              # Table definitions and seed data
│   ├── procedures_h2.sql       # H2-compatible stored procedures
│   └── procedures.sql          # Original Oracle procedures
├── src/                         # Java source code
│   └── com/firstbank/creditcard/
│       ├── action/             # Struts Action classes
│       ├── dao/                # Data Access Objects
│       ├── ejb/                # Enterprise JavaBeans
│       ├── form/               # Struts Form Beans
│       ├── service/            # Web Service implementations
│       └── vo/                 # Value Objects (DTOs)
├── WebContent/                  # Web application root
│   ├── WEB-INF/
│   │   ├── web.xml             # Web app deployment descriptor
│   │   ├── struts-config.xml   # Struts configuration
│   │   ├── ejb-jar.xml         # EJB deployment descriptor
│   │   ├── jboss-deployment-structure.xml  # JBoss config
│   │   └── server-config.wsdd  # AXIS web service config
│   ├── css/                    # Stylesheets
│   ├── pages/                  # JSP pages
│   └── index.jsp               # Welcome page
├── wsdl/                        # Web Service definitions
├── pom.xml                      # Maven build configuration
├── build.bat                    # Windows build script
├── build.sh                     # Linux/Mac build script
├── REBUILD_AND_DEPLOY.bat      # Quick rebuild and deploy script
└── README.md                    # This file
```

## 🔧 Configuration Details

### H2 Database Configuration

**Database Location**: `C:\Users\VaibhavPagar\creditcard.mv.db`

**Connection Settings**:
- JDBC URL: `jdbc:h2:tcp://localhost:9092/C:/Users/VaibhavPagar/creditcard;MODE=Oracle`
- Driver: `org.h2.Driver`
- Username: `sa`
- Password: `sa`
- Mode: Oracle (for compatibility)

### JNDI DataSource

**JNDI Name**: `java:/jdbc/FirstBankCCDS`

This is configured in WildFly's `standalone.xml` and referenced by the application.

### Application Server Ports

- **HTTP**: 8081 (default WildFly port)
- **H2 TCP**: 9092 (database server)
- **Management**: 9990 (WildFly admin console)

## 📊 Database Schema

### Key Tables

- **CC_CUSTOMER**: Customer information
- **CC_APPLICATION**: Credit card applications
- **CC_APPLICANT_FINANCIAL**: Financial details
- **CC_APPLICANT_EMPLOYMENT**: Employment information
- **CC_CARD_TYPE**: Available card products
- **CC_STATUS_CODE**: Application status codes
- **CC_CREDIT_CARD**: Issued credit cards
- **CC_APPLICATION_AUDIT**: Audit trail
- **CC_REJECTION_REASON**: Rejection reasons

### Stored Procedures (H2)

- `CREATE_CUSTOMER`: Create new customer record
- `CREATE_APPLICATION`: Create credit card application
- `UPDATE_APPLICATION_STATUS`: Update application status
- `GET_APPLICATION_STATUS`: Retrieve application status

## 🧪 Testing

### Manual Testing

1. **Apply for Credit Card**:
   - Navigate to http://localhost:8081/creditcard/showPersonalInfo.do
   - Fill in personal information (First Name, Last Name, SSN, etc.)
   - Click "Continue"
   - Fill in financial information (Annual Income, Employment Status)
   - Submit application
   - Note the application ID from confirmation page

2. **Check Application Status**:
   - Navigate to http://localhost:8081/creditcard/showStatusInquiry.do
   - Enter the application ID
   - View current status

### Web Service Testing

Use SoapUI or Postman to test SOAP endpoints.

**Endpoint**: http://localhost:8081/creditcard/services/CreditCardService

**Sample SOAP Request - Get Available Card Types**:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:cred="http://service.creditcard.firstbank.com">
   <soapenv:Header/>
   <soapenv:Body>
      <cred:getAvailableCardTypes/>
   </soapenv:Body>
</soapenv:Envelope>
```

**Sample SOAP Request - Get Application Status**:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:cred="http://service.creditcard.firstbank.com">
   <soapenv:Header/>
   <soapenv:Body>
      <cred:getApplicationStatus>
         <applicationId>200001</applicationId>
      </cred:getApplicationStatus>
   </soapenv:Body>
</soapenv:Envelope>
```

## 🐛 Troubleshooting

### Issue: Port 9092 already in use

**Solution**:
```cmd
netstat -ano | findstr :9092
taskkill /PID <process_id> /F
```
Then restart H2 server.

### Issue: Port 8081 already in use

**Solution**:
```cmd
netstat -ano | findstr :8081
taskkill /PID <process_id> /F
```
Then restart WildFly.

### Issue: Database connection failed

**Check**:
1. H2 server is running (Command Prompt #1)
2. Connection URL is correct in `standalone.xml`
3. Security element uses attribute format: `<security user-name="sa" password="sa"/>`

### Issue: ClassNotFoundException

**Solution**:
- Ensure all JARs are in `WEB-INF/lib`
- Rebuild with: `mvn clean package`

### Issue: 404 Error

**Check**:
1. WAR file deployed correctly in deployments folder
2. WildFly extracted the WAR (should see `creditcard` folder)
3. Context path is correct: `/creditcard`
4. Check WildFly logs for deployment errors

### Issue: BUILD FAILURE

**Solution**:
```cmd
mvn clean
mvn dependency:purge-local-repository
mvn package
```

### Issue: Application not deploying

**Solution**:
1. Stop WildFly (Ctrl+C)
2. Delete old deployment:
   ```cmd
   del C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\creditcard.war*
   ```
3. Copy WAR again
4. Start WildFly

### Issue: Database file locked

**Solution**:
```cmd
# Stop WildFly and H2
# Delete H2 database files
del %USERPROFILE%\creditcard.mv.db
del %USERPROFILE%\creditcard.trace.db
# Restart H2 and WildFly
```

## 🔄 Restart Procedure

To restart everything:

1. **Stop WildFly**: Press `Ctrl+C` in Command Prompt #3
2. **Stop H2**: Press `Ctrl+C` in Command Prompt #1
3. **Start H2 again**: Repeat Step 2 from Quick Start
4. **Start WildFly again**: Repeat Step 6 from Quick Start

## 📝 Development Workflow

### Making Code Changes

1. Edit source files in `src/` directory
2. Rebuild: `build.bat`
3. Deploy: `copy target\creditcard.war <wildfly>\standalone\deployments\`
4. WildFly will auto-redeploy

### Quick Rebuild and Deploy Script

Use the provided `REBUILD_AND_DEPLOY.bat`:

```batch
@echo off
echo Rebuilding and deploying...
call build.bat
if errorlevel 1 (
    echo Build failed!
    pause
    exit /b 1
)
copy /Y target\creditcard.war C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\
echo Deployment complete!
pause
```

### Viewing Logs

**WildFly Logs**:
```cmd
type C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\log\server.log
```

**Real-time Log Monitoring** (PowerShell):
```powershell
Get-Content C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\log\server.log -Wait -Tail 50
```

## 💡 Startup Script

Save this as `start-all.bat` in your project folder for easy startup:

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

## ✅ Pre-Deployment Checklist

Before running the application, verify:

- [ ] Java 21 JDK installed and in PATH
- [ ] Maven installed and in PATH
- [ ] WildFly 26.1.3.Final installed
- [ ] `standalone.xml` configured with FirstBankCCDS datasource
- [ ] Security element uses attribute format (not nested)
- [ ] H2 database tables created (first time only)
- [ ] Application built successfully (BUILD SUCCESS)
- [ ] WAR file exists in `target/` directory

## 🔐 Security Notes

⚠️ **Important**: This is a legacy application. For production use:

1. Change default database credentials
2. Implement proper authentication/authorization
3. Use HTTPS/TLS for all communications
4. Encrypt sensitive data (SSN, card numbers)
5. Implement CSRF protection
6. Update all dependencies to latest secure versions
7. Add input validation and sanitization
8. Implement rate limiting
9. Add comprehensive logging and monitoring
10. Follow OWASP security guidelines

## 📚 Technology Stack

- **Java**: 21
- **Application Server**: JBoss WildFly 26.1.3.Final
- **Web Framework**: Apache Struts 1.1
- **EJB**: 2.0 (Stateless Session Beans)
- **Database**: H2 Database (Oracle mode)
- **Web Services**: Apache AXIS 1.4
- **Build Tool**: Apache Maven 3.x
- **JSP/Servlet**: Java EE 7

## 📄 License

Copyright © 2001 FirstBank IT Department. All rights reserved.

This is a demonstration/educational project based on legacy J2EE architecture.

## 👥 Support

For issues or questions:
1. Check the troubleshooting section above
2. Review WildFly server logs
3. Verify H2 database is running
4. Check configuration files

---

**Note**: This application uses legacy technologies (J2EE 1.3, EJB 2.0, Struts 1.1) that are no longer recommended for new projects. It has been successfully updated to run on modern infrastructure (Java 21, WildFly 26, H2 Database) for educational and demonstration purposes.

**Last Updated**: 2026-05-21