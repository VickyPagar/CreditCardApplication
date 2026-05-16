package com.firstbank.creditcard.dao;

/**
 * DAOException - Custom exception class for Data Access Object layer.
 * Wraps database errors and stored procedure return codes into a
 * consistent exception type for the service layer to handle.
 *
 * @author  FirstBank IT Department
 * @version 1.0
 * @since   J2EE 1.3
 */
public class DAOException extends Exception {

    private static final long serialVersionUID = 1234567890123456789L;

    private int errorCode;

    public DAOException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public DAOException(String message) {
        super(message);
        this.errorCode = -1;
    }

    public DAOException(String message, Throwable cause) {
        super(message);
        this.errorCode = -1;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String toString() {
        return "DAOException[code=" + errorCode + ", message=" + getMessage() + "]";
    }
}
