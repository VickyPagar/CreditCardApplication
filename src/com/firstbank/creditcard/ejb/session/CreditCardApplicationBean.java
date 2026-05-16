package com.firstbank.creditcard.ejb.session;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.firstbank.creditcard.dao.ApplicationDAO;
import com.firstbank.creditcard.dao.DAOException;
import com.firstbank.creditcard.vo.ApplicationVO;
import com.firstbank.creditcard.vo.CustomerVO;

/**
 * CreditCardApplicationBean - Stateless Session Bean Implementation.
 * Business logic tier for the FirstBank Credit Card Application System.
 *
 * Implements the EJB 2.0 Stateless Session Bean contract. All methods
 * delegate to the DAO layer which invokes Oracle stored procedures.
 *
 * JNDI Name: ejb/CreditCardApplicationBean
 * Transaction: Container-Managed (CMT)
 * TX Attribute: Required (on processApplication, evaluateApplication)
 *               Supports (on read-only methods)
 *
 * Deployment Descriptor: META-INF/ejb-jar.xml
 *
 * @author  FirstBank IT Department - EJB Team
 * @version 1.0  2001-06-15
 * @since   EJB 2.0 / J2EE 1.3
 */
public class CreditCardApplicationBean implements SessionBean {

    private static final Logger logger =
        Logger.getLogger(CreditCardApplicationBean.class.getName());

    /** EJB container context injected by container */
    private SessionContext sessionContext;

    /** DAO for database operations */
    private ApplicationDAO applicationDAO;

    // ----------------------------------------------------------
    // EJB Lifecycle Methods (required by SessionBean interface)
    // ----------------------------------------------------------

    /**
     * Called by container when client invokes the create() method on Home.
     * Initialize resources here (stateless - no client state stored).
     */
    public void ejbCreate() throws CreateException {
        logger.info("CreditCardApplicationBean.ejbCreate() invoked");
        applicationDAO = new ApplicationDAO();
    }

    public void ejbRemove() {
        logger.info("CreditCardApplicationBean.ejbRemove() invoked");
        applicationDAO = null;
    }

    public void ejbActivate() {
        // Not used for Stateless Session Beans
    }

    public void ejbPassivate() {
        // Not used for Stateless Session Beans
    }

    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }

    // ----------------------------------------------------------
    // BUSINESS METHODS
    // ----------------------------------------------------------

    /**
     * Processes a full credit card application submission end-to-end:
     *   1. Creates or retrieves the customer record
     *   2. Submits the application with financial/employment data
     *   3. Returns the application ID for tracking
     *
     * Transaction: Required (container starts new TX if none exists)
     *
     * @param customerVO  Applicant personal information
     * @param appVO       Application, financial, employment details
     * @param createdBy   Initiating user/session for audit trail
     * @return            Newly assigned application ID
     */
    public long processApplication(CustomerVO customerVO, ApplicationVO appVO, String createdBy) {
        logger.info("processApplication() called for: " + customerVO.getFullName());
        long customerId    = 0;
        long applicationId = 0;

        try {
            // Step 1: Create or find the customer
            customerId = applicationDAO.createCustomer(customerVO, createdBy);
            logger.info("Customer ID resolved: " + customerId);

            // Step 2: Populate application object
            appVO.setCustomerId(customerId);

            // Step 3: Submit the application
            applicationId = applicationDAO.submitApplication(appVO, customerId, createdBy);
            logger.info("Application submitted. Application ID: " + applicationId);

            return applicationId;

        } catch (DAOException e) {
            logger.log(Level.SEVERE, "DAOException in processApplication", e);
            // Roll back transaction via container
            sessionContext.setRollbackOnly();
            throw new EJBException("Application processing failed: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in processApplication", e);
            sessionContext.setRollbackOnly();
            throw new EJBException("Unexpected error: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves the current status of a credit card application.
     * Transaction: Supports (no TX needed for read-only)
     *
     * @param applicationId  The application reference number
     * @return               ApplicationVO with populated status fields
     */
    public ApplicationVO getApplicationStatus(long applicationId) {
        logger.info("getApplicationStatus() called for application: " + applicationId);
        try {
            return applicationDAO.getApplicationStatus(applicationId);
        } catch (DAOException e) {
            logger.log(Level.WARNING, "Application not found: " + applicationId, e);
            throw new EJBException("Cannot retrieve application status: " + e.getMessage(), e);
        }
    }

    /**
     * Returns all active card types available for application.
     * Transaction: Supports (read-only)
     *
     * @return  List of String[] arrays with card type data
     */
    public List getAvailableCardTypes() {
        logger.info("getAvailableCardTypes() called");
        try {
            return applicationDAO.getAvailableCardTypes();
        } catch (DAOException e) {
            logger.log(Level.SEVERE, "Error retrieving card types", e);
            throw new EJBException("Error retrieving card types: " + e.getMessage(), e);
        }
    }

    /**
     * Runs the automated credit decision engine against a pending application.
     * Typically invoked by a back-office system or analyst after obtaining
     * the credit bureau score.
     *
     * Transaction: Required
     *
     * @param applicationId  Application to evaluate
     * @param creditScore    FICO credit score from bureau (300-850)
     * @param analystId      User or system ID initiating the decision
     * @return               "APPROVED", "REJECTED", or "REVIEW"
     */
    public String evaluateApplication(long applicationId, int creditScore, String analystId) {
        logger.info("evaluateApplication() for appId=" + applicationId + ", score=" + creditScore);

        if (creditScore < 300 || creditScore > 850) {
            throw new EJBException("Invalid credit score: " + creditScore +
                                   ". Must be between 300 and 850.");
        }

        try {
            String decision = applicationDAO.evaluateApplication(applicationId, creditScore, analystId);
            logger.info("Decision for application " + applicationId + ": " + decision);
            return decision;
        } catch (DAOException e) {
            logger.log(Level.SEVERE, "Error in evaluateApplication for appId=" + applicationId, e);
            sessionContext.setRollbackOnly();
            throw new EJBException("Credit evaluation failed: " + e.getMessage(), e);
        }
    }
}
