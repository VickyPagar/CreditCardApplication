# FirstBank Credit Card Application System

A legacy J2EE 1.3 enterprise application for processing credit card applications online. Built with EJB 2.0, Apache Struts 1.1, and Oracle database.

## 🏗️ Architecture

- **Presentation Layer**: JSP pages with Apache Struts 1.1 framework
- **Business Logic**: Stateless Session EJBs (EJB 2.0)
- **Data Access**: JDBC with Oracle stored procedures
- **Web Services**: Apache AXIS SOAP services
- **Database**: Oracle 9i+ (or compatible)

## 📋 Prerequisites

### Required Software
- **JDK 8 or higher** (Java Development Kit)
- **Apache Maven 3.6+** (Build tool)
- **H2 Database* or compatible (PostgreSQL, H2 for testing)
- **Application Server**: One of the following:
  - Apache Tomcat 9+ (for WAR deployment)
  - JBoss/WildFly (for full EAR deployment)
  - WebLogic Server
  - GlassFish

### Optional Tools
- Git (for version control)
- Oracle SQL Developer or DBeaver (database management)
- Postman or SoapUI (for testing web services)

## 🚀 Quick Start

### 1. Clone/Download the Project
```bash
cd c:/Users/VaibhavPagar/Documents/workspacefinaljboss/credit_card_app
```

### 2. Set Up Database

####  H2 Database (Quick Testing)
```bash
# Download H2 database from http://www.h2database.com/
# Start H2 console
java -jar h2*.jar

# Use connection string: jdbc:h2:~/creditcard;MODE=Oracle
# Run modified SQL scripts (adjust for H2 syntax)
```

See [database-setup.md](database-setup.md) for detailed instructions.

### 3. Configure Database Connection

Edit `WebContent/WEB-INF/struts-config.xml` (lines 20-26):
```xml
<set-property property="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
<set-property property="username" value="ccapp_user"/>
<set-property property="password" value="ccapp_password"/>
```

### 4. Build the Application

#### Windows:
```cmd
build.bat
```

#### Linux/Mac:
```bash
chmod +x build.sh
./build.sh
```

#### Manual Maven Build:
```bash
mvn clean package
```

### 5. Deploy to Application Server

#### JBoss/WildFly Deployment:
```bash
# Copy EAR file
copy target\creditcard-app-1.0.0.ear %JBOSS_HOME%\standalone\deployments\

# Start JBoss
%JBOSS_HOME%\bin\standalone.bat    # Windows
$JBOSS_HOME/bin/standalone.sh      # Linux/Mac
```

### 6. Access the Application

Open your browser and navigate to:
```
http://localhost:8080/creditcard/
```

Default pages:
- **Home**: http://localhost:8080/creditcard/index.jsp
- **Apply**: http://localhost:8080/creditcard/showPersonalInfo.do
- **Check Status**: http://localhost:8080/creditcard/showStatusInquiry.do
- **WSDL**: http://localhost:8080/creditcard/services/CreditCardService?wsdl

## 📁 Project Structure

```
credit_card_app/
├── META-INF/                    # EAR deployment descriptors
│   ├── application.xml          # J2EE application config
│   ├── ejb-jar.xml             # EJB deployment descriptor
│   └── weblogic-ejb-jar.xml    # WebLogic-specific config
├── sql/                         # Database scripts
│   ├── schema.sql              # Table definitions and seed data
│   └── procedures.sql          # PL/SQL stored procedures
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
│   │   └── server-config.wsdd  # AXIS web service config
│   ├── css/                    # Stylesheets
│   ├── pages/                  # JSP pages
│   └── index.jsp               # Welcome page
├── wsdl/                        # Web Service definitions
├── pom.xml                      # Maven build configuration
├── build.bat / build.sh         # Build scripts
├── database-setup.md            # Database setup guide
└── README.md                    # This file
```

## 🔧 Configuration

### Application Server Configuration

#### Configure JNDI DataSource

**For JBoss/WildFly** - Add to `standalone.xml`:
```xml
<datasource jndi-name="java:/jdbc/FirstBankCCDS" pool-name="FirstBankCCDS">
    <connection-url>jdbc:oracle:thin:@localhost:1521:orcl</connection-url>
    <driver>oracle</driver>
    <security>
        <user-name>ccapp_user</user-name>
        <password>ccapp_password</password>
    </security>
</datasource>
```

#### Configure EJB JNDI

Add EJB reference mapping in your application server:
- JNDI Name: `ejb/CreditCardApplicationBean`
- EJB Class: `com.firstbank.creditcard.ejb.session.CreditCardApplicationBean`

