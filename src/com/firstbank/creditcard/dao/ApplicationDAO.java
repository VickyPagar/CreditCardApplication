package com.firstbank.creditcard.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.firstbank.creditcard.vo.ApplicationVO;
import com.firstbank.creditcard.vo.CustomerVO;

/**
 * ApplicationDAO - Data Access Object for Credit Card Application persistence.
 * All database interactions go through Oracle stored procedures via JDBC.
 * Uses JNDI datasource pooling via WebLogic connection pool.
 *
 * @author  FirstBank IT Department - Data Team
 * @version 1.0
 * @since   J2EE 1.3 / JDBC 2.0
 *
 * JNDI Datasource: jdbc/FirstBankCCDS
 * Oracle Driver:   oracle.jdbc.driver.OracleDriver
 */
public class ApplicationDAO {

    private static final Logger logger = Logger.getLogger(ApplicationDAO.class.getName());

    /** JNDI name of the datasource configured in WebLogic/JBoss */
    private static final String DATASOURCE_JNDI = "jdbc/FirstBankCCDS";

    /** Oracle package procedure call strings */
    private static final String CALL_CREATE_CUSTOMER =
        "{ CALL PKG_CREDIT_CARD_APP.SP_CREATE_CUSTOMER(" +
        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

    private static final String CALL_SUBMIT_APPLICATION =
        "{ CALL PKG_CREDIT_CARD_APP.SP_SUBMIT_APPLICATION(" +
        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

    private static final String CALL_EVALUATE_APPLICATION =
        "{ CALL PKG_CREDIT_CARD_APP.SP_EVALUATE_APPLICATION(" +
        "?,?,?,?,?,?,?,?) }";

    private static final String CALL_GET_STATUS =
        "{ CALL PKG_CREDIT_CARD_APP.SP_GET_APPLICATION_STATUS(?,?,?,?,?,?,?) }";

    private static final String CALL_UPDATE_STATUS =
        "{ CALL PKG_CREDIT_CARD_APP.SP_UPDATE_APPLICATION_STATUS(?,?,?,?,?,?,?) }";

    private static final String CALL_ISSUE_CARD =
        "{ CALL PKG_CREDIT_CARD_APP.SP_ISSUE_CREDIT_CARD(?,?,?,?,?,?) }";

    // ----------------------------------------------------------
    // Private utility: Get database connection from pool
    // ----------------------------------------------------------

