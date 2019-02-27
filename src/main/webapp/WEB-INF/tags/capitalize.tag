<%--Capitalizes any word--%>

<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="stringArg" rtexprvalue="true" required="true" type="java.lang.String" description="Text to capitalize" %>
<c:set var="firstChar" value="${fn:substring(stringArg, 0, 1)}" />
<c:set var="capFirstChar" value="${fn:toUpperCase(firstChar)}" />
<c:set var="otherChars" value="${fn:substring(stringArg,1,fn:length(stringArg))}" />
${capFirstChar}${otherChars}