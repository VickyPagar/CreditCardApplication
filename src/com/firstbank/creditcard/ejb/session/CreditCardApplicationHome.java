package com.firstbank.creditcard.ejb.session;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * CreditCardApplicationHome - EJB Home Interface.
 * Used by clients to obtain a reference to the Stateless Session Bean.
 * Looked up via JNDI: ejb/CreditCardApplicationBean
 *
 * @author  FirstBank IT Department
 * @version 1.0
 * @since   EJB 2.0
 */
public interface CreditCardApplicationHome extends EJBHome {

    /**
     * Creates a new bean instance (stateless, always returns same pool member).
     *
     * @return CreditCardApplicationRemote interface
     * @throws CreateException if creation fails
     * @throws RemoteException on RMI error
     */
    CreditCardApplicationRemote create() throws CreateException, RemoteException;
}
