package business.order;

import business.book.Book;
import business.book.BookDaoJdbc;

public class LineItem {

    private long bookId;
    private long customerOrderId;
    private int quantity;

    public LineItem(long bookId, long customerOrderId, int quantity) {
        this.bookId = bookId;
        this.customerOrderId = customerOrderId;
        this.quantity = quantity;
    }

    public long getBookId() {
        return bookId;
    }

    public Book getBook(){
        return new BookDaoJdbc().findByBookId(this.bookId);
    }

    public long getCustomerOrderId() {
        return customerOrderId;
    }

    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "LineItem{" +
                "bookId=" + bookId +
                ", customerOrderId=" + customerOrderId +
                ", quantity=" + quantity +
                '}';
    }
}