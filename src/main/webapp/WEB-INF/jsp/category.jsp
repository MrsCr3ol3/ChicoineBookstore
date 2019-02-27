<jsp:useBean id="cat" scope="request" type="viewmodel.CategoryViewModel"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
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
    <jsp:include page="categoryButtons.jsp"/>
    <section class="flexContainer">
        <div class="contentBox">
            <%--<td class="itemsBox">--%>
            <c:forEach var="book" items="${cat.selectedCategoryBooks}">
                <div class="catalogItem">
                    <table>
                            <%--Book image--%>
                        <tr>
                            <td>
                                <img class="bookImg"
                                     src="${cat.bookImagePath}${book.categoryId}_${book.bookId}.jpg">
                            </td>
                        </tr>
                            <%--Actions (add to cart, view) --%>
                        <tr>
                            <td>
                                <table>
                                    <td>


                                        <input type="image"
                                               src="${cat.siteImagePath}add2Cart.png"
                                               class="addToCartButton"
                                               data-book-id="${book.bookId}"
                                               data-action="add">


                                    </td>
                                    <c:if test="${book.isPublic}">
                                        <td>
                                            <img src="${cat.siteImagePath}divider.png">
                                        </td>
                                        <td>
                                            <a href="home"><img src="${cat.siteImagePath}look.png"></a>
                                        </td>
                                    </c:if>
                                </table>
                            </td>
                        </tr>

                            <%--Book information--%>
                        <tr>
                            <td>
                                <div class="title wordwrap">${book.title}</div>
                                <div class="author wordwrap">${book.author}</div>
                                <div class="price"><my:price stringArg="${book.price}"/></div>
                            </td>
                        </tr>


                    </table>
                </div>
            </c:forEach>
            <%--</td>--%>
        </div>
    </section>
    <jsp:include page="footer.jsp"/>
</main>
<script src="<c:url value="/js/ajax-functions.js"/>" type="text/javascript"></script>

<script>
    var addToCartButtons = document.getElementsByClassName("addToCartButton");
    for (var i = 0; i < addToCartButtons.length; i++) {
        addToCartButtons[i].addEventListener("click", function (event) {
            addToCartEvent(event.target)
        });

        addToCartButtons[i].addEventListener("mouseup", function (event) {
            event.target.blur();
        });
    }

    function addToCartEvent(button) {

        var data = {
            "bookId": button.dataset.bookId,
            "action": button.dataset.action
        };
        ajaxPost('category', data, function (text, xhr) {
            addToCartCallback(text, xhr)
        });
    }

    function addToCartCallback(responseText, xhr) {
        var cartCount = JSON.parse(responseText).cartCount;
        document.getElementById('cartCount').textContent = '' + cartCount;
    }
</script>


</body>
</html>
