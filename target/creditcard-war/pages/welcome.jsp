<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!--
    welcome.jsp - FirstBank Credit Card Application Welcome Page
    Created: 2001-06-15  Author: FirstBank Web Team
    Compatible with: IE 5.5, Netscape 4.7+
-->
<html:html locale="true">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="Author" content="FirstBank IT Department">
    <meta name="Description" content="Apply online for a FirstBank credit card">
    <meta name="Keywords" content="FirstBank, credit card, apply online, visa, mastercard">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <title>FirstBank Online - Credit Card Application</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
</head>
<body bgcolor="#003366" text="#000000" link="#0000CC" vlink="#660066" alink="#FF0000">

<!-- HEADER TABLE -->
<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td bgcolor="#003366" width="200" valign="middle">
            <img src="../images/firstbank_logo.gif" alt="FirstBank" width="180" height="60" border="0">
        </td>
        <td bgcolor="#003366" align="right" valign="bottom">
            <font face="Arial, Helvetica" size="1" color="#CCCCCC">
                Secure Online Banking | <a href="/creditcard/showStatusInquiry.do" style="color:#FFCC00;">Check Application Status</a>
                &nbsp;&nbsp;
            </font>
        </td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="#336699" height="5"></td>
    </tr>
    <tr>
        <td colspan="2" bgcolor="#FFCC00" height="3"></td>
    </tr>
</table>

<!-- NAVIGATION BAR -->
<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr bgcolor="#336699">
        <td>
            <table border="0" cellspacing="0" cellpadding="5">
                <tr>
                    <td><a href="/creditcard/welcome.do" class="navlink">Home</a></td>
                    <td><font color="#CCCCCC">|</font></td>
                    <td><a href="/creditcard/cardProducts.do" class="navlink">Card Products</a></td>
                    <td><font color="#CCCCCC">|</font></td>
                    <td><a href="/creditcard/showPersonalInfo.do" class="navlink">Apply Now</a></td>
                    <td><font color="#CCCCCC">|</font></td>
                    <td><a href="/creditcard/showStatusInquiry.do" class="navlink">Application Status</a></td>
                    <td><font color="#CCCCCC">|</font></td>
                    <td><a href="/creditcard/pages/contact.jsp" class="navlink">Contact Us</a></td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<!-- MAIN CONTENT AREA -->
