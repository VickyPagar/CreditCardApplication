<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"%>
<%@ page import="java.util.List"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!--
    financial_info.jsp - Step 2: Financial & Employment Information
    Author: FirstBank Web Team  Date: 2001-06-15
-->
<html:html locale="true">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FirstBank - Credit Card Application - Step 2 of 2</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <script language="JavaScript" type="text/javascript">
    <!--
        function validateFinancialForm(frm) {
            var errors = '';

            if (frm.cardTypeId.value == '' || frm.cardTypeId.value == '0') {
                errors += '- Please select a card type.\n';
            }
            if (frm.annualIncome.value == '') {
                errors += '- Annual Income is required.\n';
            } else {
                var income = parseFloat(frm.annualIncome.value.replace(/,/g,''));
                if (isNaN(income) || income < 0) {
                    errors += '- Annual Income must be a valid positive number.\n';
                }
            }
            if (frm.homeOwnership.value == '') {
                errors += '- Home Ownership status is required.\n';
            }
            if (frm.employmentType.value == '') {
                errors += '- Employment Type is required.\n';
            }

            if (errors != '') {
                alert('Please correct the following errors:\n\n' + errors);
                return false;
            }
            return true;
        }

        // Show/hide employer fields based on employment type
        function toggleEmployerFields() {
            var empType = document.getElementById('employmentType').value;
            var showEmp = (empType == 'FULL_TIME' || empType == 'PART_TIME' || empType == 'SELF_EMPLOYED');
            document.getElementById('employerSection').style.display = showEmp ? '' : 'none';
        }
    // -->
    </script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="toggleEmployerFields()">

<!-- HEADER -->
<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td bgcolor="#003366">
            <table width="100%" border="0" cellspacing="0" cellpadding="5">
                <tr>
                    <td><font face="Arial" size="4" color="#FFFFFF"><b>FirstBank</b></font><br>
                        <font face="Arial" size="1" color="#CCCCCC">Serving You Since 1887</font>
                    </td>
                    <td align="right">
                        <font face="Arial" size="1" color="#FFCC00">
                            <b>Secure Application</b>&nbsp;
                            <img src="../images/lock_small.gif" width="12" height="14" alt="Secure" border="0">
                        </font>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr><td bgcolor="#FFCC00" height="3"></td></tr>
</table>

<!-- PROGRESS BAR -->
<table width="760" border="0" cellspacing="0" cellpadding="5" align="center" bgcolor="#E8EEF4">
    <tr>
        <td>
            <font face="Arial" size="2" color="#003366"><b>Credit Card Application - Step 2 of 2</b></font>
        </td>
        <td align="right">
            <table border="0" cellspacing="2" cellpadding="4">
                <tr>
                    <td bgcolor="#CCCCCC" align="center">
                        <font face="Arial" size="1" color="#666666">Step 1<br>Personal Info &#10003;</font>
                    </td>
                    <td bgcolor="#003366" align="center">
                        <font face="Arial" size="1" color="#FFFFFF"><b>Step 2</b><br>Financial Info</font>
                    </td>
                    <td bgcolor="#CCCCCC" align="center">
                        <font face="Arial" size="1" color="#666666">Step 3<br>Confirmation</font>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<!-- ERROR MESSAGES -->
<html:errors/>

<html:form action="/financialInfo" method="post" onsubmit="return validateFinancialForm(this)">

