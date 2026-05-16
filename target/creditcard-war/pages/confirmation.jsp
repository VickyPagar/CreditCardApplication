<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld"  prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!--
    confirmation.jsp - Application Submitted Confirmation Page
    Author: FirstBank Web Team  Date: 2001-06-15
-->
<html:html locale="true">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FirstBank - Application Submitted Successfully</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
</head>
<body bgcolor="#FFFFFF" text="#000000">

<!-- HEADER -->
<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td bgcolor="#003366">
            <table width="100%" border="0" cellspacing="0" cellpadding="5">
                <tr>
                    <td><font face="Arial" size="4" color="#FFFFFF"><b>FirstBank</b></font></td>
                    <td align="right">
                        <font face="Arial" size="1" color="#FFCC00"><b>Secure Application</b></font>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr><td bgcolor="#FFCC00" height="3"></td></tr>
</table>

<!-- PROGRESS BAR - COMPLETE -->
<table width="760" border="0" cellspacing="0" cellpadding="5" align="center" bgcolor="#E8EEF4">
    <tr>
        <td>
            <font face="Arial" size="2" color="#003366"><b>Application Complete</b></font>
        </td>
        <td align="right">
            <table border="0" cellspacing="2" cellpadding="4">
                <tr>
                    <td bgcolor="#CCCCCC" align="center">
                        <font face="Arial" size="1" color="#666666">Step 1 &#10003;</font>
                    </td>
                    <td bgcolor="#CCCCCC" align="center">
                        <font face="Arial" size="1" color="#666666">Step 2 &#10003;</font>
                    </td>
                    <td bgcolor="#006600" align="center">
                        <font face="Arial" size="1" color="#FFFFFF"><b>Submitted! &#10003;</b></font>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<table width="760" border="0" cellspacing="10" cellpadding="10" align="center">
    <tr>
        <td bgcolor="#EEFFEE" valign="top">
            <table width="100%" border="1" cellspacing="0" cellpadding="10" bordercolor="#009900">
                <tr bgcolor="#006600">
                    <td>
                        <font face="Arial" size="3" color="#FFFFFF">
                            <b>&#10003; &nbsp;Your Application Has Been Submitted!</b>
                        </font>
                    </td>
                </tr>
                <tr bgcolor="#EEFFEE">
                    <td>
                        <font face="Arial" size="2" color="#003300">
                            Thank you for applying for a FirstBank Credit Card. Your application has been
                            successfully received and is currently being processed.
                        </font>
                        <br><br>

                        <!-- Application ID from session -->
                        <%
                            Object appId = session.getAttribute("applicationId");
                            String applicationId = (appId != null) ? appId.toString() : "N/A";
                        %>

                        <table border="1" cellspacing="0" cellpadding="8" width="400" bordercolor="#CCCCCC">
                            <tr bgcolor="#003366">
                                <td colspan="2">
                                    <font face="Arial" size="2" color="#FFFFFF"><b>Application Details</b></font>
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#F4F8FF" width="50%">
                                    <font face="Arial" size="2"><b>Application Number:</b></font>
                                </td>
                                <td bgcolor="#FFFFFF">
                                    <font face="Arial" size="2" color="#CC0000">
                                        <b><%= applicationId %></b>
                                    </font>
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#F4F8FF">
                                    <font face="Arial" size="2"><b>Date Submitted:</b></font>
                                </td>
                                <td bgcolor="#FFFFFF">
                                    <font face="Arial" size="2">
                                        <%= new java.text.SimpleDateFormat("MMMM dd, yyyy").format(new java.util.Date()) %>
                                    </font>
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#F4F8FF">
                                    <font face="Arial" size="2"><b>Current Status:</b></font>
                                </td>
                                <td bgcolor="#FFFFFF">
                                    <font face="Arial" size="2" color="#CC6600"><b>Pending Review</b></font>
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#F4F8FF">
                                    <font face="Arial" size="2"><b>Expected Decision:</b></font>
                                </td>
                                <td bgcolor="#FFFFFF">
                                    <font face="Arial" size="2">Within 1-3 business days</font>
                                </td>
                            </tr>
                        </table>

                        <br>
                        <font face="Arial" size="2">
                            <b>What happens next?</b>
                        </font>
                        <ul>
                            <li>
                                <font face="Arial" size="2">
                                    Most applications receive an <b>instant decision</b>. However, some
                                    applications require additional review which may take <b>1-3 business days</b>.
                                </font>
                            </li>
                            <li>
                                <font face="Arial" size="2">
                                    If approved, your new credit card will be mailed to your address
                                    within <b>7-10 business days</b>.
                                </font>
                            </li>
                            <li>
                                <font face="Arial" size="2">
                                    You will receive a <b>written notice</b> by mail in all cases
                                    regardless of the decision.
                                </font>
                            </li>
                            <li>
                                <font face="Arial" size="2">
                                    You can check your application status online using your
                                    <b>Application Number: <%= applicationId %></b>
                                </font>
                            </li>
                        </ul>

                        <br>
                        <table border="0" cellspacing="5" cellpadding="0">
                            <tr>
                                <td>
                                    <a href="/creditcard/showStatusInquiry.do">
                                        <input type="button" value="Check Application Status"
                                               style="font-family:Arial; font-size:11px; font-weight:bold;
                                                      background-color:#003366; color:#FFFFFF;
                                                      border:2px outset #336699; padding:4px 8px;
                                                      cursor:pointer;">
                                    </a>
                                </td>
                                <td>&nbsp;</td>
                                <td>
                                    <a href="/creditcard/welcome.do">
                                        <input type="button" value="Return to Home Page"
                                               style="font-family:Arial; font-size:11px;
                                                      background-color:#CCCCCC;
                                                      border:2px outset #999999; padding:4px 8px;
                                                      cursor:pointer;">
                                    </a>
                                </td>
                            </tr>
                        </table>

                    </td>
                </tr>
            </table>
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
