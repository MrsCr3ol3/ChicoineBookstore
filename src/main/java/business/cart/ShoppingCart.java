
package business.cart;

import business.book.Book;
import viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCart {
    private int surcharge;

    public final int maxQuantity = 10;

    List<ShoppingCartItem> items;

    public ShoppingCart(int surcharge) {
        items = new ArrayList<ShoppingCartItem>();
        this.surcharge = surcharge;
    }

    /**
     * Adds an item (book + quantity) to the cart.
     * If the book is already in the cart, increments
     * the quantity.
     *
     * @param book
     */
    public synchronized void addItem(Book book) {

        if (bookInCart(book)) {
            increment(book);
        } else {
            ShoppingCartItem item = new ShoppingCartItem(book);
            items.add(item);
        }
    }

    /**
     * Updates the item to the specified quantity.
     * If the quantity is 0 the book is removed.
     * The cart is unchanged if:
     * - the quantity is less than 0
     * - the quantity is greater than the max
     * - the book is not in the cart
     *
     * @param book
     * @param quantity
     */
    public synchronized void update(Book book, int quantity) {

        ShoppingCartItem item = findItem(book);

        if (item == null || quantity < 0 || quantity > maxQuantity) {
            return;
        }

        if (quantity == 0) {
            items.remove(item);
        } else {
            item.setQuantity(quantity);
        }
    }

    /**
     * Increments the quantity of the item with the book.
     *
     * @param book
     */
    public synchronized void increment(Book book) {

        ShoppingCartItem item = findItem(book);

        if (item == null || item.getQuantity() == maxQuantity) {
            return;
        }

        item.setQuantity(item.getQuantity() + 1);
    }

    /**
     * Decrements the quantity of the item with the book.
     *
     * @param book
     */
    public synchronized void decrement(Book book) {

        ShoppingCartItem item = findItem(book);

        if (item == null || item.getQuantity() == 0) {
            return;
        }

        if (item.getQuantity() == 1) {
            items.remove(item);
        } else {
            item.setQuantity(item.getQuantity() - 1);
        }
    }

    /**
     * Returns the list of items.
     *
     * @return
     */
    public synchronized List<ShoppingCartItem> getItems() {
        return items;
    }

    /**
     * Returns the sum of quantities for all items in the cart.
     *
     * @return
     */
    public synchronized int getNumberOfItems() {
        return items.stream()
                .mapToInt(ShoppingCartItem::getQuantity)
                .sum();
    }

    /**
     * Returns the sum of the book price multiplied by the quantity for all
     * items in shopping cart list. This is the total cost *in cents*,
     * not including the surcharge.
     *
     * @return
     */
    public synchronized int getSubtotal() {
        return items.stream()
                .mapToInt(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    /**
     * Empties the shopping cart.
     */
    public synchronized void clear() {
        items.clear();
    }

    private boolean bookInCart(Book book) {
        return items.stream().anyMatch(item -> item.hasBook(book));
    }

    private ShoppingCartItem findItem(Book book) {
        Optional<ShoppingCartItem> optItem = items.stream()
                .filter(item -> item.hasBook(book))
                .findFirst();
        return optItem.orElse(null);
    }

    public void setSurcharge(int i) {
        this.surcharge = i;
    }

    public int getSurcharge() {
        return this.surcharge;
    }
}
