package com.firstbank.creditcard.service;

import java.io.Serializable;

/**
 * CreditCardApplicationRequest - SOAP Request bean.
 * Data carrier for inbound SOAP application submissions.
 * Must be serializable and follow JavaBean conventions for AXIS marshaling.
 *
 * @author  FirstBank IT Department - Integration Team
 * @version 1.0
 */
public class CreditCardApplicationRequest implements Serializable {

    private static final long serialVersionUID = 3456789012345678901L;

    // Applicant Personal Info
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth;     // ISO 8601: YYYY-MM-DD
    private String gender;
    private String ssn;
    private String emailAddress;
    private String phoneHome;
    private String phoneWork;

    // Address
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String stateCode;
    private String zipCode;

    // Card Selection
    private int    cardTypeId;
    private double requestedLimit;

    // Financial Info
    private double annualIncome;
    private double otherIncome;
    private double monthlyRent;
    private double totalDebt;
    private String homeOwnership;
    private String bankruptcyFlag;

    // Employment Info
    private String employmentType;
    private String employerName;
    private String jobTitle;
    private int    yearsEmployed;

    // Channel / source info
    private String channelCode;
    private String branchCode;
    private String agentId;

    // ----------------------------------------------------------
    // Getters and Setters (required by Apache AXIS for serialization)
    // ----------------------------------------------------------

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getPhoneHome() { return phoneHome; }
    public void setPhoneHome(String phoneHome) { this.phoneHome = phoneHome; }

    public String getPhoneWork() { return phoneWork; }
    public void setPhoneWork(String phoneWork) { this.phoneWork = phoneWork; }

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public int getCardTypeId() { return cardTypeId; }
    public void setCardTypeId(int cardTypeId) { this.cardTypeId = cardTypeId; }

    public double getRequestedLimit() { return requestedLimit; }
    public void setRequestedLimit(double requestedLimit) { this.requestedLimit = requestedLimit; }

    public double getAnnualIncome() { return annualIncome; }
    public void setAnnualIncome(double annualIncome) { this.annualIncome = annualIncome; }

    public double getOtherIncome() { return otherIncome; }
    public void setOtherIncome(double otherIncome) { this.otherIncome = otherIncome; }

    public double getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(double monthlyRent) { this.monthlyRent = monthlyRent; }

    public double getTotalDebt() { return totalDebt; }
    public void setTotalDebt(double totalDebt) { this.totalDebt = totalDebt; }

    public String getHomeOwnership() { return homeOwnership; }
    public void setHomeOwnership(String homeOwnership) { this.homeOwnership = homeOwnership; }

    public String getBankruptcyFlag() { return bankruptcyFlag; }
    public void setBankruptcyFlag(String bankruptcyFlag) { this.bankruptcyFlag = bankruptcyFlag; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public int getYearsEmployed() { return yearsEmployed; }
    public void setYearsEmployed(int yearsEmployed) { this.yearsEmployed = yearsEmployed; }

    public String getChannelCode() { return channelCode; }
    public void setChannelCode(String channelCode) { this.channelCode = channelCode; }

    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }
}