<table width="760" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <!-- LEFT NAVIGATION -->
        <td width="160" bgcolor="#E8EEF4" valign="top">
            <table width="160" border="0" cellspacing="1" cellpadding="5">
                <tr bgcolor="#003366">
                    <td><font face="Arial" size="2" color="#FFFFFF"><b>Quick Links</b></font></td>
                </tr>
                <tr bgcolor="#FFFFFF">
                    <td>
                        <font face="Arial" size="2">
                            &raquo; <a href="/creditcard/showPersonalInfo.do">Apply for Classic Card</a><br><br>
                            &raquo; <a href="/creditcard/showPersonalInfo.do">Apply for Gold Card</a><br><br>
                            &raquo; <a href="/creditcard/showPersonalInfo.do">Apply for Platinum Card</a><br><br>
                            &raquo; <a href="/creditcard/showStatusInquiry.do">Track Application</a><br><br>
                            &raquo; <a href="/creditcard/cardProducts.do">Compare Cards</a><br><br>
                            &raquo; <a href="javascript:window.print()">Print This Page</a>
                        </font>
                    </td>
                </tr>
                <tr bgcolor="#003366">
                    <td><font face="Arial" size="2" color="#FFFFFF"><b>Why FirstBank?</b></font></td>
                </tr>
                <tr bgcolor="#FFFFFF">
                    <td>
                        <font face="Arial" size="1">
                            <img src="../images/check.gif" width="10" height="10" alt="*"> No annual fee (Classic)<br>
                            <img src="../images/check.gif" width="10" height="10" alt="*"> Worldwide acceptance<br>
                            <img src="../images/check.gif" width="10" height="10" alt="*"> 24/7 customer service<br>
                            <img src="../images/check.gif" width="10" height="10" alt="*"> Fraud protection<br>
                            <img src="../images/check.gif" width="10" height="10" alt="*"> Online account access<br>
                        </font>
                    </td>
                </tr>
            </table>

            <!-- Security Badge -->
            <br>
            <table width="155" border="1" cellspacing="0" cellpadding="5" align="center" bordercolor="#999999">
                <tr bgcolor="#FFFFCC">
                    <td align="center">
                        <img src="../images/lock.gif" width="20" height="25" alt="Secure"><br>
                        <font face="Arial" size="1" color="#003366">
                            <b>128-bit SSL</b><br>
                            Secure Connection<br>
                            <font size="1" color="#666666">Verified by VeriSign</font>
                        </font>
                    </td>
                </tr>
            </table>
        </td>

        <!-- MAIN CENTER CONTENT -->
        <td valign="top" bgcolor="#FFFFFF">
            <table width="100%" border="0" cellspacing="10" cellpadding="5">
                <tr>
                    <td colspan="2">
                        <img src="../images/apply_banner.gif" width="580" height="80"
                             alt="Apply for a FirstBank Credit Card - It's Fast, Easy, and Secure!"
                             border="0">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table width="100%" border="0" cellspacing="0" cellpadding="8" bgcolor="#F4F8FF">
                            <tr>
                                <td>
                                    <font face="Arial, Helvetica, sans-serif" size="3" color="#003366">
                                        <b>Apply for a FirstBank Credit Card Today!</b>
                                    </font>
                                    <hr size="1" color="#336699">
                                    <font face="Arial, Helvetica, sans-serif" size="2">
                                        Welcome to FirstBank's online credit card application. Applying is
                                        <b>fast, easy, and secure</b>. Most applications receive a decision
                                        within <b>60 seconds</b>.
                                        <br><br>
                                        Our online application uses <b>128-bit SSL encryption</b> to protect
                                        your personal information. Your privacy is our top priority.
                                        <br><br>
                                        The application process consists of <b>two simple steps</b>:
                                    </font>
                                    <ol>
                                        <li><font face="Arial" size="2"><b>Personal &amp; Contact Information</b></font></li>
                                        <li><font face="Arial" size="2"><b>Financial &amp; Employment Information</b></font></li>
                                    </ol>
                                    <font face="Arial" size="2">
                                        You will need the following information to complete your application:
                                    </font>
                                    <ul>
                                        <li><font face="Arial" size="2">Social Security Number (SSN)</font></li>
                                        <li><font face="Arial" size="2">Current annual income information</font></li>
                                        <li><font face="Arial" size="2">Employment information</font></li>
                                        <li><font face="Arial" size="2">Current address and contact details</font></li>
                                    </ul>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <!-- CARD SELECTION BOXES -->
                <tr>
                    <td colspan="2">
                        <font face="Arial" size="2" color="#003366"><b>Choose Your Card:</b></font>
                        <hr size="1" color="#336699">
                    </td>
                </tr>

                <!-- Classic Card -->
                <tr>
                    <td width="50%" valign="top">
                        <table width="100%" border="1" cellspacing="0" cellpadding="8" bordercolor="#999999">
                            <tr bgcolor="#003366">
                                <td colspan="2">
                                    <font face="Arial" size="2" color="#FFFFFF"><b>FirstBank Classic Visa</b></font>
                                </td>
                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td>
                                    <font face="Arial" size="2">
                                        <b>Annual Fee:</b> $0<br>
                                        <b>APR:</b> 19.99%<br>
                                        <b>Credit Limit:</b> $500 - $5,000<br>
                                        <b>Min. Income:</b> $18,000/yr<br>
                                        <br>
                                        Perfect for establishing or rebuilding credit.
                                        <br><br>
                                        <a href="/creditcard/showPersonalInfo.do?cardTypeId=1">
                                            <img src="../images/apply_now_btn.gif"
                                                 alt="Apply Now" border="0"
                                                 width="90" height="25">
                                        </a>
                                    </font>
                                </td>
                            </tr>
                        </table>
                    </td>

                    <!-- Gold Card -->
                    <td width="50%" valign="top">
                        <table width="100%" border="1" cellspacing="0" cellpadding="8" bordercolor="#999999">
                            <tr bgcolor="#CC9900">
                                <td colspan="2">
                                    <font face="Arial" size="2" color="#FFFFFF"><b>FirstBank Gold Visa</b></font>
                                </td>
                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td>
                                    <font face="Arial" size="2">
                                        <b>Annual Fee:</b> $75<br>
                                        <b>APR:</b> 17.99%<br>
                                        <b>Credit Limit:</b> $2,500 - $15,000<br>
                                        <b>Min. Income:</b> $35,000/yr<br>
                                        <br>
                                        Rewards points on every purchase.
                                        <br><br>
                                        <a href="/creditcard/showPersonalInfo.do?cardTypeId=2">
                                            <img src="../images/apply_now_btn.gif"
                                                 alt="Apply Now" border="0"
                                                 width="90" height="25">
                                        </a>
                                    </font>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <!-- Platinum and Student cards -->
                <tr>
                    <td width="50%" valign="top">
                        <table width="100%" border="1" cellspacing="0" cellpadding="8" bordercolor="#999999">
                            <tr bgcolor="#666666">
                                <td colspan="2">
                                    <font face="Arial" size="2" color="#FFFFFF"><b>FirstBank Platinum MasterCard</b></font>
                                </td>
                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td>
                                    <font face="Arial" size="2">
                                        <b>Annual Fee:</b> $125<br>
                                        <b>APR:</b> 15.99%<br>
                                        <b>Credit Limit:</b> $5,000 - $30,000<br>
                                        <b>Min. Income:</b> $60,000/yr<br>
                                        <br>
                                        Premium rewards, travel insurance &amp; concierge.
                                        <br><br>
                                        <a href="/creditcard/showPersonalInfo.do?cardTypeId=3">
                                            <img src="../images/apply_now_btn.gif"
                                                 alt="Apply Now" border="0"
                                                 width="90" height="25">
                                        </a>
                                    </font>
                                </td>
                            </tr>
                        </table>
                    </td>

                    <!-- Student Card -->
                    <td width="50%" valign="top">
                        <table width="100%" border="1" cellspacing="0" cellpadding="8" bordercolor="#999999">
                            <tr bgcolor="#006633">
                                <td colspan="2">
                                    <font face="Arial" size="2" color="#FFFFFF"><b>FirstBank Student Visa</b></font>
                                </td>
                            </tr>
                            <tr bgcolor="#FFFFFF">
                                <td>
                                    <font face="Arial" size="2">
                                        <b>Annual Fee:</b> $0<br>
                                        <b>APR:</b> 21.99%<br>
                                        <b>Credit Limit:</b> $300 - $2,000<br>
                                        <b>Min. Income:</b> None required<br>
                                        <br>
                                        Build credit while you're in school.
                                        <br><br>
                                        <a href="/creditcard/showPersonalInfo.do?cardTypeId=5">
                                            <img src="../images/apply_now_btn.gif"
                                                 alt="Apply Now" border="0"
                                                 width="90" height="25">
                                        </a>
                                    </font>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <!-- FDIC and Legal Notice -->
                <tr>
                    <td colspan="2" bgcolor="#F0F0F0">
                        <font face="Arial" size="1" color="#666666">
                            <b>Important Disclosures:</b> All applications subject to credit approval.
                            APR is accurate as of June 2001 and may vary. FirstBank is an equal opportunity lender.
                            Member FDIC. &copy; 2001 FirstBank N.A. All Rights Reserved.
                            <a href="/creditcard/pages/privacy.jsp">Privacy Policy</a> |
                            <a href="/creditcard/pages/terms.jsp">Terms &amp; Conditions</a>
                        </font>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>

<!-- FOOTER -->
<table width="760" border="0" cellspacing="0" cellpadding="5" align="center">
    <tr>
        <td bgcolor="#FFCC00" height="3"></td>
    </tr>
    <tr bgcolor="#003366">
        <td align="center">
            <font face="Arial" size="1" color="#CCCCCC">
                &copy; 2001 FirstBank N.A. | All Rights Reserved |
                <a href="/creditcard/pages/privacy.jsp" style="color:#CCCCCC;">Privacy Policy</a> |
                <a href="/creditcard/pages/terms.jsp" style="color:#CCCCCC;">Terms of Use</a> |
                <a href="/creditcard/pages/contact.jsp" style="color:#CCCCCC;">Contact Us</a>
                <br>
                This site is best viewed at 800x600 resolution with Internet Explorer 5.5 or Netscape 6.0+
            </font>
        </td>
    </tr>
</table>

</body>
</html:html>
