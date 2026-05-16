package com.firstbank.creditcard.service;

import java.io.Serializable;

/**
 * ApplicationStatusResponse - SOAP response for status queries.
 */
public class ApplicationStatusResponse implements Serializable {
    private static final long serialVersionUID = 5678901234567890123L;

    private boolean success;
    private String  applicationId;
    private String  statusCode;
    private String  statusDescription;
    private double  approvedLimit;
    private String  rejectionReason;
    private String  errorCode;
    private String  errorMessage;

    public ApplicationStatusResponse() { }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }

    public String getStatusDescription() { return statusDescription; }
    public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }

    public double getApprovedLimit() { return approvedLimit; }
    public void setApprovedLimit(double approvedLimit) { this.approvedLimit = approvedLimit; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
