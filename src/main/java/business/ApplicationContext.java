
package business;

import business.book.BookDao;
import business.book.BookDaoJdbc;
import business.category.CategoryDao;
import business.category.CategoryDaoJdbc;
import business.customer.CustomerDao;
import business.customer.CustomerDaoJdbc;
import business.order.*;


public class ApplicationContext {
    private LineItemDao lineItemDao;

    private OrderDao orderDao;

    private OrderService orderService;

    private CustomerDao customerDao;

    private CategoryDao categoryDao;

    private BookDao bookDao;

    public static ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {
        orderService = new DefaultOrderService();
        lineItemDao = new LineItemDaoJdbc();
        orderDao = new OrderDaoJdbc();
        customerDao = new CustomerDaoJdbc();
        categoryDao = new CategoryDaoJdbc();
        bookDao = new BookDaoJdbc();

        DefaultOrderService service = (DefaultOrderService) orderService;
        service.setBookDao(bookDao);
        service.setCustomerDao(customerDao);
        service.setOrderDao(orderDao);
        service.setLineItemDao(lineItemDao);
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public OrderService getOrderService() {
        return this.orderService;
    }
}
