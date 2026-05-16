<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!--
    personal_info.jsp - Step 1: Personal & Contact Information
    Author: FirstBank Web Team  Date: 2001-06-15
-->
<html:html locale="true">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FirstBank - Credit Card Application - Step 1 of 2</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <script language="JavaScript" type="text/javascript">
    <!--
        // Basic client-side validation (1990s/early 2000s style)
        function validateForm(frm) {
            var errors = '';

            if (frm.firstName.value == '') {
                errors += '- First Name is required.\n';
            }
            if (frm.lastName.value == '') {
                errors += '- Last Name is required.\n';
            }
            if (frm.ssn.value == '') {
                errors += '- Social Security Number is required.\n';
            } else {
                // Basic SSN format check
                var ssnPattern = /^\d{3}-\d{2}-\d{4}$/;
                if (!ssnPattern.test(frm.ssn.value)) {
                    errors += '- SSN must be in format: NNN-NN-NNNN\n';
                }
            }
            if (frm.ssn.value != frm.ssnConfirm.value) {
                errors += '- Social Security Numbers do not match.\n';
            }
            if (frm.dateOfBirth.value == '') {
                errors += '- Date of Birth is required.\n';
            }
            if (frm.emailAddress.value == '' && frm.phoneHome.value == '') {
                errors += '- Please enter at least an email address or home phone.\n';
            }
            if (frm.addressLine1.value == '') {
                errors += '- Street Address is required.\n';
            }
            if (frm.city.value == '') {
                errors += '- City is required.\n';
            }
            if (frm.zipCode.value == '') {
                errors += '- ZIP Code is required.\n';
            }
            if (!frm.agreeToTerms.checked) {
                errors += '- You must agree to the Terms and Conditions to continue.\n';
            }

            if (errors != '') {
                alert('Please correct the following errors:\n\n' + errors);
                return false;
            }
            return true;
        }

        // Format SSN as applicant types (NNN-NN-NNNN)
        function formatSSN(field) {
            var val = field.value.replace(/\D/g, '');
            if (val.length >= 4 && val.length <= 5) {
                field.value = val.substr(0,3) + '-' + val.substr(3);
            } else if (val.length > 5) {
                field.value = val.substr(0,3) + '-' + val.substr(3,2) + '-' + val.substr(5,4);
            }
        }
    // -->
    </script>
</head>
<body bgcolor="#FFFFFF" text="#000000">

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
            <font face="Arial" size="2" color="#003366">
                <b>Credit Card Application</b>
            </font>
        </td>
        <td align="right">
            <table border="0" cellspacing="2" cellpadding="4">
                <tr>
                    <td bgcolor="#003366" align="center">
                        <font face="Arial" size="1" color="#FFFFFF"><b>Step 1</b><br>Personal Info</font>
                    </td>
                    <td bgcolor="#CCCCCC" align="center">
                        <font face="Arial" size="1" color="#666666">Step 2<br>Financial Info</font>
                    </td>
                    <td bgcolor="#CCCCCC" align="center">
                        <font face="Arial" size="1" color="#666666">Step 3<br>Confirmation</font>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<!-- ERROR MESSAGES (Struts) -->
<html:errors/>

<!-- MAIN FORM -->
<html:form action="/personalInfo" method="post" onsubmit="return validateForm(this)">

