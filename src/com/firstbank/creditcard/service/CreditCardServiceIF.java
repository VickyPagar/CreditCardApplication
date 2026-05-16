package com.firstbank.creditcard.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * CreditCardService - SOAP Web Service Interface.
 * Exposes credit card application operations as SOAP-accessible
 * methods via Apache AXIS 1.x on J2EE 1.3.
 *
 * Service Endpoint: http://host:7001/creditcard/services/CreditCardService
 * WSDL Location:    http://host:7001/creditcard/services/CreditCardService?wsdl
 *
 * @author  FirstBank IT Department - Integration Team
 * @version 1.0  2001-06-15
 * @since   Apache AXIS 1.x / JAX-RPC 1.0
 */
public interface CreditCardServiceIF extends Remote {

    /**
     * Submits a new credit card application via SOAP.
     * Corresponds to a partner bank or branch channel application.
     *
     * @param request  SOAP request object with all application data
     * @return         SOAP response with application ID and status
     * @throws RemoteException  on service invocation failure
     */
    CreditCardApplicationResponse submitApplication(CreditCardApplicationRequest request)
        throws RemoteException;

    /**
     * Queries the status of a submitted application.
     *
     * @param applicationId  Unique application reference number (string)
     * @return               Status response with current decision details
     * @throws RemoteException on service invocation failure
     */
    ApplicationStatusResponse getApplicationStatus(String applicationId)
        throws RemoteException;

    /**
     * Returns list of available card products.
     * Used by branch and partner portals to show available options.
     *
     * @return  Array of CardTypeInfo objects
     * @throws RemoteException on service invocation failure
     */
    CardTypeInfo[] getCardTypes() throws RemoteException;
}
