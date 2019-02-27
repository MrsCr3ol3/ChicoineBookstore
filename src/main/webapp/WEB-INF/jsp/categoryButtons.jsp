<jsp:useBean id="cat" scope="request" type="viewmodel.CategoryViewModel"/>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="hp" scope="session" value="/WEB-INF/jsp/homepage.jsp"/>

<%--this jsp holds the row of category buttons visible on each page.  It is held here to reduce code redundancy--%>

<categoryButtons>
    <section class="flexContainerButtons">
        <c:forEach var="category" items="${cat.categories}">

            <c:choose>
                <c:when test="${category.name.equalsIgnoreCase(cat.selectedCategory.name) && !pageContext.request.servletPath.equalsIgnoreCase(hp)}">
                    <div class="categoryButton categoryButtonSelected" id="selectedCategoryButton"><a
                            href="category?category=${category.name}"><my:capitalize
                            stringArg="${category.name}"/></a></div>
                </c:when>

                <c:otherwise>
                    <div class="categoryButton"><a href="category?category=${category.name}"><my:capitalize
                            stringArg="${category.name}"/></a></div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </section>
    <br/>

    <%--Don't show the title if it's the homepage--%>
    <c:if test="${!pageContext.request.servletPath.equalsIgnoreCase(hp)}">
        <section class="flexContainerTitle">
            <div id="categoryNameHeader" class="catHeader">
                <my:capitalize stringArg="${cat.selectedCategory.name}"/>

            </div>
        </section>
    </c:if>

</categoryButtons>
