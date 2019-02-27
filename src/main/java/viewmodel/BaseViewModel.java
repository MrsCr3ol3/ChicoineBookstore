package viewmodel;

import business.ApplicationContext;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.category.Category;
import business.category.CategoryDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class BaseViewModel {

    private static final String SITE_IMAGE_PATH = "images/site/";
    private static final String BOOK_IMAGE_PATH = "images/books/";

    // Every view model knows the request and session
    protected HttpServletRequest request;
    protected HttpSession session;

    protected CategoryDao categoryDao;
    protected BookDao bookDao;


    protected int surcharge = 500;

    // The header (on all pages) needs to know the categories
    protected List<Category> categories;

    public BaseViewModel(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession(false);

        this.categories = initCategories();
        this.categoryDao = ApplicationContext.INSTANCE.getCategoryDao();
        this.bookDao = ApplicationContext.INSTANCE.getBookDao();

    }


    private List<Category> initCategories() {
        List<Category> result = (List<Category>) request.getServletContext().getAttribute("categories");
        if (result == null) {
            result = ApplicationContext.INSTANCE.getCategoryDao().findAll();
            request.getServletContext().setAttribute("categories", result);
        }
        return result;
    }


    public String getSiteImagePath() {
        return SITE_IMAGE_PATH;
    }

    public String getBookImagePath() {
        return BOOK_IMAGE_PATH;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getSurcharge() {
        return surcharge;
    }


}
