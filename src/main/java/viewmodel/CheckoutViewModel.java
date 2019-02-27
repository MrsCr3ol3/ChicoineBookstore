package viewmodel;

import business.customer.CustomerForm;

import javax.servlet.http.HttpServletRequest;

public class CheckoutViewModel extends BaseViewModel {

    private CustomerForm customerForm;
    private Boolean hasTransactionError;
    private Boolean hasValidationError;

    public CheckoutViewModel(HttpServletRequest request) {
        super(request);

        CustomerForm sessionForm = (CustomerForm) session.getAttribute("customerForm");
        customerForm = (sessionForm == null) ? new CustomerForm() : sessionForm;

        hasValidationError = (Boolean) session.getAttribute("validationError");
        session.setAttribute("validationError", null);

        hasTransactionError = (Boolean) session.getAttribute("transactionError");
        request.getSession().setAttribute("transactionError", null);
    }

    public CustomerForm getCustomerForm() {
        return customerForm;
    }

    public boolean getHasValidationError() {
        return hasValidationError != null && hasValidationError;
    }

    public boolean getHasTransactionError() {
        return hasTransactionError != null && hasTransactionError;
    }
}
