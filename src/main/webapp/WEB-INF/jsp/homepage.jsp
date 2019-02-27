<jsp:useBean id="hm" scope="request" type="viewmodel.HomepageViewModel"/>
<jsp:useBean id="cat" scope="request" type="viewmodel.CategoryViewModel"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!doctype html>
<html>
<head>
    <link rel="stylesheet" href="css/normalize-and-reset.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/homepage.css">
    <link rel="stylesheet" href="css/category.css">
    <link rel="stylesheet" href="css/cart.css">

    <title>Chicoine Bookstore</title>

    <meta charset="utf-8">
    <meta name="description" content="Chicoine Bookstore">
</head>
<body>
<jsp:include page="header.jsp"/>

<main>
    <div class="catHeader">
        Welcome to Chicoine books. Please choose a category:
    </div>
    <article>
        <jsp:include page="categoryButtons.jsp"/>

        <div class="catHeader">
            Or browse random selections from some popular categories:
        </div>
        <br/>

        <%--show 4 random books from 4 random categories:--%>
        <c:forEach var="catKey" items="${hm.bookCollection}">
            <article>
                <section class="flexContainer">
                    <div class="catHeader">
                        <my:capitalize stringArg="${catKey.key.name}"/>
                    </div>
                    <div class="contentBox">


                        <c:forEach var="book" items="${catKey.value}">
                            <div class="catalogItem">
                                <table>
                                    <tr>
                                        <img class="bookImg"
                                             src="${hm.bookImagePath}${book.categoryId}_${book.bookId}.jpg">
                                    </tr>
                                    <tr>
                                        <div class="title wordwrap">${book.title}</div>
                                        <div class="author wordwrap">${book.author}</div>
                                        <div class="price"><my:price stringArg="${book.price}"/>
                                        </div>
                                    </tr>
                                    <tr>


                                </table>
                            </div>
                        </c:forEach>

                    </div>
                </section>
            </article>
        </c:forEach>

    </article>
    <jsp:include page="footer.jsp"/>
</main>
</body>
</html>
