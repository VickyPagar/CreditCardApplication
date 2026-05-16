package com.firstbank.creditcard.service;

import java.io.Serializable;

/**
 * CreditCardApplicationResponse - SOAP Response bean.
 * Returned by SOAP service after application submission or status query.
 *
 * @author  FirstBank IT Department - Integration Team
 * @version 1.0
 */
public class CreditCardApplicationResponse implements Serializable {

    private static final long serialVersionUID = 4567890123456789012L;

    private boolean success;
    private String  applicationId;
    private String  statusCode;
    private String  statusDescription;
    private String  errorCode;
    private String  errorMessage;

    public CreditCardApplicationResponse() { }

    public CreditCardApplicationResponse(boolean success, String applicationId, String statusCode) {
        this.success       = success;
        this.applicationId = applicationId;
        this.statusCode    = statusCode;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }

    public String getStatusDescription() { return statusDescription; }
    public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
