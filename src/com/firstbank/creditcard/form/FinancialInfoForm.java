package com.firstbank.creditcard.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * FinancialInfoForm - Struts ActionForm for Step 2 of the application.
 * Collects financial and employment information from the applicant.
 *
 * @author  FirstBank IT Department - Web Team
 * @version 1.0
 * @since   Struts 1.1 / J2EE 1.3
 */
public class FinancialInfoForm extends ActionForm {

    private static final long serialVersionUID = -1L;

    // Card Selection
    private String cardTypeId;
    private String requestedLimit;

    // Financial Information
    private String annualIncome;
    private String otherIncome;
    private String monthlyRent;
    private String mortgageBalance;
    private String totalDebt;
    private String checkingBalance;
    private String savingsBalance;
    private String homeOwnership;
    private String bankruptcyFlag;

    // Employment Information
    private String employmentType;
    private String employerName;
    private String employerPhone;
    private String jobTitle;
    private String yearsEmployed;
    private String monthsEmployed;

    // ----------------------------------------------------------
    // Struts Validation Method
    // ----------------------------------------------------------

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        // Validate Card Type
        if (cardTypeId == null || cardTypeId.trim().length() == 0) {
            errors.add("cardTypeId", new ActionError("error.cardType.required"));
        }

        // Validate Annual Income
        if (annualIncome == null || annualIncome.trim().length() == 0) {
            errors.add("annualIncome", new ActionError("error.annualIncome.required"));
        } else {
            try {
                double income = Double.parseDouble(annualIncome.replaceAll(",", ""));
                if (income < 0) {
                    errors.add("annualIncome", new ActionError("error.annualIncome.negative"));
                }
            } catch (NumberFormatException nfe) {
                errors.add("annualIncome", new ActionError("error.annualIncome.format"));
            }
        }

        // Validate Total Debt (if provided)
        if (totalDebt != null && totalDebt.trim().length() > 0) {
            try {
                double debt = Double.parseDouble(totalDebt.replaceAll(",", ""));
                if (debt < 0) {
                    errors.add("totalDebt", new ActionError("error.totalDebt.negative"));
                }
            } catch (NumberFormatException nfe) {
                errors.add("totalDebt", new ActionError("error.totalDebt.format"));
            }
        }

        // Validate Home Ownership
        if (homeOwnership == null || homeOwnership.trim().length() == 0) {
            errors.add("homeOwnership", new ActionError("error.homeOwnership.required"));
        }

        // Validate Employment Type
        if (employmentType == null || employmentType.trim().length() == 0) {
            errors.add("employmentType", new ActionError("error.employmentType.required"));
        }

        // Employer Name required if employed
        if ("FULL_TIME".equals(employmentType) || "PART_TIME".equals(employmentType)) {
            if (employerName == null || employerName.trim().length() == 0) {
                errors.add("employerName", new ActionError("error.employerName.required"));
            }
        }

        return errors;
    }

    // ----------------------------------------------------------
    // Getters and Setters
    // ----------------------------------------------------------

    public String getCardTypeId() { return cardTypeId; }
    public void setCardTypeId(String cardTypeId) { this.cardTypeId = cardTypeId; }

    public String getRequestedLimit() { return requestedLimit; }
    public void setRequestedLimit(String requestedLimit) { this.requestedLimit = requestedLimit; }

    public String getAnnualIncome() { return annualIncome; }
    public void setAnnualIncome(String annualIncome) { this.annualIncome = annualIncome; }

    public String getOtherIncome() { return otherIncome; }
    public void setOtherIncome(String otherIncome) { this.otherIncome = otherIncome; }

    public String getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(String monthlyRent) { this.monthlyRent = monthlyRent; }

    public String getMortgageBalance() { return mortgageBalance; }
    public void setMortgageBalance(String mortgageBalance) { this.mortgageBalance = mortgageBalance; }

    public String getTotalDebt() { return totalDebt; }
    public void setTotalDebt(String totalDebt) { this.totalDebt = totalDebt; }

    public String getCheckingBalance() { return checkingBalance; }
    public void setCheckingBalance(String checkingBalance) { this.checkingBalance = checkingBalance; }

    public String getSavingsBalance() { return savingsBalance; }
    public void setSavingsBalance(String savingsBalance) { this.savingsBalance = savingsBalance; }

    public String getHomeOwnership() { return homeOwnership; }
    public void setHomeOwnership(String homeOwnership) { this.homeOwnership = homeOwnership; }

    public String getBankruptcyFlag() { return bankruptcyFlag; }
    public void setBankruptcyFlag(String bankruptcyFlag) { this.bankruptcyFlag = bankruptcyFlag; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public String getEmployerPhone() { return employerPhone; }
    public void setEmployerPhone(String employerPhone) { this.employerPhone = employerPhone; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getYearsEmployed() { return yearsEmployed; }
    public void setYearsEmployed(String yearsEmployed) { this.yearsEmployed = yearsEmployed; }

    public String getMonthsEmployed() { return monthsEmployed; }
    public void setMonthsEmployed(String monthsEmployed) { this.monthsEmployed = monthsEmployed; }
}
