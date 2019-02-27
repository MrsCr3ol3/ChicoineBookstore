<jsp:useBean id="crt" scope="request" type="viewmodel.CartViewModel"/>
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


    <title>Chicoine Bookstore - Cart</title>

    <meta charset="utf-8">
    <meta name="description" content="Chicoine Bookstore">
</head>
<body>

<jsp:include page="header.jsp"/>

<main>

    <%--make sure the cart has items before showing this:--%>
    <c:if test="${crt.cart.items.size() > 0}">
        <table class="cartTable">
            <tr>
                <th class="cartTableHeader">Product</th>
                <th class="cartTableHeader">Quantity</th>
                <th class="cartTableHeader">Price</th>
            </tr>

                <%--loop through cart items and display info in styled way--%>
            <c:forEach var="selBook" items="${crt.cart.items}">
                <tr>
                    <td>

                        <table class="bookCartItem">
                            <td><img class="cartBookImg"
                                     src="${crt.bookImagePath}${selBook.book.categoryId}_${selBook.bookId}.jpg">
                            </td>
                            <td width="100%">
                                <div class="title wordwrap">${selBook.book.title}</div>
                                <div class="author wordwrap">${selBook.book.author}</div>
                            </td>
                        </table>
                    </td>
                    <td>
                        <div class="quantity" id="quantityLabel">
                            <table>
                                <td>
                                    <div id="selBookQuantity_${selBook.bookId}">${selBook.quantity}</div>
                                </td>

                                <td>

                                        <%--use ajax to update cart quantities:--%>
                                    <input type="image"
                                           src="${cat.siteImagePath}increase_sm.png"
                                           class="cartAddButton"
                                           data-book-id="${selBook.bookId}"
                                           data-action="increase">


                                </td>
                                <td>
                                        <%--another ajax call to decrease quantity--%>
                                    <input type="image"
                                           src="${cat.siteImagePath}decrease_sm.png"
                                           class="cartSubtractButton"
                                           data-book-id="${selBook.bookId}"
                                           data-action="decrease">


                                </td>
                            </table>
                        </div>

                    </td>

                        <%--format  price as dollar amount--%>
                    <td>
                        <div class="cartPrice"><my:price stringArg="${selBook.book.price}"/></div>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <%--format total as dollar amount, same as price--%>
        <div id="totalPriceLabel" class="catHeader">Total: <my:price stringArg="${crt.cart.subtotal}"/></div>
    </c:if>

    <%--if shopping cart is empty then tell the user--%>
    <c:if test="${crt.cart.items.size() == 0}">
        <div class="catHeader">Your shopping cart is empty.</div>
    </c:if>


    <%--continue shopping and proceed buttons (proceed only shown if items in cart)    --%>
    <section class="flexContainer">
        <a href="category?category=${cat.selectedCategory.name}">
            <img id="continueShoppingBtn" src="${crt.siteImagePath}continueShop.png"></a>
        <c:if test="${crt.cart.items.size() > 0}">
            <a href="/EvanBookstoreFinal/checkout">
                <img id="proceedBtn"
                     src="${crt.siteImagePath}proceed.png"></a>
        </c:if>
    </section>


    <jsp:include page="footer.jsp"/>
</main>

<%--ajax calls--%>
<script src="<c:url value="/js/ajax-functions.js"/>" type="text/javascript"></script>

<script>
    var addToCartButtons = document.getElementsByClassName("cartAddButton");
    var subtractFromCartButtons = document.getElementsByClassName("cartSubtractButton");

    for (var i = 0; i < addToCartButtons.length; i++) {

        // each entry has equal number of add/subtract buttons.  We can safely assume i will be equal in both lists
        addToCartButtons[i].addEventListener("click", function (event) {
            changeBookCountEvent(event.target)
        });

        subtractFromCartButtons[i].addEventListener("click", function (event) {
            changeBookCountEvent(event.target)
        });

        // remove blue border on mouseup
        addToCartButtons[i].addEventListener("mouseup", function (event) {
            event.target.blur();
        });

        subtractFromCartButtons[i].addEventListener("mouseup", function (event) {
            event.target.blur();
        });
    }

    function changeBookCountEvent(button) {
        var data = {
            "bookId": button.dataset.bookId,
            "action": button.dataset.action
        };

        ajaxPost('cart', data, function (text, xhr) {

            cartAndItemCountCallback(text, xhr)

        });
    }

    // update the labels on screen
    function cartAndItemCountCallback(responseText, xhr) {
        // redirect will be null if not needed, and not null if a book
        // has been removed from the cart entirely.  If not null, redirect to url
        var redirect = JSON.parse(responseText).redir;
        if (redirect != null) {
            location.href = redirect;
            return;
        }

        var bookCount = JSON.parse(responseText).bookCount;
        var cartCount = JSON.parse(responseText).cartCount;
        var bookEntry = JSON.parse(responseText).bookEntry;
        var tPrice = JSON.parse(responseText).totalPrice;

        document.getElementById('cartCount').textContent = '' + cartCount;
        document.getElementById('selBookQuantity_' + bookEntry).textContent = '' + bookCount;
        document.getElementById('totalPriceLabel').textContent = 'Total: $' + tPrice;


    }

</script>
</body>
</html>
