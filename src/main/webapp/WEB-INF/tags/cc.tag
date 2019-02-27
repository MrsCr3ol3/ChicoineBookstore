<%--Masks credit card numbers--%>

<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="stringArg" rtexprvalue="true" required="true" type="java.lang.String" description="Text to capitalize" %>

<c:set var="last4" value="${fn:substring(stringArg,fn:length(stringArg)-4,fn:length(stringArg))}" />
####-####-####-${last4}