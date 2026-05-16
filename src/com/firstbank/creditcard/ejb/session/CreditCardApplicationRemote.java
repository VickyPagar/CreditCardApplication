package com.firstbank.creditcard.ejb.session;

import javax.ejb.EJBException;
import java.rmi.RemoteException;

import com.firstbank.creditcard.vo.ApplicationVO;
import com.firstbank.creditcard.vo.CustomerVO;
import java.util.List;

/**
 * CreditCardApplicationBean - EJB Remote Interface.
 * Defines the business methods exposed to clients (Struts Actions and
 * SOAP service layer). Implemented as a Stateless Session Bean per
 * EJB 2.0 specification.
 *
 * @author  FirstBank IT Department - EJB Team
 * @version 1.0
 * @since   EJB 2.0 / J2EE 1.3
 */
public interface CreditCardApplicationRemote extends javax.ejb.EJBObject {

    /**
     * Processes a complete credit card application.
     * This is the primary entry point for new applications received via web.
     *
     * @param customerVO  Applicant personal details
     * @param appVO       Application, financial and employment details
     * @param createdBy   Session user or IP for audit trail
     * @return            Application ID assigned to the new application
     * @throws RemoteException on remote communication error
     */
    long processApplication(CustomerVO customerVO, ApplicationVO appVO, String createdBy)
        throws RemoteException;

    /**
     * Retrieves the current status of an application.
     *
     * @param applicationId  Unique application reference number
     * @return               ApplicationVO with status details
     * @throws RemoteException on remote communication error
     */
    ApplicationVO getApplicationStatus(long applicationId) throws RemoteException;

    /**
     * Returns all available card types eligible for application.
     *
     * @return  List of card type data arrays
     * @throws RemoteException on remote communication error
     */
    List getAvailableCardTypes() throws RemoteException;

    /**
     * Triggers the automated credit decision engine for an application.
     * Typically invoked by a back-office batch process or manually by analysts.
     *
     * @param applicationId  Application to evaluate
     * @param creditScore    FICO score obtained from credit bureau (300-850)
     * @param analystId      ID of analyst initiating evaluation
     * @return               Decision: "APPROVED", "REJECTED", "REVIEW"
     * @throws RemoteException on remote communication error
     */
    String evaluateApplication(long applicationId, int creditScore, String analystId)
        throws RemoteException;
}
