package controller;

import business.ApplicationContext;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import viewmodel.CategoryViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Category", urlPatterns = {"/category"})
public class CategoryServlet extends BookstoreServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Book book = null;
        if ("add".equals(action)) {
            Long bookId = Long.parseLong(request.getParameter("bookId"));

            BookDao bookDao = ApplicationContext.INSTANCE.getBookDao();
            book = bookDao.findByBookId(bookId);
            ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
            cart.addItem(book);

            ajaxCartCount(request, response, cart);
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        super.doGet(request, response);
        request.setAttribute("cat", new CategoryViewModel(request));

        String userPath = "/category";
        String url = "/WEB-INF/jsp" + userPath + ".jsp";
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void ajaxCartCount(HttpServletRequest request, HttpServletResponse response, ShoppingCart cart) throws IOException {
        boolean isAjaxRequest =
                "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
        if (isAjaxRequest) {
            String jsonString = "{\"cartCount\": " + cart.getNumberOfItems() + "}";
            response.setContentType("application/json");
            response.getWriter().write(jsonString);
            response.flushBuffer();
            return;
        }
    }

}
