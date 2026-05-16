<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!--
    status_inquiry.jsp - Application Status Inquiry Form
    Author: FirstBank Web Team  Date: 2001-06-15
-->
<html:html locale="true">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FirstBank - Check Application Status</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <script language="JavaScript">
    <!--
        function validateStatus(frm) {
            if (frm.applicationId.value == '' || isNaN(frm.applicationId.value)) {
                alert('Please enter a valid numeric Application ID.');
                frm.applicationId.focus();
                return false;
            }
            return true;
        }
    // -->
    </script>
</head>
<body bgcolor="#FFFFFF">

<!-- HEADER -->
<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr><td bgcolor="#003366" height="60">
        <table width="100%" cellpadding="5" border="0">
            <tr>
                <td><font face="Arial" size="4" color="#FFFFFF"><b>FirstBank</b></font></td>
                <td align="right"><font face="Arial" size="1" color="#CCCCCC">
                    <a href="/creditcard/welcome.do" style="color:#CCCCCC;">Home</a> |
                    <a href="/creditcard/showPersonalInfo.do" style="color:#CCCCCC;">Apply Now</a>
                </font></td>
            </tr>
        </table>
    </td></tr>
    <tr><td bgcolor="#FFCC00" height="3"></td></tr>
</table>

<!-- CONTENT -->
<table width="760" border="0" cellspacing="10" cellpadding="10" align="center">
    <tr>
        <td>
            <font face="Arial" size="3" color="#003366"><b>Check Your Application Status</b></font>
            <hr size="1" color="#336699">
            <font face="Arial" size="2">
                Enter your <b>Application Number</b> below to view the current status of your
                credit card application. Your Application Number was provided when you
                submitted your application.
            </font>
        </td>
    </tr>
    <tr>
        <td>
            <html:errors/>
            <html:form action="/statusInquiry" method="post" onsubmit="return validateStatus(this)">
            <table border="1" cellspacing="0" cellpadding="8" width="400" bordercolor="#CCCCCC" align="center">
                <tr bgcolor="#003366">
                    <td colspan="2">
                        <font face="Arial" size="2" color="#FFFFFF"><b>Application Status Inquiry</b></font>
                    </td>
                </tr>
                <tr bgcolor="#F4F8FF">
                    <td><font face="Arial" size="2">Application Number:</font></td>
                    <td>
                        <html:text property="applicationId" size="15" maxlength="10"
                                   styleClass="inputField" style="font-size:14px; font-weight:bold;"/>
                    </td>
                </tr>
                <tr bgcolor="#FFFFFF">
                    <td colspan="2" align="center">
                        <br>
                        <input type="submit" value="Check Status"
                               style="font-family:Arial; font-size:11px; font-weight:bold;
                                      background-color:#003366; color:#FFFFFF;
                                      border:2px outset #336699;
                                      padding:5px 15px; cursor:pointer;">
                        &nbsp;
                        <input type="reset" value="Clear"
                               style="font-family:Arial; font-size:11px; background-color:#CCCCCC;
                                      padding:5px 10px; cursor:pointer;">
                        <br><br>
                    </td>
                </tr>
            </table>
            </html:form>
        </td>
    </tr>
</table>

<!-- FOOTER -->
<table width="760" border="0" cellspacing="0" cellpadding="5" align="center">
    <tr><td bgcolor="#FFCC00" height="3"></td></tr>
    <tr bgcolor="#003366">
        <td align="center">
            <font face="Arial" size="1" color="#CCCCCC">
                &copy; 2001 FirstBank N.A. All Rights Reserved. Member FDIC.
            </font>
        </td>
    </tr>
</table>

</body>
</html:html>
