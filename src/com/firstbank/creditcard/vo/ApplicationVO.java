package com.firstbank.creditcard.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ApplicationVO - Value Object for Credit Card Application data.
 * Aggregates all application information including personal, financial,
 * and employment data for transfer between application layers.
 *
 * @author  FirstBank IT Department
 * @version 1.0
 * @since   J2EE 1.3
 */
public class ApplicationVO implements Serializable {

    private static final long serialVersionUID = -2345678901234567890L;

    // ----- Application Fields -----
    private long   applicationId;
    private long   customerId;
    private int    cardTypeId;
    private String cardTypeName;
    private Date   applicationDate;
    private String statusCode;
    private String statusDescription;
    private double requestedLimit;
    private double approvedLimit;
    private int    creditScore;
    private String rejectionReason;
    private String rejectionReasonDesc;
    private String analystId;
    private Date   reviewDate;
    private String reviewNotes;
    private String ipAddress;
    private String channelCode;

    // ----- Financial Fields -----
    private double annualIncome;
    private double otherIncome;
    private double monthlyRent;
    private double mortgageBalance;
    private double totalDebt;
    private double checkingBalance;
    private double savingsBalance;
    private String homeOwnership;
    private String bankruptcyFlag;
    private Date   bankruptcyDate;

    // ----- Employment Fields -----
    private String employmentType;
    private String employerName;
    private String employerPhone;
    private String jobTitle;
    private int    yearsEmployed;
    private int    monthsEmployed;

    // ----- Customer snapshot -----
    private CustomerVO customer;

    // ----------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------

    public ApplicationVO() {
        this.channelCode    = "WEB";
        this.bankruptcyFlag = "N";
    }

    // ----------------------------------------------------------
    // Derived / Business Methods
    // ----------------------------------------------------------

    /**
     * Calculates debt-to-income ratio.
     */
    public double getDebtToIncomeRatio() {
        if (annualIncome > 0) {
            return totalDebt / annualIncome;
        }
        return 0.0;
    }

    /**
     * Returns true if application is in a terminal state.
     */
    public boolean isDecisionMade() {
        return "APPROVED".equals(statusCode) ||
               "REJECTED".equals(statusCode) ||
               "CANCELLED".equals(statusCode);
    }

    /**
     * Returns true if the application was approved.
     */
    public boolean isApproved() {
        return "APPROVED".equals(statusCode);
    }

    /**
     * Returns true if the application was rejected.
     */
    public boolean isRejected() {
        return "REJECTED".equals(statusCode);
    }

    /**
     * Returns the total household income (annual + other).
     */
    public double getTotalIncome() {
        return annualIncome + otherIncome;
    }

    // ----------------------------------------------------------
    // Getters and Setters
    // ----------------------------------------------------------

    public long getApplicationId() { return applicationId; }
    public void setApplicationId(long applicationId) { this.applicationId = applicationId; }

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }

    public int getCardTypeId() { return cardTypeId; }
    public void setCardTypeId(int cardTypeId) { this.cardTypeId = cardTypeId; }

    public String getCardTypeName() { return cardTypeName; }
    public void setCardTypeName(String cardTypeName) { this.cardTypeName = cardTypeName; }

    public Date getApplicationDate() { return applicationDate; }
    public void setApplicationDate(Date applicationDate) { this.applicationDate = applicationDate; }

    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }

    public String getStatusDescription() { return statusDescription; }
    public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }

    public double getRequestedLimit() { return requestedLimit; }
    public void setRequestedLimit(double requestedLimit) { this.requestedLimit = requestedLimit; }

    public double getApprovedLimit() { return approvedLimit; }
    public void setApprovedLimit(double approvedLimit) { this.approvedLimit = approvedLimit; }

    public int getCreditScore() { return creditScore; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public String getRejectionReasonDesc() { return rejectionReasonDesc; }
    public void setRejectionReasonDesc(String rejectionReasonDesc) { this.rejectionReasonDesc = rejectionReasonDesc; }

    public String getAnalystId() { return analystId; }
    public void setAnalystId(String analystId) { this.analystId = analystId; }

    public Date getReviewDate() { return reviewDate; }
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }

    public String getReviewNotes() { return reviewNotes; }
    public void setReviewNotes(String reviewNotes) { this.reviewNotes = reviewNotes; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getChannelCode() { return channelCode; }
    public void setChannelCode(String channelCode) { this.channelCode = channelCode; }

    public double getAnnualIncome() { return annualIncome; }
    public void setAnnualIncome(double annualIncome) { this.annualIncome = annualIncome; }

    public double getOtherIncome() { return otherIncome; }
    public void setOtherIncome(double otherIncome) { this.otherIncome = otherIncome; }

    public double getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(double monthlyRent) { this.monthlyRent = monthlyRent; }

    public double getMortgageBalance() { return mortgageBalance; }
    public void setMortgageBalance(double mortgageBalance) { this.mortgageBalance = mortgageBalance; }

    public double getTotalDebt() { return totalDebt; }
    public void setTotalDebt(double totalDebt) { this.totalDebt = totalDebt; }

    public double getCheckingBalance() { return checkingBalance; }
    public void setCheckingBalance(double checkingBalance) { this.checkingBalance = checkingBalance; }

    public double getSavingsBalance() { return savingsBalance; }
    public void setSavingsBalance(double savingsBalance) { this.savingsBalance = savingsBalance; }

    public String getHomeOwnership() { return homeOwnership; }
    public void setHomeOwnership(String homeOwnership) { this.homeOwnership = homeOwnership; }

    public String getBankruptcyFlag() { return bankruptcyFlag; }
    public void setBankruptcyFlag(String bankruptcyFlag) { this.bankruptcyFlag = bankruptcyFlag; }

    public Date getBankruptcyDate() { return bankruptcyDate; }
    public void setBankruptcyDate(Date bankruptcyDate) { this.bankruptcyDate = bankruptcyDate; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public String getEmployerPhone() { return employerPhone; }
    public void setEmployerPhone(String employerPhone) { this.employerPhone = employerPhone; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public int getYearsEmployed() { return yearsEmployed; }
    public void setYearsEmployed(int yearsEmployed) { this.yearsEmployed = yearsEmployed; }

    public int getMonthsEmployed() { return monthsEmployed; }
    public void setMonthsEmployed(int monthsEmployed) { this.monthsEmployed = monthsEmployed; }

    public CustomerVO getCustomer() { return customer; }
    public void setCustomer(CustomerVO customer) { this.customer = customer; }

    public String toString() {
        return "ApplicationVO[applicationId=" + applicationId +
               ", customerId=" + customerId +
               ", cardTypeId=" + cardTypeId +
               ", status=" + statusCode + "]";
    }
}
