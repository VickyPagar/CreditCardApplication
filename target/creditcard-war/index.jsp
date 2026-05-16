<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
    // index.jsp - Redirect to welcome action
    response.sendRedirect(request.getContextPath() + "/welcome.do");
%>
