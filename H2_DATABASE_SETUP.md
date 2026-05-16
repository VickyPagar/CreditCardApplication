# H2 Database Setup for Credit Card Application

## Overview
Since you're using H2 database, the setup is much simpler - H2 is already included in WildFly!

## ✅ Already Fixed:
1. **struts-config.xml** - Element ordering corrected
2. **jboss-deployment-structure.xml** - Updated for H2 (no Oracle dependency)

## 🔧 Configuration Steps

### Step 1: Configure H2 DataSource in WildFly

Edit your WildFly configuration file:
**Path**: `C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\configuration\standalone.xml`

Find the `<datasources>` section and add/update the datasource:

```xml
<subsystem xmlns="urn:jboss:domain:datasources:6.0">
    <datasources>
        <!-- Existing ExampleDS -->
        <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
            <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
            <driver>h2</driver>
            <security user-name="sa" password="sa"/>
        </datasource>
        
        <!-- ADD THIS: FirstBank Credit Card DataSource -->
        <datasource jndi-name="java:/jdbc/FirstBankCCDS" 
                    pool-name="FirstBankCCDS" 
                    enabled="true" 
                    use-java-context="true">
            <connection-url>jdbc:h2:~/creditcard;MODE=Oracle;DB_CLOSE_DELAY=-1</connection-url>
            <driver>h2</driver>
            <security user-name="sa" password=""/>
            <validation>
                <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.novendor.NullValidConnectionChecker"/>
                <background-validation>false</background-validation>
            </validation>
        </datasource>
        
        <drivers>
            <driver name="h2" module="com.h2database.h2">
                <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
            </driver>
        </drivers>
    </datasources>
</subsystem>
```

**IMPORTANT**: Make sure the `<security>` element uses the attribute format:
```xml
<security user-name="sa" password=""/>
```

**NOT** the nested format (this causes errors):
```xml
<security>
    <user-name>sa</user-name>
    <password></password>
</security>
```

### Step 2: Initialize H2 Database

You have two options:

#### Option A: Let Application Create Tables (Automatic)
The application will create tables on first run if they don't exist.

#### Option B: Create Tables Manually (Recommended)

1. **Start H2 Console** (optional, for verification):
   ```cmd
   cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
   java -cp ..\modules\system\layers\base\com\h2database\h2\main\h2-*.jar org.h2.tools.Server -web
   ```
   
   This opens H2 Console at: http://localhost:8082

2. **Connect with these settings**:
   - JDBC URL: `jdbc:h2:~/creditcard;MODE=Oracle`
   - User: `sa`
   - Password: (leave empty)

3. **Run your SQL scripts**:
   - Execute `sql/schema.sql`
   - Execute `sql/procedures.sql`

### Step 3: Rebuild Application

```cmd
cd C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app
build.bat
```

### Step 4: Deploy to WildFly

```cmd
copy target\creditcard.war C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\
```

### Step 5: Start WildFly

```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
standalone.bat
```

### Step 6: Verify Deployment

Check the server log for:
```
INFO  [org.jboss.as.connector.subsystems.datasources] WFLYJCA0001: Bound data source [java:/jdbc/FirstBankCCDS]
INFO  [org.wildfly.extension.undertow] WFLYUT0021: Registered web context: '/creditcard'
INFO  [org.jboss.as.server] WFLYSRV0010: Deployed "creditcard.war"
```

### Step 7: Test Application

Open browser:
- **Home**: http://localhost:8081/creditcard/
- **Apply**: http://localhost:8081/creditcard/showPersonalInfo.do
- **Status**: http://localhost:8081/creditcard/showStatusInquiry.do

## 🔍 Troubleshooting

### Issue: DataSource not found

**Check standalone.xml**:
- JNDI name must be: `java:/jdbc/FirstBankCCDS`
- Security element uses attribute format (not nested)

### Issue: Database file locked

**Solution**:
```cmd
# Stop WildFly
# Delete H2 database files
del %USERPROFILE%\creditcard.mv.db
del %USERPROFILE%\creditcard.trace.db
# Restart WildFly
```

### Issue: Tables not found

**Solution**:
Run the SQL scripts manually using H2 Console or create them via application.

### Issue: Port 8081 already in use

**Check what's using the port**:
```cmd
netstat -ano | findstr :8081
```

**Change WildFly port** in `standalone.xml`:
```xml
<socket-binding name="http" port="${jboss.http.port:8080}"/>
```

## 📊 H2 Database Location

Your H2 database file will be created at:
```
C:\Users\VaibhavPagar\creditcard.mv.db
```

## 🎯 Quick Commands

### Rebuild and Deploy:
```cmd
cd C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app
build.bat
copy target\creditcard.war C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\
```

### View Logs:
```cmd
type C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\log\server.log
```

### Stop WildFly:
Press `Ctrl+C` in the WildFly console window

### Clean Deployment:
```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments
del creditcard.war*
```

## ✅ Verification Checklist

- [ ] standalone.xml has FirstBankCCDS datasource configured
- [ ] Security element uses attribute format (not nested)
- [ ] Application rebuilt with `build.bat`
- [ ] WAR file copied to deployments folder
- [ ] WildFly started successfully
- [ ] No errors in server.log
- [ ] DataSource bound: `java:/jdbc/FirstBankCCDS`
- [ ] Application deployed: `/creditcard`
- [ ] Can access: http://localhost:8081/creditcard/

## 🚀 All-in-One PowerShell Script

Copy and paste this into PowerShell to rebuild and deploy:

```powershell
# Navigate to project
cd C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app

# Rebuild
.\build.bat

# Wait for build to complete
Start-Sleep -Seconds 2

# Copy to WildFly
Copy-Item "target\creditcard.war" -Destination "C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\" -Force

Write-Host "Deployment complete! Check WildFly console for deployment status."
Write-Host "Access application at: http://localhost:8081/creditcard/"
```

## 📝 Summary

With H2 database:
1. ✅ No JDBC driver download needed (H2 included in WildFly)
2. ✅ Simple configuration in standalone.xml
3. ✅ Database file stored in user home directory
4. ✅ Perfect for development and testing

**Next Step**: Configure the datasource in standalone.xml and rebuild!

Good luck! 🎉