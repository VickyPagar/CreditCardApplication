package com.firstbank.creditcard.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * CustomerVO - Value Object (Transfer Object) for Customer data.
 * Used to transfer customer information between application tiers.
 *
 * @author  FirstBank IT Department
 * @version 1.0
 * @since   J2EE 1.3
 */
public class CustomerVO implements Serializable {

    private static final long serialVersionUID = -4120234561234567890L;

    private long   customerId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date   dateOfBirth;
    private String gender;
    private String ssn;
    private String emailAddress;
    private String phoneHome;
    private String phoneWork;
    private String phoneMobile;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String stateCode;
    private String zipCode;
    private String countryCode;
    private String customerStatus;
    private Date   createdDate;

    // ----------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------

    public CustomerVO() {
        this.countryCode    = "US";
        this.customerStatus = "ACTIVE";
    }

    // ----------------------------------------------------------
    // Business Methods
    // ----------------------------------------------------------

    /**
     * Returns the full name of the customer.
     */
    public String getFullName() {
        StringBuffer sb = new StringBuffer();
        sb.append(firstName != null ? firstName : "");
        if (middleName != null && middleName.trim().length() > 0) {
            sb.append(" ").append(middleName);
        }
        sb.append(" ").append(lastName != null ? lastName : "");
        return sb.toString().trim();
    }

    /**
     * Returns masked SSN for display (e.g. XXX-XX-1234).
     */
    public String getMaskedSsn() {
        if (ssn != null && ssn.length() == 11) {
            return "XXX-XX-" + ssn.substring(7);
        }
        return "XXX-XX-XXXX";
    }

    // ----------------------------------------------------------
    // Generated Getters and Setters
    // ----------------------------------------------------------

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

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

    public String getPhoneMobile() { return phoneMobile; }
    public void setPhoneMobile(String phoneMobile) { this.phoneMobile = phoneMobile; }

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

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getCustomerStatus() { return customerStatus; }
    public void setCustomerStatus(String customerStatus) { this.customerStatus = customerStatus; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public String toString() {
        return "CustomerVO[customerId=" + customerId +
               ", name=" + getFullName() +
               ", ssn=" + getMaskedSsn() + "]";
    }
}
