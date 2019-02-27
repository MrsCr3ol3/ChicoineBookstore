<jsp:useBean id="bse" scope="request" type="viewmodel.BaseViewModel"/>
<jsp:useBean id="crt" scope="request" type="viewmodel.CartViewModel"/>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<header>

    <div id="logo">
        <!-- give the browser a chance to hold this space by providing a width and height on all static image references as such -->
        <a href="home"><img src="${bse.siteImagePath}logo.png"></a>
    </div>


    <div class="flexItem">
        <div class="searchBox">

            <!-- this search input could easily just be a regular text input, simply update type here and the CSS selector -->
            <input type="search" name="searchTerm" placeholder="Search for a book here...">
            <%--<a href="category?category=a"><input type="submit" value=""></a>--%>
            <a href="category?category=biographical">
                <img class="searchbtn" src="${crt.siteImagePath}search_btn.png"></a>
        </div>
    </div>

    <div class="flexItem">

        <table class="toolTable">

            <tr>
                <%--eventually will redirect to account page--%>
                <div class="loginAcc">
                    <a href="home"></a>
                </div>

                <%--eventually will be replaced with user name--%>
                <div class="welcomeBack">
                    Welcome back, Evan
                </div>
            </tr>
            <tr>
                <div class="sideStuff">
                    <a href="/EvanBookstoreFinal/cart">
                        <div class="cartStuff">
                            <div id="cartCount">
                                ${crt.cart.numberOfItems}
                            </div>
                        </div>
                    </a>

                    <%--create a list to house our button and popup--%>
                    <ul class="categoryDropdownItem">
                        <%--create a list of just the popup--%>
                        <li>

                            <%--give the user something to click--%>
                            <a href="#">
                                <div id="categoryDropdown">
                                    <div class="catText">Categories</div>
                                </div>
                            </a>

                            <%--create the menu items    --%>
                            <ul class="menuItem">

                                <c:forEach var="category" items="${bse.categories}">
                                    <li class="categoryDropdownItem"><a
                                            href="category?category=${category.name}"><my:capitalize
                                            stringArg="${category.name}"/></a></li>
                                </c:forEach>

                            </ul>
                        </li>
                    </ul>
                </div>
            </tr>
        </table>

    </div>


</header>