<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td bgcolor="#FFFFFF" valign="top">
            <table width="100%" border="0" cellspacing="5" cellpadding="5">

                <!-- SECTION: PERSONAL INFORMATION -->
                <tr bgcolor="#003366">
                    <td colspan="4">
                        <font face="Arial" size="2" color="#FFFFFF">
                            <b>&nbsp;Section 1 - Personal Information</b>
                        </font>
                    </td>
                </tr>

                <tr bgcolor="#F4F8FF">
                    <td width="25%"><font face="Arial" size="2">First Name: <font color="#CC0000">*</font></font></td>
                    <td width="25%"><html:text property="firstName" size="20" maxlength="50" styleClass="inputField"/></td>
                    <td width="25%"><font face="Arial" size="2">Middle Name:</font></td>
                    <td width="25%"><html:text property="middleName" size="15" maxlength="50" styleClass="inputField"/></td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td><font face="Arial" size="2">Last Name: <font color="#CC0000">*</font></font></td>
                    <td colspan="3"><html:text property="lastName" size="30" maxlength="50" styleClass="inputField"/></td>
                </tr>

                <tr bgcolor="#F4F8FF">
                    <td><font face="Arial" size="2">Date of Birth: <font color="#CC0000">*</font></font></td>
                    <td>
                        <html:text property="dateOfBirth" size="12" maxlength="10" styleClass="inputField"/>
                        <font face="Arial" size="1" color="#666666">(MM/DD/YYYY)</font>
                    </td>
                    <td><font face="Arial" size="2">Gender: <font color="#CC0000">*</font></font></td>
                    <td>
                        <html:select property="gender" styleClass="inputField">
                            <html:option value="">-- Select --</html:option>
                            <html:option value="M">Male</html:option>
                            <html:option value="F">Female</html:option>
                            <html:option value="O">Other / Prefer not to say</html:option>
                        </html:select>
                    </td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td>
                        <font face="Arial" size="2">
                            Social Security #: <font color="#CC0000">*</font>
                        </font>
                    </td>
                    <td>
                        <html:text property="ssn" size="12" maxlength="11"
                                   styleClass="inputField"
                                   onkeyup="formatSSN(this)"
                                   style="font-family: Courier New; letter-spacing:2px;"/>
                        <font face="Arial" size="1" color="#666666">(NNN-NN-NNNN)</font>
                    </td>
                    <td><font face="Arial" size="2">Confirm SSN: <font color="#CC0000">*</font></font></td>
                    <td>
                        <html:password property="ssnConfirm" size="12" maxlength="11"
                                       styleClass="inputField"
                                       style="font-family: Courier New; letter-spacing:2px;"/>
                    </td>
                </tr>

                <!-- SECTION: CONTACT INFORMATION -->
                <tr bgcolor="#003366">
                    <td colspan="4">
                        <font face="Arial" size="2" color="#FFFFFF">
                            <b>&nbsp;Section 2 - Contact Information</b>
                        </font>
                    </td>
                </tr>

                <tr bgcolor="#F4F8FF">
                    <td><font face="Arial" size="2">Email Address: <font color="#CC0000">*</font></font></td>
                    <td><html:text property="emailAddress" size="25" maxlength="100" styleClass="inputField"/></td>
                    <td><font face="Arial" size="2">Confirm Email:</font></td>
                    <td><html:text property="emailConfirm" size="25" maxlength="100" styleClass="inputField"/></td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td><font face="Arial" size="2">Home Phone:</font></td>
                    <td><html:text property="phoneHome" size="15" maxlength="15" styleClass="inputField"/></td>
                    <td><font face="Arial" size="2">Work Phone:</font></td>
                    <td><html:text property="phoneWork" size="15" maxlength="15" styleClass="inputField"/></td>
                </tr>

                <!-- SECTION: ADDRESS -->
                <tr bgcolor="#003366">
                    <td colspan="4">
                        <font face="Arial" size="2" color="#FFFFFF">
                            <b>&nbsp;Section 3 - Residential Address</b>
                        </font>
                    </td>
                </tr>

                <tr bgcolor="#F4F8FF">
                    <td><font face="Arial" size="2">Street Address: <font color="#CC0000">*</font></font></td>
                    <td colspan="3"><html:text property="addressLine1" size="40" maxlength="100" styleClass="inputField"/></td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td><font face="Arial" size="2">Apt / Suite:</font></td>
                    <td colspan="3"><html:text property="addressLine2" size="30" maxlength="100" styleClass="inputField"/></td>
                </tr>

                <tr bgcolor="#F4F8FF">
                    <td><font face="Arial" size="2">City: <font color="#CC0000">*</font></font></td>
                    <td><html:text property="city" size="25" maxlength="50" styleClass="inputField"/></td>
                    <td><font face="Arial" size="2">State: <font color="#CC0000">*</font></font></td>
                    <td>
                        <html:select property="stateCode" styleClass="inputField">
                            <html:option value="">-- State --</html:option>
                            <html:option value="AL">Alabama</html:option>
                            <html:option value="AK">Alaska</html:option>
                            <html:option value="AZ">Arizona</html:option>
                            <html:option value="AR">Arkansas</html:option>
                            <html:option value="CA">California</html:option>
                            <html:option value="CO">Colorado</html:option>
                            <html:option value="CT">Connecticut</html:option>
                            <html:option value="DE">Delaware</html:option>
                            <html:option value="FL">Florida</html:option>
                            <html:option value="GA">Georgia</html:option>
                            <html:option value="HI">Hawaii</html:option>
                            <html:option value="ID">Idaho</html:option>
                            <html:option value="IL">Illinois</html:option>
                            <html:option value="IN">Indiana</html:option>
                            <html:option value="IA">Iowa</html:option>
                            <html:option value="KS">Kansas</html:option>
                            <html:option value="KY">Kentucky</html:option>
                            <html:option value="LA">Louisiana</html:option>
                            <html:option value="ME">Maine</html:option>
                            <html:option value="MD">Maryland</html:option>
                            <html:option value="MA">Massachusetts</html:option>
                            <html:option value="MI">Michigan</html:option>
                            <html:option value="MN">Minnesota</html:option>
                            <html:option value="MS">Mississippi</html:option>
                            <html:option value="MO">Missouri</html:option>
                            <html:option value="MT">Montana</html:option>
                            <html:option value="NE">Nebraska</html:option>
                            <html:option value="NV">Nevada</html:option>
                            <html:option value="NH">New Hampshire</html:option>
                            <html:option value="NJ">New Jersey</html:option>
                            <html:option value="NM">New Mexico</html:option>
                            <html:option value="NY">New York</html:option>
                            <html:option value="NC">North Carolina</html:option>
                            <html:option value="ND">North Dakota</html:option>
                            <html:option value="OH">Ohio</html:option>
                            <html:option value="OK">Oklahoma</html:option>
                            <html:option value="OR">Oregon</html:option>
                            <html:option value="PA">Pennsylvania</html:option>
                            <html:option value="RI">Rhode Island</html:option>
                            <html:option value="SC">South Carolina</html:option>
                            <html:option value="SD">South Dakota</html:option>
                            <html:option value="TN">Tennessee</html:option>
                            <html:option value="TX">Texas</html:option>
                            <html:option value="UT">Utah</html:option>
                            <html:option value="VT">Vermont</html:option>
                            <html:option value="VA">Virginia</html:option>
                            <html:option value="WA">Washington</html:option>
                            <html:option value="WV">West Virginia</html:option>
                            <html:option value="WI">Wisconsin</html:option>
                            <html:option value="WY">Wyoming</html:option>
                        </html:select>
                    </td>
                </tr>

                <tr bgcolor="#FFFFFF">
                    <td><font face="Arial" size="2">ZIP Code: <font color="#CC0000">*</font></font></td>
                    <td>
                        <html:text property="zipCode" size="10" maxlength="10" styleClass="inputField"/>
                        <font face="Arial" size="1" color="#666666">(NNNNN or NNNNN-NNNN)</font>
                    </td>
                    <td colspan="2"></td>
                </tr>

                <!-- TERMS AND CONDITIONS -->
                <tr bgcolor="#FFFFCC">
                    <td colspan="4">
                        <table border="1" width="100%" cellspacing="0" cellpadding="5" bordercolor="#CCCCCC">
                            <tr>
                                <td valign="top" width="20">
                                    <html:checkbox property="agreeToTerms" value="Y"/>
                                </td>
                                <td>
                                    <font face="Arial" size="1" color="#333333">
                                        I agree to the <a href="/creditcard/pages/terms.jsp" target="_blank">Terms and Conditions</a>
                                        and <a href="/creditcard/pages/privacy.jsp" target="_blank">Privacy Policy</a>
                                        of FirstBank's Online Credit Card Application. I certify that all information
                                        provided is true and accurate to the best of my knowledge. I authorize FirstBank
                                        to obtain a consumer credit report in connection with this application.
                                        <font color="#CC0000">*</font>
                                    </font>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <!-- BUTTONS -->
                <tr bgcolor="#E8EEF4">
                    <td colspan="2">
                        <font face="Arial" size="1" color="#CC0000"><b>* Required Fields</b></font>
                    </td>
                    <td colspan="2" align="right">
                        <input type="reset" value="Clear Form"
                               style="font-family:Arial; font-size:11px; background-color:#CCCCCC;">
                        &nbsp;&nbsp;
                        <input type="submit" value="Continue to Step 2 >>"
                               style="font-family:Arial; font-size:11px; font-weight:bold;
                                      background-color:#003366; color:#FFFFFF;
                                      border:2px outset #336699;
                                      padding:4px 8px; cursor:pointer;">
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