<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td bgcolor="#FFFFFF" valign="top">
            <table width="100%" border="0" cellspacing="5" cellpadding="5">

                <!-- SECTION: CARD SELECTION -->
                <tr bgcolor="#003366">
                    <td colspan="4">
                        <font face="Arial" size="2" color="#FFFFFF"><b>&nbsp;Section 1 - Card Selection</b></font>
                    </td>
                </tr>

                <tr bgcolor="#F4F8FF">
                    <td width="30%"><font face="Arial" size="2">Card Type: <font color="#CC0000">*</font></font></td>
                    <td colspan="3">
                        <html:select property="cardTypeId" styleId="cardTypeId" styleClass="inputField">
                            <html:option value="">-- Please Select a Card Type --</html:option>
                            <html:option value="1">FirstBank Classic Visa (APR 19.99% | No Annual Fee | Min. Income: $18,000)</html:option>
                            <html:option value="2">FirstBank Gold Visa (APR 17.99% | Annual Fee: $75 | Min. Income: $35,000)</html:option>
                            <html:option value="3">FirstBank Platinum MasterCard (APR 15.99% | Annual Fee: $125 | Min. Income: $60,000)</html:option>
                            <html:option value="4">FirstBank Titanium Card (APR 13.99% | Annual Fee: $250 | Min. Income: $100,000)</html:option>
                            <html:option value="5">FirstBank Student Visa (APR 21.99% | No Annual Fee | No Min. Income)</html:option>
                            <html:option value="6">FirstBank Business Card (APR 14.99% | Annual Fee: $199 | Min. Income: $80,000)</html:option>
                        </html:select>
                    </td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td><font face="Arial" size="2">Requested Credit Limit:</font></td>
                    <td>
                        $ <html:text property="requestedLimit" size="12" maxlength="12" styleClass="inputField"/>
                        <font face="Arial" size="1" color="#666666">(optional - subject to approval)</font>
                    </td>
                    <td colspan="2"></td>
                </tr>

                <!-- SECTION: FINANCIAL INFORMATION -->
                <tr bgcolor="#003366">
                    <td colspan="4">
                        <font face="Arial" size="2" color="#FFFFFF"><b>&nbsp;Section 2 - Financial Information</b></font>
                    </td>
                </tr>

                <tr bgcolor="#F4F8FF">
                    <td><font face="Arial" size="2">Annual Income: <font color="#CC0000">*</font></font></td>
                    <td>
                        $ <html:text property="annualIncome" size="12" maxlength="14" styleClass="inputField"/>
                        <font face="Arial" size="1" color="#666666">(before taxes)</font>
                    </td>
                    <td><font face="Arial" size="2">Other Income:</font></td>
                    <td>
                        $ <html:text property="otherIncome" size="12" maxlength="14" styleClass="inputField"/>
                        <font face="Arial" size="1" color="#666666">(annual)</font>
                    </td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td><font face="Arial" size="2">Monthly Rent/Mortgage:</font></td>
                    <td>
                        $ <html:text property="monthlyRent" size="12" maxlength="10" styleClass="inputField"/>
                    </td>
                    <td><font face="Arial" size="2">Total Outstanding Debt:</font></td>
                    <td>
                        $ <html:text property="totalDebt" size="12" maxlength="14" styleClass="inputField"/>
                        <font face="Arial" size="1" color="#666666">(all loans/cards)</font>
                    </td>
                </tr>

                <tr bgcolor="#F4F8FF">
                    <td><font face="Arial" size="2">Checking Account Balance:</font></td>
                    <td>
                        $ <html:text property="checkingBalance" size="12" maxlength="14" styleClass="inputField"/>
                    </td>
                    <td><font face="Arial" size="2">Savings Account Balance:</font></td>
                    <td>
                        $ <html:text property="savingsBalance" size="12" maxlength="14" styleClass="inputField"/>
                    </td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td><font face="Arial" size="2">Home Ownership: <font color="#CC0000">*</font></font></td>
                    <td>
                        <html:select property="homeOwnership" styleClass="inputField">
                            <html:option value="">-- Select --</html:option>
                            <html:option value="OWN">Own (no mortgage)</html:option>
                            <html:option value="RENT">Rent</html:option>
                            <html:option value="LIVE_WITH_PARENTS">Live with Parents/Family</html:option>
                            <html:option value="OTHER">Other</html:option>
                        </html:select>
                    </td>
                    <td><font face="Arial" size="2">Bankruptcy in past 7 yrs?</font></td>
                    <td>
                        <html:select property="bankruptcyFlag" styleClass="inputField">
                            <html:option value="N">No</html:option>
                            <html:option value="Y">Yes</html:option>
                        </html:select>
                    </td>
                </tr>

                <!-- SECTION: EMPLOYMENT -->
                <tr bgcolor="#003366">
                    <td colspan="4">
                        <font face="Arial" size="2" color="#FFFFFF"><b>&nbsp;Section 3 - Employment Information</b></font>
                    </td>
                </tr>

                <tr bgcolor="#F4F8FF">
                    <td><font face="Arial" size="2">Employment Status: <font color="#CC0000">*</font></font></td>
                    <td colspan="3">
                        <html:select property="employmentType" styleId="employmentType"
                                     styleClass="inputField" onchange="toggleEmployerFields()">
                            <html:option value="">-- Select Employment Status --</html:option>
                            <html:option value="FULL_TIME">Employed Full-Time</html:option>
                            <html:option value="PART_TIME">Employed Part-Time</html:option>
                            <html:option value="SELF_EMPLOYED">Self-Employed / Business Owner</html:option>
                            <html:option value="RETIRED">Retired</html:option>
                            <html:option value="STUDENT">Student</html:option>
                            <html:option value="UNEMPLOYED">Not Currently Employed</html:option>
                        </html:select>
                    </td>
                </tr>

                <!-- EMPLOYER DETAILS (shown/hidden by JS) -->
                <tbody id="employerSection">
                    <tr bgcolor="#FFFFFF">
                        <td><font face="Arial" size="2">Employer Name:</font></td>
                        <td colspan="3">
                            <html:text property="employerName" size="35" maxlength="100" styleClass="inputField"/>
                        </td>
                    </tr>
                    <tr bgcolor="#F4F8FF">
                        <td><font face="Arial" size="2">Job Title:</font></td>
                        <td>
                            <html:text property="jobTitle" size="25" maxlength="100" styleClass="inputField"/>
                        </td>
                        <td><font face="Arial" size="2">Employer Phone:</font></td>
                        <td>
                            <html:text property="employerPhone" size="15" maxlength="15" styleClass="inputField"/>
                        </td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                        <td><font face="Arial" size="2">Years with Employer:</font></td>
                        <td>
                            <html:text property="yearsEmployed" size="4" maxlength="3" styleClass="inputField"/>
                            &nbsp; years
                        </td>
                        <td colspan="2"></td>
                    </tr>
                </tbody>

                <!-- IMPORTANT DISCLAIMER -->
                <tr bgcolor="#FFFFCC">
                    <td colspan="4">
                        <table border="1" width="100%" cellspacing="0" cellpadding="5" bordercolor="#FFCC00">
                            <tr>
                                <td>
                                    <font face="Arial" size="1" color="#333333">
                                        <b>Important:</b> By clicking "Submit Application", I authorize FirstBank to
                                        access my credit report and share my information with credit reporting agencies.
                                        I understand that this does not guarantee credit approval.
                                        Providing false information may result in application denial and/or legal action.
                                    </font>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <!-- BUTTONS -->
                <tr bgcolor="#E8EEF4">
                    <td colspan="2">
                        <input type="button" value="<< Back to Step 1"
                               onclick="history.back()"
                               style="font-family:Arial; font-size:11px; background-color:#CCCCCC;">
                        &nbsp;
                        <font face="Arial" size="1" color="#CC0000"><b>* Required Fields</b></font>
                    </td>
                    <td colspan="2" align="right">
                        <input type="reset" value="Clear Form"
                               style="font-family:Arial; font-size:11px; background-color:#CCCCCC;">
                        &nbsp;&nbsp;
                        <input type="submit" value="Submit Application"
                               style="font-family:Arial; font-size:12px; font-weight:bold;
                                      background-color:#006600; color:#FFFFFF;
                                      border:2px outset #009900;
                                      padding:5px 10px; cursor:pointer;">
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>

</html:form>

<!-- FOOTER -->
<table width="760" border="0" cellspacing="0" cellpadding="5" align="center">
    <tr><td bgcolor="#FFCC00" height="3"></td></tr>
    <tr bgcolor="#003366">
        <td align="center">
            <font face="Arial" size="1" color="#CCCCCC">
                &copy; 2001 FirstBank N.A. All Rights Reserved. Member FDIC. Equal Housing Lender.
            </font>
        </td>
    </tr>
</table>

</body>
</html:html>
