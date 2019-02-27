package viewmodel;

import business.order.OrderDetails;

import javax.servlet.http.HttpServletRequest;

public class ConfirmationViewModel extends BaseViewModel {

    private OrderDetails orderDetails;

    public ConfirmationViewModel(HttpServletRequest request) {
        super(request);
        this.orderDetails = (OrderDetails) session.getAttribute("orderDetails");
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }
}
