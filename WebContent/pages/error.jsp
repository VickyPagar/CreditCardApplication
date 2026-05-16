<%@ page language="java" contentType="text/html; charset=ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--
    error.jsp - System Error Page
    Author: FirstBank Web Team  Date: 2001-06-15
-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FirstBank - System Error</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
</head>
<body bgcolor="#FFFFFF">
<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr><td bgcolor="#003366" height="50">
        <font face="Arial" size="4" color="#FFFFFF">&nbsp;&nbsp;<b>FirstBank</b></font>
    </td></tr>
    <tr><td bgcolor="#FFCC00" height="3"></td></tr>
</table>
<table width="760" border="0" cellspacing="10" cellpadding="10" align="center">
    <tr>
        <td>
            <div class="errorBox">
                <font face="Arial" size="3"><b>System Error</b></font><br><br>
                <font face="Arial" size="2">
                    We apologize, but a system error has occurred. Our technical team has been notified.
                    <br><br>
                    Please try again later or contact FirstBank Customer Service at
                    <b>1-800-FIRST-BK (1-800-347-7825)</b>, available 24 hours a day, 7 days a week.
                    <br><br>
                    <b>Reference Error ID:</b>
                    <%= System.currentTimeMillis() %>
                    <br><br>
                    <a href="/creditcard/welcome.do">&lt;&lt; Return to Home Page</a>
                </font>
            </div>
            <!-- DO NOT display exception details in production -->
            <!-- <%-- exception: <%= exception %> --%> -->
        </td>
    </tr>
</table>
<table width="760" border="0" cellspacing="0" cellpadding="5" align="center">
    <tr><td bgcolor="#FFCC00" height="3"></td></tr>
    <tr bgcolor="#003366">
        <td align="center">
            <font face="Arial" size="1" color="#CCCCCC">&copy; 2001 FirstBank N.A. All Rights Reserved.</font>
        </td>
    </tr>
</table>
</body>
</html>
