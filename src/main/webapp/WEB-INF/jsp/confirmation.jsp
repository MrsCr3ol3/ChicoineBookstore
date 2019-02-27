<jsp:useBean id="con" scope="request" type="viewmodel.ConfirmationViewModel"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link rel="stylesheet" href="css/confirmation.css">

    <title>Chicoine Bookstore - Order Confirmation</title>

    <meta charset="utf-8">
    <meta name="description" content="Chicoine Bookstore">
</head>
<body>

<jsp:include page="header.jsp"/>

<main>
    <c:set var="orderDeets" value="${con.orderDetails}" scope="request"/>

    <div class="confirmHeader">Confirmation</div>
    <div class="confirmationBox">
        <p>Your confirmation number is: ${orderDeets.order.confirmationNumber}</p>
        <br/>
        <p><fmt:formatDate type="both"
                           dateStyle="long" timeStyle="long" value="${orderDeets.order.dateCreated}"/></p>

        <br/>
        <table class="confirmTable">
            <tr>
                <th class="confirmTableHeader">Product</th>
                <th class="confirmTableHeader">Quantity</th>
                <th class="confirmTableHeader">Price</th>
            </tr>
            <c:forEach var="selBook" items="${orderDeets.lineItems}">
                <tr>
                    <td>
                        <table class="bookCartItem">
                            <td><img class="cartBookImg" style="max-width: 80px;"
                                     src="${con.bookImagePath}${selBook.book.categoryId}_${selBook.bookId}.jpg">
                            </td>
                            <td width="100%">
                                <div class="title ">${selBook.book.title}</div>
                                <div class="author ">${selBook.book.author}</div>
                            </td>
                        </table>
                    </td>

                    <td>
                            ${selBook.quantity}
                    </td>

                    <td>
                        <div class="cartPrice"><my:price stringArg="${selBook.book.price}"/></div>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <table class="totalBox">
            <tr>
                <td width="100%" align="right">
                    <div class="confirmPrice">Delivery surcharge: <my:price stringArg="${con.surcharge}"/></div>
                <td/>

            </tr>
            <tr>
                <td width="100%" align="right">
                    <div class="confirmPrice">Total: <my:price stringArg="${orderDeets.order.amount}"/></div>
                <td/>

            </tr>
        </table>

        <div class="catHeader">Customer Information</div>

        <table class="customerInfoBox">
            <tr>
                <td>Name:</td>
                <td>${orderDeets.customer.customerName}</td>
            </tr>

            <tr>
                <td>Address:</td>
                <td>${orderDeets.customer.address}</td>
            </tr>

            <tr>
                <td>Credit Card:</td>
                <td><my:cc stringArg="${orderDeets.customer.ccNumber}"></my:cc></td>
            </tr>
        </table>


    </div>


    <jsp:include page="footer.jsp"/>
</main>
</body>
</html>
