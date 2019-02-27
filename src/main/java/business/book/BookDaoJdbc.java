package business.book;

import business.JdbcUtils;
import business.category.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.BookstoreDbException.BookstoreQueryDbException;

public class BookDaoJdbc implements BookDao {

    private static final String FIND_BY_BOOK_ID_SQL =
            "SELECT book_id, title, author, price, is_public, category_id " +
                    "FROM book " +
                    "WHERE book_id = ?";


    private static final String FIND_BY_CATEGORY_ID_SQL =
            "SELECT book_id, title, author, price, is_public, category_id " +
                    "FROM book " +
                    "WHERE category_id = ?";

    private static final String FIND_LAST_BOOK =
            "SELECT book_id, title, author, price, is_public, category_id " +
                    "FROM book " +
                    "WHERE category_id = ? " +
                    "ORDER BY book_id DESC " +
                    "LIMIT 1";

    private static final String FIND_FIRST_BOOK =
            "SELECT book_id, title, author, price, is_public, category_id " +
                    "FROM book " +
                    "WHERE category_id = ? " +
                    "ORDER BY book_id ASC " +
                    "LIMIT 1";

    @Override
    public Book findByBookId(long bookId) {
        Book book = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_BOOK_ID_SQL)) {
            statement.setLong(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    book = readBook(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding book " + bookId, e);
        }
        return book;
    }


    @Override
    public List<Book> findByCategoryId(long categoryId) {
        List<Book> books = findBooks(FIND_BY_CATEGORY_ID_SQL, categoryId);
        return books;
    }

    @Override
    public long getMaxBookID(long catID) {
        List<Book> books = findBooks(FIND_LAST_BOOK, catID);
        if (books.size() == 0 || books.get(0) == null) {
            return 0;
        }
        return books.get(0).getBookId();
    }

    @Override
    public long getMinBookID(long catID) {
        List<Book> books = findBooks(FIND_FIRST_BOOK, catID);
        if (books.size() == 0 || books.get(0) == null) {
            return 0;
        }
        return books.get(0).getBookId();
    }


    private List<Book> findBooks(String sql, long catID) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, catID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = readBook(resultSet);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding books with category ID: " + catID, e);
        }
        return books;
    }

    private Book readBook(ResultSet resultSet) throws SQLException {
        Long bookId = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        Integer price = resultSet.getInt("price");
        Boolean isPublic = resultSet.getBoolean("is_public");
        Long categoryId = resultSet.getLong("category_id");
        return new Book(bookId, title, author, price, isPublic, categoryId);
    }

}
