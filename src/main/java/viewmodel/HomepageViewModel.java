package viewmodel;

import business.book.Book;
import business.category.Category;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class HomepageViewModel extends BaseViewModel {
    List<Category> randomCategories;

    Map<Category, List<Book>> bookCollection;

    public HomepageViewModel(HttpServletRequest request) {
        super(request);
        buildCategories();
        buildBookCollection();
    }

    private void buildBookCollection() {
        bookCollection = new HashMap<>();

        for (Category c : randomCategories) {
            List<Book> thisBookList = new ArrayList<>();

            int minBookID = (int) bookDao.getMinBookID(c.getCategoryId());
            int maxBookID = (int) bookDao.getMaxBookID(c.getCategoryId());

            if (maxBookID == 0) {
                continue;
            }

            Random rand = new Random();

            while (thisBookList.size() != 4) {
                int randomNum = rand.nextInt((maxBookID - minBookID) + 1) + minBookID;
                Book b = bookDao.findByBookId(randomNum);

                long bID = b.getBookId();
                boolean found = false;
                for (Book bk : thisBookList) {
                    if (bk.getBookId().equals(bID)) {
                        found = true;
                    }
                }
                if (found == false) {
                    thisBookList.add(b);
                }
            }

            bookCollection.put(c, thisBookList);
        }
    }

    private void buildCategories() {
        randomCategories = new ArrayList<>();

        int minCatID = 1000;
        int maxCatID = (int) categoryDao.getMaxCategoryID();
        Random rand = new Random();

        while (randomCategories.size() != 4) {
            int randomNum = rand.nextInt((maxCatID - minCatID) + 1) + minCatID;

            Category c = categoryDao.findByCategoryId(randomNum);

            if (categoryAlreadyAdded(c)) {
                continue;
            }
            randomCategories.add(c);

        }

    }

    private boolean categoryAlreadyAdded(Category c) {
        for (Category cat : randomCategories) {
            if (cat.getCategoryId() == c.getCategoryId()) {
                return true;
            }
        }
        return false;
    }

    private boolean bookAlreadyAdded(List<Book> thisBookList, Book b) {
        for (Book book : thisBookList) {
            if (book.getBookId() == b.getBookId()) {
                return true;
            }
        }
        return false;
    }

    public Map<Category, List<Book>> getBookCollection() {
        return bookCollection;
    }
}
