# Database Setup Instructions

## Prerequisites
- Oracle Database 11g or higher (or compatible database like PostgreSQL with modifications)
- Database user with CREATE privileges

## Setup Steps

### 1. Create Database User
```sql
-- Connect as SYSDBA
CREATE USER ccapp_user IDENTIFIED BY ccapp_password;
GRANT CONNECT, RESOURCE TO ccapp_user;
GRANT CREATE SESSION TO ccapp_user;
GRANT CREATE TABLE TO ccapp_user;
GRANT CREATE SEQUENCE TO ccapp_user;
GRANT CREATE PROCEDURE TO ccapp_user;
ALTER USER ccapp_user QUOTA UNLIMITED ON USERS;
```

### 2. Run Schema Creation
```bash
# Connect as ccapp_user
sqlplus ccapp_user/ccapp_password@localhost:1521/orcl

# Run the schema script
@sql/schema.sql

# Run the stored procedures
@sql/procedures.sql
```

### 3. Verify Installation
```sql
-- Check tables
SELECT table_name FROM user_tables;

-- Check sequences
SELECT sequence_name FROM user_sequences;

-- Check data
SELECT * FROM CC_CARD_TYPE;
SELECT * FROM CC_STATUS_CODE;
```

## Database Connection Configuration

### For WebLogic/JBoss
Configure JNDI datasource: `jdbc/FirstBankCCDS`

**Connection Properties:**
- Driver: `oracle.jdbc.driver.OracleDriver`
- URL: `jdbc:oracle:thin:@localhost:1521:orcl`
- Username: `ccapp_user`
- Password: `ccapp_password`
- Min Pool Size: 2
- Max Pool Size: 10

### Alternative: H2 Database (for testing)
For quick testing without Oracle, you can use H2 database:

1. Download H2 database
2. Modify connection string to: `jdbc:h2:~/creditcard;MODE=Oracle`
3. Adjust SQL scripts for H2 compatibility

## Troubleshooting

### Connection Issues
- Verify Oracle listener is running: `lsnrctl status`
- Check firewall allows port 1521
- Verify TNS configuration

### Permission Issues
- Ensure user has proper grants
- Check tablespace quotas

### Data Issues
- Verify seed data was inserted
- Check sequences are created and accessible