### Environment Variables

Set these environment variables (optional):
```bash
# Database connection
export DB_URL=jdbc:oracle:thin:@localhost:1521:orcl
export DB_USER=ccapp_user
export DB_PASSWORD=ccapp_password

# Application server
export CATALINA_HOME=/path/to/tomcat
export JBOSS_HOME=/path/to/jboss
```

## 🧪 Testing

### Manual Testing

1. **Apply for Credit Card**:
   - Navigate to http://localhost:8080/creditcard/showPersonalInfo.do
   - Fill in personal information
   - Complete financial information
   - Submit application

2. **Check Application Status**:
   - Navigate to http://localhost:8080/creditcard/showStatusInquiry.do
   - Enter application ID
   - View status

### Web Service Testing

Use SoapUI or Postman to test SOAP endpoints:

**Endpoint**: http://localhost:8080/creditcard/services/CreditCardService

**Sample SOAP Request**:
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

## 📊 Database Schema

Key tables:
- **CC_CUSTOMER**: Customer information
- **CC_APPLICATION**: Credit card applications
- **CC_APPLICANT_FINANCIAL**: Financial details
- **CC_APPLICANT_EMPLOYMENT**: Employment information
- **CC_CARD_TYPE**: Available card products
- **CC_STATUS_CODE**: Application status codes
- **CC_CREDIT_CARD**: Issued credit cards
- **CC_APPLICATION_AUDIT**: Audit trail

## 🔐 Security Notes

⚠️ **Important**: This is a legacy application from 2001. For production use:

1. Update to modern frameworks (Spring Boot, JPA)
2. Implement proper authentication/authorization
3. Use HTTPS/TLS for all communications
4. Encrypt sensitive data (SSN, card numbers)
5. Implement CSRF protection
6. Update all dependencies to latest secure versions
7. Add input validation and sanitization
8. Implement rate limiting
9. Add comprehensive logging and monitoring
10. Follow OWASP security guidelines

## 🐛 Troubleshooting

### Build Issues

**Problem**: Maven dependencies not downloading
```bash
# Solution: Clear Maven cache and rebuild
mvn dependency:purge-local-repository
mvn clean install
```

**Problem**: Compilation errors
```bash
# Solution: Ensure JDK 8+ is installed
java -version
mvn -version
```

### Deployment Issues

**Problem**: ClassNotFoundException
- Ensure all required JARs are in WEB-INF/lib
- Check application server classpath

**Problem**: JNDI lookup fails
- Verify datasource is configured in application server
- Check JNDI name matches configuration

**Problem**: Database connection fails
- Verify Oracle listener is running: `lsnrctl status`
- Test connection with SQL client
- Check firewall settings

### Runtime Issues

**Problem**: 404 errors
- Verify context path is `/creditcard`
- Check WAR deployment was successful
- Review application server logs

**Problem**: EJB not found
- Verify EJB is deployed
- Check JNDI bindings in application server
- Review ejb-jar.xml configuration

## 📝 Development Notes

### Adding New Features

1. **New JSP Page**: Add to `WebContent/pages/`
2. **New Action**: Create in `src/com/firstbank/creditcard/action/`
3. **Configure Struts**: Update `WebContent/WEB-INF/struts-config.xml`
4. **Database Changes**: Create migration scripts in `sql/`

### Code Style

This project follows early 2000s J2EE conventions:
- EJB 2.0 patterns (Home/Remote interfaces)
- Struts 1.x MVC pattern
- DAO pattern for data access
- Value Objects for data transfer

## 📚 Additional Resources

- [Apache Struts 1.x Documentation](https://struts.apache.org/struts1eol-announcement.html)
- [EJB 2.0 Specification](https://www.oracle.com/java/technologies/ejb-2_0-spec.html)
- [Oracle PL/SQL Documentation](https://docs.oracle.com/en/database/oracle/oracle-database/)
- [Apache AXIS Documentation](https://axis.apache.org/axis/)

## 📄 License

Copyright © 2001 FirstBank IT Department. All rights reserved.

This is a demonstration/educational project based on legacy J2EE architecture.

## 👥 Support

For issues or questions:
1. Check the troubleshooting section above
2. Review application server logs
3. Check database connectivity
4. Verify configuration files

---

**Note**: This application uses legacy technologies (J2EE 1.3, EJB 2.0, Struts 1.1) that are no longer recommended for new projects. Consider modernizing to Spring Boot, JPA, and REST APIs for production use.
