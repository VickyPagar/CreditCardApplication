package com.firstbank.creditcard.dao;

import java.sql.*;

/**
 * H2 Database Stored Procedures Implementation
 * Simulates Oracle PL/SQL package PKG_CREDIT_CARD_APP for H2 database
 * 
 * @author FirstBank IT Department
 * @version 1.0 - H2 Compatible
 */
public class H2Procedures {

    /**
     * SP_CREATE_CUSTOMER - Creates or finds existing customer by SSN
     */
    public static void spCreateCustomer(
        Connection conn,
        String p_first_name,
        String p_middle_name,
        String p_last_name,
        Date p_date_of_birth,
        String p_gender,
        String p_ssn,
        String p_email,
        String p_phone_home,
        String p_phone_work,
        String p_address_line1,
        String p_address_line2,
        String p_city,
        String p_state_code,
        String p_zip_code,
        String p_created_by,
        long[] p_customer_id,
        int[] p_return_code,
        String[] p_return_message
    ) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Check if customer exists by SSN
            ps = conn.prepareStatement("SELECT CUSTOMER_ID FROM CC_CUSTOMER WHERE SSN = ?");
            ps.setString(1, p_ssn);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                p_customer_id[0] = rs.getLong(1);
                p_return_code[0] = 0;
                p_return_message[0] = "Existing customer found";
                return;
            }
            rs.close();
            ps.close();
            
            // Get next customer ID
            ps = conn.prepareStatement("SELECT SEQ_CUSTOMER_ID.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();
            rs.next();
            long customerId = rs.getLong(1);
            rs.close();
            ps.close();
            
            // Insert new customer
            ps = conn.prepareStatement(
                "INSERT INTO CC_CUSTOMER (CUSTOMER_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, " +
                "DATE_OF_BIRTH, GENDER, SSN, EMAIL_ADDRESS, PHONE_HOME, PHONE_WORK, " +
                "ADDRESS_LINE1, ADDRESS_LINE2, CITY, STATE_CODE, ZIP_CODE, CREATED_BY) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setLong(1, customerId);
            ps.setString(2, p_first_name);
            ps.setString(3, p_middle_name);
            ps.setString(4, p_last_name);
            ps.setDate(5, p_date_of_birth);
            ps.setString(6, p_gender);
            ps.setString(7, p_ssn);
            ps.setString(8, p_email);
            ps.setString(9, p_phone_home);
            ps.setString(10, p_phone_work);
            ps.setString(11, p_address_line1);
            ps.setString(12, p_address_line2);
            ps.setString(13, p_city);
            ps.setString(14, p_state_code);
            ps.setString(15, p_zip_code);
            ps.setString(16, p_created_by);
            ps.executeUpdate();
            
            p_customer_id[0] = customerId;
            p_return_code[0] = 0;
            p_return_message[0] = "Customer created successfully";
            
        } catch (SQLException e) {
            p_return_code[0] = -1;
            p_return_message[0] = "Error: " + e.getMessage();
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
        }
    }

    /**
     * SP_SUBMIT_APPLICATION - Submits new credit card application
     */
    public static void spSubmitApplication(
        Connection conn,
        long p_customer_id,
        int p_card_type_id,
        double p_requested_limit,
        String p_ip_address,
        double p_annual_income,
        double p_other_income,
        double p_monthly_rent,
        double p_total_debt,
        String p_home_ownership,
        String p_bankruptcy_flag,
        String p_employment_type,
        String p_employer_name,
        String p_employer_phone,
        String p_job_title,
        int p_years_employed,
        String p_created_by,
        long[] p_application_id,
        int[] p_return_code
    ) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Get application ID
            ps = conn.prepareStatement("SELECT SEQ_APPLICATION_ID.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();
            rs.next();
            long applicationId = rs.getLong(1);
            rs.close();
            ps.close();
            
            // Insert application
            ps = conn.prepareStatement(
                "INSERT INTO CC_APPLICATION (APPLICATION_ID, CUSTOMER_ID, CARD_TYPE_ID, " +
                "REQUESTED_LIMIT, IP_ADDRESS, STATUS_CODE, CREATED_BY) " +
                "VALUES (?, ?, ?, ?, ?, 'PENDING', ?)"
            );
            ps.setLong(1, applicationId);
            ps.setLong(2, p_customer_id);
            ps.setInt(3, p_card_type_id);
            ps.setDouble(4, p_requested_limit);
            ps.setString(5, p_ip_address);
            ps.setString(6, p_created_by);
            ps.executeUpdate();
            ps.close();
            
            // Insert financial info
            ps = conn.prepareStatement("SELECT SEQ_APPLICATION_ID.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();
            rs.next();
            long financialId = rs.getLong(1);
            rs.close();
            ps.close();
            
            ps = conn.prepareStatement(
                "INSERT INTO CC_APPLICANT_FINANCIAL (FINANCIAL_ID, APPLICATION_ID, ANNUAL_INCOME, " +
                "OTHER_INCOME, MONTHLY_RENT, TOTAL_DEBT, HOME_OWNERSHIP, BANKRUPTCY_FLAG) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setLong(1, financialId);
            ps.setLong(2, applicationId);
            ps.setDouble(3, p_annual_income);
            ps.setDouble(4, p_other_income);
            ps.setDouble(5, p_monthly_rent);
            ps.setDouble(6, p_total_debt);
            ps.setString(7, p_home_ownership);
            ps.setString(8, p_bankruptcy_flag);
            ps.executeUpdate();
            ps.close();
            
            // Insert employment info
            ps = conn.prepareStatement("SELECT SEQ_APPLICATION_ID.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();
            rs.next();
            long employmentId = rs.getLong(1);
            rs.close();
            ps.close();
            
            ps = conn.prepareStatement(
                "INSERT INTO CC_APPLICANT_EMPLOYMENT (EMPLOYMENT_ID, APPLICATION_ID, EMPLOYMENT_TYPE, " +
                "EMPLOYER_NAME, EMPLOYER_PHONE, JOB_TITLE, YEARS_EMPLOYED) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setLong(1, employmentId);
            ps.setLong(2, applicationId);
            ps.setString(3, p_employment_type);
            ps.setString(4, p_employer_name);
            ps.setString(5, p_employer_phone);
            ps.setString(6, p_job_title);
            ps.setInt(7, p_years_employed);
            ps.executeUpdate();
            ps.close();
            
            // Audit log
            ps = conn.prepareStatement(
                "INSERT INTO CC_APPLICATION_AUDIT (AUDIT_ID, APPLICATION_ID, ACTION_CODE, " +
                "NEW_STATUS, ACTION_BY, ACTION_NOTES, IP_ADDRESS) " +
                "VALUES (SEQ_AUDIT_ID.NEXTVAL, ?, 'SUBMIT', 'PENDING', ?, 'Application submitted', ?)"
            );
            ps.setLong(1, applicationId);
            ps.setString(2, p_created_by);
            ps.setString(3, p_ip_address);
            ps.executeUpdate();
            
            p_application_id[0] = applicationId;
            p_return_code[0] = 0;
            
        } catch (SQLException e) {
            p_return_code[0] = -1;
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
        }
    }

    /**
     * SP_EVALUATE_APPLICATION - Auto-decision engine
     */
    public static void spEvaluateApplication(
        Connection conn,
        long p_application_id,
        int p_credit_score,
        String p_analyst_id,
        String[] p_decision,
        double[] p_approved_limit,
        String[] p_rejection_reason,
        int[] p_return_code,
        String[] p_return_message
    ) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Get financial info
            ps = conn.prepareStatement(
                "SELECT f.ANNUAL_INCOME, f.TOTAL_DEBT, c.MIN_INCOME_REQ, c.CREDIT_LIMIT_MAX " +
                "FROM CC_APPLICATION a " +
                "JOIN CC_APPLICANT_FINANCIAL f ON a.APPLICATION_ID = f.APPLICATION_ID " +
                "JOIN CC_CARD_TYPE c ON a.CARD_TYPE_ID = c.CARD_TYPE_ID " +
                "WHERE a.APPLICATION_ID = ?"
            );
            ps.setLong(1, p_application_id);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                p_return_code[0] = -1;
                p_return_message[0] = "Application not found";
                return;
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
            if (p_credit_score < 620) {
                p_decision[0] = "REJECTED";
                p_rejection_reason[0] = "LOW_SCORE";
                p_approved_limit[0] = 0;
            } else if (annualIncome < minIncome) {
                p_decision[0] = "REJECTED";
                p_rejection_reason[0] = "LOW_INCOME";
                p_approved_limit[0] = 0;
            } else if (debtRatio > 43) {
                p_decision[0] = "REJECTED";
                p_rejection_reason[0] = "HIGH_DEBT";
                p_approved_limit[0] = 0;
            } else if (p_credit_score >= 750) {
                p_decision[0] = "APPROVED";
                p_approved_limit[0] = Math.min(maxLimit, annualIncome * 0.3);
            } else if (p_credit_score >= 620) {
                p_decision[0] = "APPROVED";
                p_approved_limit[0] = Math.min(maxLimit * 0.5, annualIncome * 0.2);
            } else {
                p_decision[0] = "REVIEW";
                p_approved_limit[0] = 0;
            }
            
            // Update application
            ps = conn.prepareStatement(
                "UPDATE CC_APPLICATION SET STATUS_CODE = ?, CREDIT_SCORE = ?, " +
                "APPROVED_LIMIT = ?, REJECTION_REASON = ?, ANALYST_ID = ?, REVIEW_DATE = CURRENT_TIMESTAMP " +
                "WHERE APPLICATION_ID = ?"
            );
            ps.setString(1, p_decision[0]);
            ps.setInt(2, p_credit_score);
            ps.setDouble(3, p_approved_limit[0]);
            ps.setString(4, p_rejection_reason[0]);
            ps.setString(5, p_analyst_id);
            ps.setLong(6, p_application_id);
            ps.executeUpdate();
            
            p_return_code[0] = 0;
            p_return_message[0] = "Evaluation completed";
            
        } catch (SQLException e) {
            p_return_code[0] = -1;
            p_return_message[0] = "Error: " + e.getMessage();
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
        }
    }

    /**
     * SP_GET_APPLICATION_STATUS - Retrieves application status
     */
    public static void spGetApplicationStatus(
        Connection conn,
        long p_application_id,
        String[] p_status_code,
        String[] p_status_desc,
        double[] p_approved_limit,
        String[] p_rejection_reason,
        int[] p_return_code,
        String[] p_return_message
    ) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement(
                "SELECT a.STATUS_CODE, s.STATUS_DESC, a.APPROVED_LIMIT, r.REASON_DESC " +
                "FROM CC_APPLICATION a " +
                "JOIN CC_STATUS_CODE s ON a.STATUS_CODE = s.STATUS_CODE " +
                "LEFT JOIN CC_REJECTION_REASON r ON a.REJECTION_REASON = r.REASON_CODE " +
                "WHERE a.APPLICATION_ID = ?"
            );
            ps.setLong(1, p_application_id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                p_status_code[0] = rs.getString(1);
                p_status_desc[0] = rs.getString(2);
                p_approved_limit[0] = rs.getDouble(3);
                p_rejection_reason[0] = rs.getString(4);
                p_return_code[0] = 0;
                p_return_message[0] = "Status retrieved";
            } else {
                p_return_code[0] = -1;
                p_return_message[0] = "Application not found";
            }
            
        } catch (SQLException e) {
            p_return_code[0] = -1;
            p_return_message[0] = "Error: " + e.getMessage();
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
        }
    }

    /**
     * SP_UPDATE_APPLICATION_STATUS - Updates application status
     */
    public static void spUpdateApplicationStatus(
        Connection conn,
        long p_application_id,
        String p_new_status,
        double p_approved_limit,
        String p_rejection_reason,
        String p_analyst_id,
        int[] p_return_code,
        String[] p_return_message
    ) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Get old status
            ps = conn.prepareStatement("SELECT STATUS_CODE FROM CC_APPLICATION WHERE APPLICATION_ID = ?");
            ps.setLong(1, p_application_id);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                p_return_code[0] = -1;
                p_return_message[0] = "Application not found";
                return;
            }
            
            String oldStatus = rs.getString(1);
            rs.close();
            ps.close();
            
            // Update application
            ps = conn.prepareStatement(
                "UPDATE CC_APPLICATION SET STATUS_CODE = ?, APPROVED_LIMIT = ?, " +
                "REJECTION_REASON = ?, ANALYST_ID = ?, REVIEW_DATE = CURRENT_TIMESTAMP " +
                "WHERE APPLICATION_ID = ?"
            );
            ps.setString(1, p_new_status);
            ps.setDouble(2, p_approved_limit);
            ps.setString(3, p_rejection_reason);
            ps.setString(4, p_analyst_id);
            ps.setLong(5, p_application_id);
            ps.executeUpdate();
            ps.close();
            
            // Audit log
            ps = conn.prepareStatement(
                "INSERT INTO CC_APPLICATION_AUDIT (AUDIT_ID, APPLICATION_ID, ACTION_CODE, " +
                "OLD_STATUS, NEW_STATUS, ACTION_BY) " +
                "VALUES (SEQ_AUDIT_ID.NEXTVAL, ?, 'STATUS_UPDATE', ?, ?, ?)"
            );
            ps.setLong(1, p_application_id);
            ps.setString(2, oldStatus);
            ps.setString(3, p_new_status);
            ps.setString(4, p_analyst_id);
            ps.executeUpdate();
            
            p_return_code[0] = 0;
            p_return_message[0] = "Status updated";
            
        } catch (SQLException e) {
            p_return_code[0] = -1;
            p_return_message[0] = "Error: " + e.getMessage();
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
        }
    }

    /**
     * SP_ISSUE_CREDIT_CARD - Issues credit card for approved application
     */
    public static void spIssueCreditCard(
        Connection conn,
        long p_application_id,
        String p_card_number,
        String p_cvv_hash,
        long[] p_card_id,
        int[] p_return_code,
        String[] p_return_message
    ) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Get application details
            ps = conn.prepareStatement(
                "SELECT a.CUSTOMER_ID, a.CARD_TYPE_ID, a.APPROVED_LIMIT, " +
                "c.FIRST_NAME || ' ' || c.LAST_NAME " +
                "FROM CC_APPLICATION a " +
                "JOIN CC_CUSTOMER c ON a.CUSTOMER_ID = c.CUSTOMER_ID " +
                "WHERE a.APPLICATION_ID = ? AND a.STATUS_CODE = 'APPROVED'"
            );
            ps.setLong(1, p_application_id);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                p_return_code[0] = -1;
                p_return_message[0] = "Application not found or not approved";
                return;
            }
            
            long customerId = rs.getLong(1);
            int cardTypeId = rs.getInt(2);
            double approvedLimit = rs.getDouble(3);
            String cardHolderName = rs.getString(4);
            rs.close();
            ps.close();
            
            // Get card ID
            ps = conn.prepareStatement("SELECT SEQ_CARD_ID.NEXTVAL FROM DUAL");
            rs = ps.executeQuery();
            rs.next();
            long cardId = rs.getLong(1);
            rs.close();
            ps.close();
            
            // Insert card
            ps = conn.prepareStatement(
                "INSERT INTO CC_CREDIT_CARD (CARD_ID, APPLICATION_ID, CUSTOMER_ID, CARD_TYPE_ID, " +
                "CARD_NUMBER, CARD_HOLDER_NAME, EXPIRY_MONTH, EXPIRY_YEAR, CVV_HASH, " +
                "CREDIT_LIMIT, AVAILABLE_CREDIT, CARD_STATUS) " +
                "VALUES (?, ?, ?, ?, ?, ?, MONTH(ADD_MONTHS(CURRENT_DATE, 36)), " +
                "YEAR(ADD_MONTHS(CURRENT_DATE, 36)), ?, ?, ?, 'INACTIVE')"
            );
            ps.setLong(1, cardId);
            ps.setLong(2, p_application_id);
            ps.setLong(3, customerId);
            ps.setInt(4, cardTypeId);
            ps.setString(5, p_card_number);
            ps.setString(6, cardHolderName);
            ps.setString(7, p_cvv_hash);
            ps.setDouble(8, approvedLimit);
            ps.setDouble(9, approvedLimit);
            ps.executeUpdate();
            
            p_card_id[0] = cardId;
            p_return_code[0] = 0;
            p_return_message[0] = "Card issued successfully";
            
        } catch (SQLException e) {
            p_return_code[0] = -1;
            p_return_message[0] = "Error: " + e.getMessage();
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
        }
    }
}

// Made with Bob