    private Connection getConnection() throws SQLException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(DATASOURCE_JNDI);
            return ds.getConnection();
        } catch (NamingException e) {
            logger.log(Level.SEVERE, "Cannot lookup JNDI datasource: " + DATASOURCE_JNDI, e);
            throw new SQLException("Cannot obtain database connection from pool: " + e.getMessage());
        }
    }

    private void close(Connection con, CallableStatement cs) {
        if (cs  != null) try { cs.close();  } catch (SQLException e) { logger.warning("Error closing statement: " + e.getMessage()); }
        if (con != null) try { con.close(); } catch (SQLException e) { logger.warning("Error closing connection: " + e.getMessage()); }
    }

    private void close(Connection con, PreparedStatement ps, ResultSet rs) {
        if (rs  != null) try { rs.close();  } catch (SQLException e) { logger.warning("Error closing resultset: " + e.getMessage()); }
        if (ps  != null) try { ps.close();  } catch (SQLException e) { logger.warning("Error closing statement: " + e.getMessage()); }
        if (con != null) try { con.close(); } catch (SQLException e) { logger.warning("Error closing connection: " + e.getMessage()); }
    }

    // ----------------------------------------------------------
    // PUBLIC METHODS
    // ----------------------------------------------------------

    /**
     * Creates a new customer record in the database or returns the ID
     * of an existing customer matched by SSN (idempotent operation).
     *
     * @param customerVO  CustomerVO populated with applicant data
     * @param createdBy   User/session identifier for audit trail
     * @return            The customer ID (new or existing)
     * @throws DAOException on database error
     */
    public long createCustomer(CustomerVO customerVO, String createdBy) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            
            // Check if customer exists by SSN
            ps = con.prepareStatement("SELECT CUSTOMER_ID FROM CC_CUSTOMER WHERE SSN = ?");
            ps.setString(1, customerVO.getSsn());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                long customerId = rs.getLong(1);
                logger.info("Existing customer found: ID=" + customerId);
                return customerId;
            }
            rs.close();
            ps.close();
            
            // Get next customer ID
            ps = con.prepareStatement("SELECT SEQ_CUSTOMER_ID.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();
            rs.next();
            long customerId = rs.getLong(1);
            rs.close();
            ps.close();
            
            // Insert new customer
            ps = con.prepareStatement(
                "INSERT INTO CC_CUSTOMER (CUSTOMER_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, " +
                "DATE_OF_BIRTH, GENDER, SSN, EMAIL_ADDRESS, PHONE_HOME, PHONE_WORK, " +
                "ADDRESS_LINE1, ADDRESS_LINE2, CITY, STATE_CODE, ZIP_CODE, CREATED_BY) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setLong(1, customerId);
            ps.setString(2, customerVO.getFirstName());
            ps.setString(3, customerVO.getMiddleName());
            ps.setString(4, customerVO.getLastName());
            ps.setDate(5, new java.sql.Date(customerVO.getDateOfBirth().getTime()));
            ps.setString(6, customerVO.getGender());
            ps.setString(7, customerVO.getSsn());
            ps.setString(8, customerVO.getEmailAddress());
            ps.setString(9, customerVO.getPhoneHome());
            ps.setString(10, customerVO.getPhoneWork());
            ps.setString(11, customerVO.getAddressLine1());
            ps.setString(12, customerVO.getAddressLine2());
            ps.setString(13, customerVO.getCity());
            ps.setString(14, customerVO.getStateCode());
            ps.setString(15, customerVO.getZipCode());
            ps.setString(16, createdBy);
            ps.executeUpdate();
            
            logger.info("Customer created successfully: ID=" + customerId);
            return customerId;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException in createCustomer", e);
            throw new DAOException(e.getErrorCode(), "Database error: " + e.getMessage());
        } finally {
            close(con, ps, rs);
        }
    }

    /**
     * Submits a new credit card application to the database.
     * Calls SP_SUBMIT_APPLICATION which validates and inserts all records.
     *
     * @param appVO      ApplicationVO with all application data
     * @param customerId Customer's ID (returned from createCustomer)
     * @param createdBy  User/session identifier for audit trail
     * @return           The new application ID
     * @throws DAOException on validation failure or database error
     */
    public long submitApplication(ApplicationVO appVO, long customerId, String createdBy)
            throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            
            // Get application ID
            ps = con.prepareStatement("SELECT SEQ_APPLICATION_ID.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();
            rs.next();
            long applicationId = rs.getLong(1);
            rs.close();
            ps.close();
            
            // Insert application
            ps = con.prepareStatement(
                "INSERT INTO CC_APPLICATION (APPLICATION_ID, CUSTOMER_ID, CARD_TYPE_ID, " +
                "REQUESTED_LIMIT, IP_ADDRESS, STATUS_CODE, CREATED_BY) " +
                "VALUES (?, ?, ?, ?, ?, 'PENDING', ?)"
            );
            ps.setLong(1, applicationId);
            ps.setLong(2, customerId);
            ps.setInt(3, appVO.getCardTypeId());
            ps.setDouble(4, appVO.getRequestedLimit());
            ps.setString(5, appVO.getIpAddress());
            ps.setString(6, createdBy);
            ps.executeUpdate();
            ps.close();
            
            // Insert financial info
            ps = con.prepareStatement("SELECT SEQ_APPLICATION_ID.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();
            rs.next();
            long financialId = rs.getLong(1);
            rs.close();
            ps.close();
            
            ps = con.prepareStatement(
                "INSERT INTO CC_APPLICANT_FINANCIAL (FINANCIAL_ID, APPLICATION_ID, ANNUAL_INCOME, " +
                "OTHER_INCOME, MONTHLY_RENT, TOTAL_DEBT, HOME_OWNERSHIP, BANKRUPTCY_FLAG) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setLong(1, financialId);
            ps.setLong(2, applicationId);
            ps.setDouble(3, appVO.getAnnualIncome());
            ps.setDouble(4, appVO.getOtherIncome());
            ps.setDouble(5, appVO.getMonthlyRent());
            ps.setDouble(6, appVO.getTotalDebt());
            ps.setString(7, appVO.getHomeOwnership());
            ps.setString(8, appVO.getBankruptcyFlag());
            ps.executeUpdate();
            ps.close();
            
            // Insert employment info
            ps = con.prepareStatement("SELECT SEQ_APPLICATION_ID.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();
            rs.next();
            long employmentId = rs.getLong(1);
            rs.close();
            ps.close();
            
            ps = con.prepareStatement(
                "INSERT INTO CC_APPLICANT_EMPLOYMENT (EMPLOYMENT_ID, APPLICATION_ID, EMPLOYMENT_TYPE, " +
                "EMPLOYER_NAME, EMPLOYER_PHONE, JOB_TITLE, YEARS_EMPLOYED) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setLong(1, employmentId);
            ps.setLong(2, applicationId);
            ps.setString(3, appVO.getEmploymentType());
            ps.setString(4, appVO.getEmployerName());
            ps.setString(5, appVO.getEmployerPhone());
            ps.setString(6, appVO.getJobTitle());
            ps.setInt(7, appVO.getYearsEmployed());
            ps.executeUpdate();
            ps.close();
            
            // Audit log
            ps = con.prepareStatement(
                "INSERT INTO CC_APPLICATION_AUDIT (AUDIT_ID, APPLICATION_ID, ACTION_CODE, " +
                "NEW_STATUS, ACTION_BY, ACTION_NOTES, IP_ADDRESS) " +
                "VALUES (SEQ_AUDIT_ID.NEXTVAL, ?, 'SUBMIT', 'PENDING', ?, 'Application submitted', ?)"
            );
            ps.setLong(1, applicationId);
            ps.setString(2, createdBy);
            ps.setString(3, appVO.getIpAddress());
            ps.executeUpdate();
            
            logger.info("Application submitted successfully: ID=" + applicationId);
            return applicationId;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException in submitApplication", e);
            throw new DAOException(e.getErrorCode(), "Database error: " + e.getMessage());
        } finally {
            close(con, ps, rs);
        }
    }

    /**
     * Retrieves the current status of an application.
     *
     * @param applicationId  The unique application ID
     * @return               ApplicationVO with status information populated
     * @throws DAOException  if not found or database error
     */
    public ApplicationVO getApplicationStatus(long applicationId) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(
                "SELECT a.STATUS_CODE, s.STATUS_DESC, a.APPROVED_LIMIT, r.REASON_DESC " +
                "FROM CC_APPLICATION a " +
                "JOIN CC_STATUS_CODE s ON a.STATUS_CODE = s.STATUS_CODE " +
                "LEFT JOIN CC_REJECTION_REASON r ON a.REJECTION_REASON = r.REASON_CODE " +
                "WHERE a.APPLICATION_ID = ?"
            );
            ps.setLong(1, applicationId);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new DAOException(-1, "Application not found: " + applicationId);
            }

            ApplicationVO appVO = new ApplicationVO();
            appVO.setApplicationId(applicationId);
            appVO.setStatusCode(rs.getString(1));
            appVO.setStatusDescription(rs.getString(2));
            appVO.setApprovedLimit(rs.getDouble(3));
            appVO.setRejectionReasonDesc(rs.getString(4));

            return appVO;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException in getApplicationStatus", e);
            throw new DAOException(e.getErrorCode(), "Database error: " + e.getMessage());
        } finally {
            close(con, ps, rs);
        }
    }

    /**
     * Retrieves a list of all card types available for application.
     *
     * @return  List of String arrays: [cardTypeId, cardTypeName, minIncome, creditLimitMin, creditLimitMax, annualFee, interestRate]
     */
    public List getAvailableCardTypes() throws DAOException {
        Connection      con = null;
        PreparedStatement ps = null;
        ResultSet         rs = null;
        List              results = new ArrayList();

        try {
            con = getConnection();
            String sql = "SELECT CARD_TYPE_ID, CARD_TYPE_NAME, CARD_TYPE_CODE, " +
                         "       CREDIT_LIMIT_MIN, CREDIT_LIMIT_MAX, ANNUAL_FEE, " +
                         "       INTEREST_RATE, MIN_INCOME_REQ " +
                         "FROM CC_CARD_TYPE " +
                         "WHERE ACTIVE_FLAG = 'Y' " +
                         "ORDER BY CREDIT_LIMIT_MIN ASC";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String[] row = new String[8];
                row[0] = String.valueOf(rs.getInt("CARD_TYPE_ID"));
                row[1] = rs.getString("CARD_TYPE_NAME");
                row[2] = rs.getString("CARD_TYPE_CODE");
                row[3] = String.valueOf(rs.getDouble("CREDIT_LIMIT_MIN"));
                row[4] = String.valueOf(rs.getDouble("CREDIT_LIMIT_MAX"));
                row[5] = String.valueOf(rs.getDouble("ANNUAL_FEE"));
                row[6] = String.valueOf(rs.getDouble("INTEREST_RATE"));
                row[7] = String.valueOf(rs.getDouble("MIN_INCOME_REQ"));
                results.add(row);
            }

            return results;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException in getAvailableCardTypes", e);
            throw new DAOException(e.getErrorCode(), "Database error: " + e.getMessage());
        } finally {
            close(con, ps, rs);
        }
    }

    /**
     * Runs the auto-decision engine on a submitted application.
     * Called asynchronously from the EJB tier after submission.
     *
     * @param applicationId  Application to evaluate
     * @param creditScore    Credit bureau score (300-850)
     * @param analystId      Processing system or user ID
     * @return               Decision: "APPROVED", "REJECTED", or "REVIEW"
     * @throws DAOException  on error
     */
    public String evaluateApplication(long applicationId, int creditScore, String analystId)
            throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            
            // Get financial info
            ps = con.prepareStatement(
                "SELECT f.ANNUAL_INCOME, f.TOTAL_DEBT, c.MIN_INCOME_REQ, c.CREDIT_LIMIT_MAX " +
                "FROM CC_APPLICATION a " +
                "JOIN CC_APPLICANT_FINANCIAL f ON a.APPLICATION_ID = f.APPLICATION_ID " +
                "JOIN CC_CARD_TYPE c ON a.CARD_TYPE_ID = c.CARD_TYPE_ID " +
                "WHERE a.APPLICATION_ID = ?"
            );
            ps.setLong(1, applicationId);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new DAOException(-1, "Application not found");
            }
            
            double annualIncome = rs.getDouble(1);
            double totalDebt = rs.getDouble(2);
            double minIncome = rs.getDouble(3);
            double maxLimit = rs.getDouble(4);
            rs.close();
            ps.close();
            
            // Calculate debt-to-income ratio
            double debtRatio = (totalDebt / annualIncome) * 100;
            
            // Decision logic
            String decision;
            String rejectionReason = null;
            double approvedLimit = 0;
            
            if (creditScore < 620) {
                decision = "REJECTED";
                rejectionReason = "LOW_SCORE";
            } else if (annualIncome < minIncome) {
                decision = "REJECTED";
                rejectionReason = "LOW_INCOME";
            } else if (debtRatio > 43) {
                decision = "REJECTED";
                rejectionReason = "HIGH_DEBT";
            } else if (creditScore >= 750) {
                decision = "APPROVED";
                approvedLimit = Math.min(maxLimit, annualIncome * 0.3);
            } else if (creditScore >= 620) {
                decision = "APPROVED";
                approvedLimit = Math.min(maxLimit * 0.5, annualIncome * 0.2);
            } else {
                decision = "REVIEW";
            }
            
            // Update application
            ps = con.prepareStatement(
                "UPDATE CC_APPLICATION SET STATUS_CODE = ?, CREDIT_SCORE = ?, " +
                "APPROVED_LIMIT = ?, REJECTION_REASON = ?, ANALYST_ID = ?, REVIEW_DATE = CURRENT_TIMESTAMP " +
                "WHERE APPLICATION_ID = ?"
            );
            ps.setString(1, decision);
            ps.setInt(2, creditScore);
            ps.setDouble(3, approvedLimit);
            ps.setString(4, rejectionReason);
            ps.setString(5, analystId);
            ps.setLong(6, applicationId);
            ps.executeUpdate();
            
            logger.info("Application evaluated: ID=" + applicationId + ", Decision=" + decision);
            return decision;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException in evaluateApplication", e);
            throw new DAOException(e.getErrorCode(), "Database error: " + e.getMessage());
        } finally {
            close(con, ps, rs);
        }
    }
}
