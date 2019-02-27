package viewmodel;

import business.book.Book;
import business.category.Category;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CategoryViewModel extends BaseViewModel {
    private Category selectedCategory;
    private List<Book> selectedCategoryBooks;

    public CategoryViewModel(HttpServletRequest request) {
        super(request);

        String categoryName = request.getParameter("category");
        if (!isValidName(categoryName)) {
            categoryName = (String) request.getSession().getAttribute("category");
        }

        selectedCategory = (isValidName(categoryName)) ?
                categoryDao.findByName(categoryName) :
                categoryDao.findByCategoryId(1000);

        request.getSession().setAttribute("category", selectedCategory.getName());

        selectedCategoryBooks = bookDao.findByCategoryId(selectedCategory.getCategoryId());
    }

    private boolean isValidName(String categoryName) {
        for (Category c : categoryDao.findAll()) {
            if (c.getName().equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public List<Book> getSelectedCategoryBooks() {
        return selectedCategoryBooks;
    }


}
