<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!--
    application_status.jsp - Application Status Result Display
    Author: FirstBank Web Team  Date: 2001-06-15
-->
<html:html locale="true">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FirstBank - Application Status Result</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
</head>
<body bgcolor="#FFFFFF">

<!-- HEADER -->
<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr><td bgcolor="#003366" height="60">
        <table width="100%" cellpadding="5" border="0">
            <tr>
                <td><font face="Arial" size="4" color="#FFFFFF"><b>FirstBank</b></font></td>
                <td align="right"><font face="Arial" size="1" color="#CCCCCC">
                    <a href="/creditcard/welcome.do" style="color:#CCCCCC;">Home</a>
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
            <font face="Arial" size="3" color="#003366"><b>Application Status Result</b></font>
            <hr size="1" color="#336699">

            <%
                com.firstbank.creditcard.vo.ApplicationVO appVO =
                    (com.firstbank.creditcard.vo.ApplicationVO) request.getAttribute("applicationVO");
                String statusCode = (appVO != null) ? appVO.getStatusCode() : "UNKNOWN";
                String statusColor = "#000000";
                if ("APPROVED".equals(statusCode)) statusColor = "#006600";
                else if ("REJECTED".equals(statusCode)) statusColor = "#CC0000";
                else if ("REVIEW".equals(statusCode)) statusColor = "#0000CC";
                else if ("PENDING".equals(statusCode)) statusColor = "#CC6600";
            %>

            <% if (appVO != null) { %>
            <table border="1" cellspacing="0" cellpadding="8" width="500" bordercolor="#CCCCCC">
                <tr bgcolor="#003366">
                    <td colspan="2">
                        <font face="Arial" size="2" color="#FFFFFF"><b>Application Information</b></font>
                    </td>
                </tr>
                <tr>
                    <td bgcolor="#F4F8FF" width="45%"><font face="Arial" size="2"><b>Application Number:</b></font></td>
                    <td bgcolor="#FFFFFF"><font face="Arial" size="2"><b><%= appVO.getApplicationId() %></b></font></td>
                </tr>
                <tr>
                    <td bgcolor="#F4F8FF"><font face="Arial" size="2"><b>Current Status:</b></font></td>
                    <td bgcolor="#FFFFFF">
                        <font face="Arial" size="2" color="<%= statusColor %>">
                            <b><%= appVO.getStatusDescription() != null ? appVO.getStatusDescription() : statusCode %></b>
                        </font>
                    </td>
                </tr>
                <% if ("APPROVED".equals(statusCode)) { %>
                <tr>
                    <td bgcolor="#F4F8FF"><font face="Arial" size="2"><b>Approved Credit Limit:</b></font></td>
                    <td bgcolor="#EEFFEE">
                        <font face="Arial" size="2" color="#006600">
                            <b>$<%= String.format("%,.2f", appVO.getApprovedLimit()) %></b>
                        </font>
                    </td>
                </tr>
                <% } %>
                <% if ("REJECTED".equals(statusCode) && appVO.getRejectionReasonDesc() != null) { %>
                <tr>
                    <td bgcolor="#F4F8FF"><font face="Arial" size="2"><b>Reason:</b></font></td>
                    <td bgcolor="#FFEEEE">
                        <font face="Arial" size="2" color="#CC0000">
                            <%= appVO.getRejectionReasonDesc() %>
                        </font>
                    </td>
                </tr>
                <% } %>
            </table>

            <br>
            <% if ("PENDING".equals(statusCode) || "REVIEW".equals(statusCode)) { %>
            <div class="noticeBox">
                <font face="Arial" size="2">
                    <b>Note:</b> Your application is currently under review. Decisions are typically
                    made within <b>1-3 business days</b>. Please check back again later.
                </font>
            </div>
            <% } else if ("APPROVED".equals(statusCode)) { %>
            <div class="successBox">
                <font face="Arial" size="2">
                    <b>Congratulations!</b> Your credit card application has been approved.
                    Your new FirstBank credit card will be mailed to your address on file within
                    <b>7-10 business days</b>. Please call 1-800-FIRST-BK to activate upon receipt.
                </font>
            </div>
            <% } else if ("REJECTED".equals(statusCode)) { %>
            <div class="errorBox">
                <font face="Arial" size="2">
                    We regret to inform you that your application was not approved at this time.
                    You will receive a written notice explaining this decision within 10 business days.
                    You may re-apply after 6 months. For questions, please call 1-800-FIRST-BK.
                </font>
            </div>
            <% } %>

            <% } else { %>
            <div class="errorBox">
                <font face="Arial" size="2">
                    <b>Error:</b> Unable to retrieve application information. Please try again
                    or contact customer service at 1-800-FIRST-BK.
                </font>
            </div>
            <% } %>

            <br>
            <a href="/creditcard/showStatusInquiry.do">Check Another Application</a>
            &nbsp;|&nbsp;
            <a href="/creditcard/welcome.do">Return to Home Page</a>

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
