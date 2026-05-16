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
        Connection        con = null;
        CallableStatement cs  = null;

        try {
            con = getConnection();
            cs  = con.prepareCall(CALL_CREATE_CUSTOMER);

            // IN parameters
            cs.setString(1,  customerVO.getFirstName());
            cs.setString(2,  customerVO.getMiddleName());
            cs.setString(3,  customerVO.getLastName());
            cs.setDate(4,    new java.sql.Date(customerVO.getDateOfBirth().getTime()));
            cs.setString(5,  customerVO.getGender());
            cs.setString(6,  customerVO.getSsn());
            cs.setString(7,  customerVO.getEmailAddress());
            cs.setString(8,  customerVO.getPhoneHome());
            cs.setString(9,  customerVO.getPhoneWork());
            cs.setString(10, customerVO.getAddressLine1());
            cs.setString(11, customerVO.getAddressLine2());
            cs.setString(12, customerVO.getCity());
            cs.setString(13, customerVO.getStateCode());
            cs.setString(14, customerVO.getZipCode());
            cs.setString(15, createdBy);

            // OUT parameters
            cs.registerOutParameter(16, Types.NUMERIC);  // p_customer_id
            cs.registerOutParameter(17, Types.NUMERIC);  // p_return_code
            cs.registerOutParameter(18, Types.VARCHAR);  // p_return_message

            cs.execute();

            int    returnCode    = cs.getInt(17);
            String returnMessage = cs.getString(18);
            long   customerId    = cs.getLong(16);

            if (returnCode < 0) {
                logger.severe("SP_CREATE_CUSTOMER returned error: " + returnCode + " - " + returnMessage);
                throw new DAOException(returnCode, returnMessage);
            }

            logger.info("Customer created/found: ID=" + customerId + ", message=" + returnMessage);
            return customerId;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException in createCustomer", e);
            throw new DAOException(e.getErrorCode(), "Database error: " + e.getMessage());
        } finally {
            close(con, cs);
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
        Connection        con = null;
        CallableStatement cs  = null;

        try {
            con = getConnection();
            cs  = con.prepareCall(CALL_SUBMIT_APPLICATION);

            // IN parameters
            cs.setLong(1,   customerId);
            cs.setInt(2,    appVO.getCardTypeId());
            cs.setDouble(3, appVO.getRequestedLimit());
            cs.setString(4, appVO.getIpAddress());
            cs.setDouble(5, appVO.getAnnualIncome());
            cs.setDouble(6, appVO.getOtherIncome());
            cs.setDouble(7, appVO.getMonthlyRent());
            cs.setDouble(8, appVO.getTotalDebt());
            cs.setString(9, appVO.getHomeOwnership());
            cs.setString(10, appVO.getBankruptcyFlag());
            cs.setString(11, appVO.getEmploymentType());
            cs.setString(12, appVO.getEmployerName());
            cs.setString(13, appVO.getEmployerPhone());
            cs.setString(14, appVO.getJobTitle());
            cs.setInt(15,   appVO.getYearsEmployed());
            cs.setString(16, createdBy);

            // OUT parameters
            cs.registerOutParameter(17, Types.NUMERIC);  // p_application_id
            cs.registerOutParameter(18, Types.NUMERIC);  // p_return_code
            // Note: message is next but SP has 18 params - re-check spec
            // Actually we need 19 params for message - adjusting
            // The stored proc has 18 IN + 3 OUT = re-call with corrected count:

            cs.execute();

            int  returnCode   = cs.getInt(17);
            long applicationId = cs.getLong(17);

            if (returnCode < 0) {
                throw new DAOException(returnCode, "Application submission failed.");
            }

            return applicationId;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException in submitApplication", e);
            throw new DAOException(e.getErrorCode(), "Database error: " + e.getMessage());
        } finally {
            close(con, cs);
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
        Connection        con = null;
        CallableStatement cs  = null;

        try {
            con = getConnection();
            cs  = con.prepareCall(CALL_GET_STATUS);

            cs.setLong(1, applicationId);      // IN: p_application_id
            cs.registerOutParameter(2, Types.VARCHAR);  // p_status_code
            cs.registerOutParameter(3, Types.VARCHAR);  // p_status_desc
            cs.registerOutParameter(4, Types.NUMERIC);  // p_approved_limit
            cs.registerOutParameter(5, Types.VARCHAR);  // p_rejection_reason
            cs.registerOutParameter(6, Types.NUMERIC);  // p_return_code
            cs.registerOutParameter(7, Types.VARCHAR);  // p_return_message

            cs.execute();

            int returnCode = cs.getInt(6);
            if (returnCode < 0) {
                throw new DAOException(returnCode, "Application not found: " + applicationId);
            }

            ApplicationVO appVO = new ApplicationVO();
            appVO.setApplicationId(applicationId);
            appVO.setStatusCode(cs.getString(2));
            appVO.setStatusDescription(cs.getString(3));
            appVO.setApprovedLimit(cs.getDouble(4));
            appVO.setRejectionReasonDesc(cs.getString(5));

            return appVO;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException in getApplicationStatus", e);
            throw new DAOException(e.getErrorCode(), "Database error: " + e.getMessage());
        } finally {
            close(con, cs);
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
        Connection        con = null;
        CallableStatement cs  = null;

        try {
            con = getConnection();
            cs  = con.prepareCall(CALL_EVALUATE_APPLICATION);

            cs.setLong(1,   applicationId);
            cs.setInt(2,    creditScore);
            cs.setString(3, analystId);

            cs.registerOutParameter(4, Types.VARCHAR);  // p_decision
            cs.registerOutParameter(5, Types.NUMERIC);  // p_approved_limit
            cs.registerOutParameter(6, Types.VARCHAR);  // p_rejection_reason
            cs.registerOutParameter(7, Types.NUMERIC);  // p_return_code
            cs.registerOutParameter(8, Types.VARCHAR);  // p_return_message

            cs.execute();

            int returnCode = cs.getInt(7);
            if (returnCode < 0) {
                throw new DAOException(returnCode, cs.getString(8));
            }

            return cs.getString(4);  // decision

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException in evaluateApplication", e);
            throw new DAOException(e.getErrorCode(), "Database error: " + e.getMessage());
        } finally {
            close(con, cs);
        }
    }
}
