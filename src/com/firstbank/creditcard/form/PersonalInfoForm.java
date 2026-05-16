package com.firstbank.creditcard.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * PersonalInfoForm - Struts ActionForm for Step 1 of the application.
 * Collects applicant personal and contact information.
 * Validation is performed in the validate() method per Struts convention.
 *
 * @author  FirstBank IT Department - Web Team
 * @version 1.0
 * @since   Struts 1.1 / J2EE 1.3
 */
public class PersonalInfoForm extends ActionForm {

    private static final long serialVersionUID = -1234567890123456789L;

    // Personal Information
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth;     // String representation: MM/DD/YYYY
    private String gender;
    private String ssn;
    private String ssnConfirm;

    // Contact Information
    private String emailAddress;
    private String emailConfirm;
    private String phoneHome;
    private String phoneWork;
    private String phoneMobile;

    // Address Information
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String stateCode;
    private String zipCode;

    // Terms & Conditions
    private String agreeToTerms;

    // ----------------------------------------------------------
    // Struts Validation Method
    // ----------------------------------------------------------

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        // Validate First Name
        if (firstName == null || firstName.trim().length() == 0) {
            errors.add("firstName", new ActionError("error.firstName.required"));
        } else if (firstName.trim().length() > 50) {
            errors.add("firstName", new ActionError("error.firstName.tooLong"));
        }

        // Validate Last Name
        if (lastName == null || lastName.trim().length() == 0) {
            errors.add("lastName", new ActionError("error.lastName.required"));
        } else if (lastName.trim().length() > 50) {
            errors.add("lastName", new ActionError("error.lastName.tooLong"));
        }

        // Validate Date of Birth
        if (dateOfBirth == null || dateOfBirth.trim().length() == 0) {
            errors.add("dateOfBirth", new ActionError("error.dateOfBirth.required"));
        } else {
            // Validate MM/DD/YYYY format with basic regex check
            if (!dateOfBirth.matches("\\d{2}/\\d{2}/\\d{4}")) {
                errors.add("dateOfBirth", new ActionError("error.dateOfBirth.format"));
            }
        }

        // Validate Gender
        if (gender == null || gender.trim().length() == 0) {
            errors.add("gender", new ActionError("error.gender.required"));
        }

        // Validate SSN
        if (ssn == null || ssn.trim().length() == 0) {
            errors.add("ssn", new ActionError("error.ssn.required"));
        } else if (!ssn.matches("\\d{3}-\\d{2}-\\d{4}")) {
            errors.add("ssn", new ActionError("error.ssn.format"));
        }

        // Validate SSN confirmation
        if (ssnConfirm == null || !ssnConfirm.equals(ssn)) {
            errors.add("ssnConfirm", new ActionError("error.ssnConfirm.mismatch"));
        }

        // Validate Email
        if (emailAddress == null || emailAddress.trim().length() == 0) {
            errors.add("emailAddress", new ActionError("error.email.required"));
        } else if (!emailAddress.matches("[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,4}")) {
            errors.add("emailAddress", new ActionError("error.email.format"));
        }

        // Validate Email confirmation
        if (emailConfirm == null || !emailConfirm.equals(emailAddress)) {
            errors.add("emailConfirm", new ActionError("error.emailConfirm.mismatch"));
        }

        // Validate Phone (at least one required)
        if ((phoneHome == null || phoneHome.trim().length() == 0) &&
            (phoneWork == null || phoneWork.trim().length() == 0)) {
            errors.add("phoneHome", new ActionError("error.phone.required"));
        }

        // Validate Address
        if (addressLine1 == null || addressLine1.trim().length() == 0) {
            errors.add("addressLine1", new ActionError("error.addressLine1.required"));
        }

        if (city == null || city.trim().length() == 0) {
            errors.add("city", new ActionError("error.city.required"));
        }

        if (stateCode == null || stateCode.trim().length() == 0) {
            errors.add("stateCode", new ActionError("error.stateCode.required"));
        }

        if (zipCode == null || zipCode.trim().length() == 0) {
            errors.add("zipCode", new ActionError("error.zipCode.required"));
        } else if (!zipCode.matches("\\d{5}(-\\d{4})?")) {
            errors.add("zipCode", new ActionError("error.zipCode.format"));
        }

        // Validate agreement to terms
        if (!"Y".equals(agreeToTerms)) {
            errors.add("agreeToTerms", new ActionError("error.terms.required"));
        }

        return errors;
    }

    // Reset form fields (called before each request population)
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        agreeToTerms = null;  // Reset checkbox
    }

    // ----------------------------------------------------------
    // Getters and Setters
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

    public String getSsnConfirm() { return ssnConfirm; }
    public void setSsnConfirm(String ssnConfirm) { this.ssnConfirm = ssnConfirm; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getEmailConfirm() { return emailConfirm; }
    public void setEmailConfirm(String emailConfirm) { this.emailConfirm = emailConfirm; }

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

    public String getAgreeToTerms() { return agreeToTerms; }
    public void setAgreeToTerms(String agreeToTerms) { this.agreeToTerms = agreeToTerms; }
}
