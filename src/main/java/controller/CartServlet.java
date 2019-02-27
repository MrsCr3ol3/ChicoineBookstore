package controller;

import business.ApplicationContext;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import viewmodel.CartViewModel;
import viewmodel.CategoryViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Cart", loadOnStartup = 1, urlPatterns = {"/cart"})
public class CartServlet extends BookstoreServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        String action = request.getParameter("action");
        BookDao bookDao = ApplicationContext.INSTANCE.getBookDao();
        ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");

        if (action.equalsIgnoreCase("increase") || action.equalsIgnoreCase("decrease")) {
            Long bookId = Long.parseLong(request.getParameter("bookId"));
            Book book = bookDao.findByBookId(bookId);
            if ("increase".equals(action)) {
                cart.increment(book);
            } else if ("decrease".equals(action)) {
                cart.decrement(book);
            }
            ajaxCartCount(request, response, cart, getSCitem(cart, bookId));
        }
    }

    private ShoppingCartItem getSCitem(ShoppingCart cart, Long bookId) {
        for (ShoppingCartItem sc : cart.getItems()) {
            if (sc.getBook().getBookId().equals(bookId)) {
                return sc;
            }
        }

        return null;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        request.setAttribute("crt", new CartViewModel(request));
        request.setAttribute("cat", new CategoryViewModel(request));

        String userPath = "/cart";
        String url = "/WEB-INF/jsp" + userPath + ".jsp";
        request.getRequestDispatcher(url).forward(request, response);

    }

    protected void ajaxCartCount(HttpServletRequest request, HttpServletResponse response, ShoppingCart cart, ShoppingCartItem sci) throws IOException {

        Long bookId = 0L;
        int quantity = 0;

        if (sci != null) {
            bookId = sci.getBookId();
            quantity = sci.getQuantity();
        }

        boolean isAjaxRequest =
                "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
        if (isAjaxRequest) {
            String jsonString = "";

//            if quantity is zero, send back a redirect url instead (to reload cart page)
            if (quantity == 0) {
                jsonString = "{\"redir\": \"" + request.getContextPath() + "/cart" + "\"}";
            }else{
                jsonString = "{\"cartCount\": " + cart.getNumberOfItems() + ", " +
                        "\"bookEntry\": " + bookId + ", " +
                        "\"totalPrice\": " + formatJsonCash(cart.getSubtotal()) + ", " +
                        "\"bookCount\": " + quantity + "}";
            }

//            System.out.println(jsonString);
            response.setContentType("application/json");
            response.getWriter().write(jsonString);
            response.flushBuffer();
            return;
        }
    }

    private String formatJsonCash(int subtotal) {
        String s = "" + subtotal;
        return s.substring(0, s.length() - 2) + "." + s.substring(s.length() - 2, s.length());

    }

}
