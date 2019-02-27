package viewmodel;

import business.cart.ShoppingCart;
import business.category.Category;

import javax.servlet.http.HttpServletRequest;

public class CartViewModel extends BaseViewModel {

    protected ShoppingCart cart;


    public CartViewModel(HttpServletRequest request) {
        super(request);
        this.cart = initCart();
    }
    private ShoppingCart initCart() {
        ShoppingCart result = (ShoppingCart) request.getSession().getAttribute("cart");
        if (result == null) {
            result = new ShoppingCart(getSurcharge());
            request.getSession().setAttribute("cart", result);
        }
        return result;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public String getTotal() {
        return Integer.toString(cart.getSubtotal() + getSurcharge());
    }
}
