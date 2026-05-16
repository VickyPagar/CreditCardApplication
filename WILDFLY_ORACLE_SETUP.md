# WildFly Oracle JDBC Driver Setup Guide

## Overview
This guide explains how to install and configure the Oracle JDBC driver in WildFly 26 for the Credit Card Application.

## Current Issues Fixed
1. ✅ **struts-config.xml validation error** - Fixed element ordering (global-exceptions must come before global-forwards)
2. ✅ **jboss-deployment-structure.xml** - Created to reference Oracle JDBC module
3. ⏳ **Oracle JDBC driver** - Needs to be installed in WildFly

## Step-by-Step Oracle JDBC Driver Installation

### Step 1: Download Oracle JDBC Driver

1. Download `ojdbc8.jar` (or `ojdbc11.jar` for Java 11+) from:
   - https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html
   - Or use Maven Central: https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc8

2. Save the file to a temporary location (e.g., `C:\temp\ojdbc8.jar`)

### Step 2: Create Module Directory Structure

Navigate to your WildFly installation and create the module directory:

```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base

mkdir com\oracle\ojdbc\main
```

### Step 3: Copy JDBC Driver

Copy the Oracle JDBC driver to the module directory:

```cmd
copy C:\temp\ojdbc8.jar C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base\com\oracle\ojdbc\main\
```

### Step 4: Create module.xml

Create a file named `module.xml` in the same directory:

**Path**: `C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\modules\system\layers\base\com\oracle\ojdbc\main\module.xml`

**Content**:
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

### Step 5: Register JDBC Driver in WildFly

Edit the WildFly configuration file:

**Path**: `C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\configuration\standalone.xml`

Find the `<drivers>` section under `<datasources>` and add:

```xml
<drivers>
    <driver name="h2" module="com.h2database.h2">
        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
    </driver>
    <!-- ADD THIS ORACLE DRIVER -->
    <driver name="oracle" module="com.oracle.ojdbc">
        <driver-class>oracle.jdbc.driver.OracleDriver</driver-class>
        <xa-datasource-class>oracle.jdbc.xa.client.OracleXADataSource</xa-datasource-class>
    </driver>
</drivers>
```

### Step 6: Configure DataSource

In the same `standalone.xml` file, find or add the datasource configuration:

```xml
<datasource jndi-name="java:/jdbc/FirstBankCCDS" 
            pool-name="FirstBankCCDS" 
            enabled="true" 
            use-java-context="true">
    <connection-url>jdbc:oracle:thin:@localhost:1521:XE</connection-url>
    <driver>oracle</driver>
    <security user-name="ccapp_user" password="ccapp_password"/>
    <validation>
        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.oracle.OracleValidConnectionChecker"/>
        <stale-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.oracle.OracleStaleConnectionChecker"/>
        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.oracle.OracleExceptionSorter"/>
    </validation>
</datasource>
```

**IMPORTANT**: Make sure the `<security>` element uses the attribute format:
```xml
<security user-name="ccapp_user" password="ccapp_password"/>
```

NOT the nested format:
```xml
<security>
    <user-name>ccapp_user</user-name>
    <password>ccapp_password</password>
</security>
```

### Step 7: Rebuild Application

```cmd
cd C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app
build.bat
```

### Step 8: Deploy to WildFly

```cmd
copy target\creditcard.war C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\standalone\deployments\
```

### Step 9: Restart WildFly

```cmd
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final\bin
standalone.bat
```

## Alternative: Using H2 Database (For Testing)

If you don't have Oracle installed, you can use H2 database which is already included in WildFly:

### Update standalone.xml datasource:

```xml
<datasource jndi-name="java:/jdbc/FirstBankCCDS" 
            pool-name="FirstBankCCDS" 
            enabled="true" 
            use-java-context="true">
    <connection-url>jdbc:h2:~/creditcard;MODE=Oracle</connection-url>
    <driver>h2</driver>
    <security user-name="sa" password=""/>
</datasource>
```

### Remove Oracle dependency from jboss-deployment-structure.xml:

Comment out or remove the Oracle module dependency:
```xml
<!-- <module name="com.oracle.ojdbc" export="true"/> -->
```

## Verification Steps

1. **Check WildFly logs** for successful driver loading:
   ```
   INFO  [org.jboss.as.connector.deployers.jdbc] WFLYJCA0018: Started Driver service with driver-name = oracle
   ```

2. **Check datasource binding**:
   ```
   INFO  [org.jboss.as.connector.subsystems.datasources] WFLYJCA0001: Bound data source [java:/jdbc/FirstBankCCDS]
   ```

3. **Verify no ClassNotFoundException** for Oracle driver

4. **Test application** at: http://localhost:8081/creditcard/

## Troubleshooting

### Issue: Module not found
- Verify directory structure: `modules/system/layers/base/com/oracle/ojdbc/main/`
- Check `module.xml` is in the correct location
- Verify JAR filename matches in `module.xml`

### Issue: Driver not registered
- Check `standalone.xml` syntax
- Restart WildFly completely
- Check for typos in driver name

### Issue: Connection failed
- Verify Oracle database is running
- Check connection URL, username, password
- Test connection manually with SQL*Plus

### Issue: Still getting ClassNotFoundException
- Verify `jboss-deployment-structure.xml` is in `WEB-INF/` folder
- Rebuild application after adding the file
- Check module name matches: `com.oracle.ojdbc`

## Quick Commands Reference

```cmd
# Navigate to WildFly
cd C:\Users\VaibhavPagar\Documents\Jboss\wildfly-26.1.3.Final

# Start WildFly
bin\standalone.bat

# Stop WildFly (Ctrl+C or)
bin\jboss-cli.bat --connect command=:shutdown

# View logs
type standalone\log\server.log

# Deploy application
copy C:\Users\VaibhavPagar\Documents\workspacefinaljboss\credit_card_app\target\creditcard.war standalone\deployments\

# Undeploy (delete WAR and marker files)
del standalone\deployments\creditcard.war*
```

## Summary of Changes Made

1. ✅ Fixed `WebContent/WEB-INF/struts-config.xml` - Corrected element order
2. ✅ Created `WebContent/WEB-INF/jboss-deployment-structure.xml` - Added Oracle module dependency
3. ⏳ Need to install Oracle JDBC driver in WildFly (follow steps above)
4. ⏳ Need to configure datasource in `standalone.xml`
5. ⏳ Need to rebuild and redeploy application

## Next Steps

1. Download Oracle JDBC driver (ojdbc8.jar)
2. Follow Steps 2-6 above to install and configure
3. Rebuild application: `build.bat`
4. Deploy to WildFly
5. Test application

Good luck! 🚀