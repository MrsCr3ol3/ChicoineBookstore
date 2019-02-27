<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="p" scope="request" type="viewmodel.CheckoutViewModel"/>
<jsp:useBean id="crt" scope="request" type="viewmodel.CartViewModel"/>

<!doctype html>
<html>
<head>
    <title>Chicoine Bookstore - Checkout</title>

    <meta charset="utf-8">
    <meta name="description" content="The category page for My Bookstore">

    <link rel="stylesheet" href="css/normalize-and-reset.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/homepage.css">
    <link rel="stylesheet" href="css/category.css">
    <link rel="stylesheet" href="css/confirmation.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="<c:url value="/js/jquery.validate.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/additional-methods.js"/>" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#checkoutForm").validate({
                rules: {
                    name: {
                        required: true,
                        maxlength: 45
                    },
                    email: {
                        required: true,
                        email: true,
                        maxlength: 45
                    },
                    phone: {
                        required: true,
                        minlength: 9,
                        maxlength: 45
                    },
                    address: {
                        required: true,
                        maxlength: 45
                    },
                    ccNumber: {
                        required: true,
                        creditcard: true,
                        maxlength: 45
                    }
                }

            });
        });
    </script>
</head>

<body class="home">
<main>
    <jsp:include page="header.jsp"/>

    <div class="checkoutBox" id="checkoutFormBox">
        <form id="checkoutForm" action="<c:url value='checkout'/>" method="post">

            <div class="confirmHeader">Checkout</div>

            <div id="checkoutFormErrors">
                <c:if test="${p.hasValidationError}">
                    <c:if test="${p.customerForm.hasNameError}">
                        <div class="warningText">${p.customerForm.nameErrorMessage}<br/></div>
                    </c:if>
                    <c:if test="${p.customerForm.hasAddressError}">
                        <div class="warningText">${p.customerForm.addressErrorMessage}<br/></div>
                    </c:if>
                    <c:if test="${p.customerForm.hasPhoneError}">
                        <div class="warningText">${p.customerForm.phoneErrorMessage}<br/></div>
                    </c:if>
                    <c:if test="${p.customerForm.hasEmailError}">
                        <div class="warningText">${p.customerForm.emailErrorMessage}<br/></div>
                    </c:if>
                    <c:if test="${p.customerForm.hasCCNumberError}">
                        <div class="warningText">${p.customerForm.CCNumberErrorMessage}<br/></div>
                    </c:if>
                    <c:if test="${p.customerForm.hasCCExpDateError}">
                        <div class="warningText">${p.customerForm.CCExpDateErrorMessage}<br/></div>
                    </c:if>
                </c:if>
                <c:if test="${p.hasTransactionError}">
                    <div class="errorText">An error occurred while processing this transaction.</div>
                    <br>
                </c:if>
            </div>

            <table class="confirmTable" id="checkoutFormAndInfo">


                <tr>
                    <td>Name</td>
                    <td>
                        <input class="textField" type="text" size="20" maxlength="45" name="name"
                               value="${p.customerForm.name}">
                    </td>
                </tr>

                <tr>
                    <td>Address</td>
                    <td>
                        <input class="textField" type="text" size="20" maxlength="45" name="address"
                               value="${p.customerForm.address}">
                    </td>
                </tr>

                <tr>
                    <td>Phone</td>
                    <td>
                        <input class="textField" type="text" size="20" maxlength="45" id="phone" name="phone"
                               value="${p.customerForm.phone}">
                    </td>
                </tr>

                <tr>
                    <td>Email</td>
                    <td>
                        <input class="textField" type="text" size="20" maxlength="45" name="email"
                               value="${p.customerForm.email}">
                    </td>
                </tr>


                <tr>
                    <%--do not retain cc info--%>
                    <td>
                        <input class="textField" type="text" size="20" maxlength="45" name="ccNumber">
                    </td>
                </tr>


                <tr>
                    <td>Exp. Date</td>
                    <td>
                        <select class="selectMenu" name="ccMonth">
                            <c:set var="montharr"
                                   value="${['January','February','March','April','May','June','July','August','September','October','November','December']}"/>
                            <c:forEach begin="1" end="12" var="month">
                                <option value="${month}"
                                        <c:if test="${p.customerForm.ccMonth eq month}">selected</c:if>>
                                        ${month}-${montharr[month - 1]}
                                </option>
                            </c:forEach>
                        </select>
                        <select class="selectMenu" name="ccYear">
                            <c:forEach begin="2018" end="2027" var="year">
                                <option value="${year}"
                                        <c:if test="${p.customerForm.ccYear eq year}">selected</c:if>>
                                        ${year}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>

            </table>

            <br/>
            <span class="total" id="checkoutInfoText">
            Your credit card will be charged <strong><my:price stringArg="${crt.total}"/></strong><br>
            (<strong><my:price stringArg="${crt.cart.subtotal}"/></strong> + <strong><my:price
                    stringArg="${p.surcharge}"/></strong> shipping)
            </span>
            <br/>
            <input type="image" src="${crt.siteImagePath}submitOrderSm.png"
                   id="boldSubmitButton" formnovalidate
                   type="submit" value="Submit">


        </form>

    </div>

    <jsp:include page="footer.jsp"/>
</main>
</body>
</html>
