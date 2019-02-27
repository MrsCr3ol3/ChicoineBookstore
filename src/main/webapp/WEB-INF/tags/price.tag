<%--converts input to price format--%>

<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="stringArg" rtexprvalue="true" required="true" type="java.lang.String" description="Text to capitalize" %>
<c:set var="dollars" value="${fn:substring(stringArg, 0, fn:length(stringArg)-2)}" />
<c:set var="cents" value="${fn:substring(stringArg,fn:length(stringArg)-2,fn:length(stringArg))}" />
$${dollars}.${cents}