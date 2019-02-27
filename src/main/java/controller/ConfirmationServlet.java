package controller;

import viewmodel.BaseViewModel;
import viewmodel.CartViewModel;
import viewmodel.ConfirmationViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Confirmation",
        urlPatterns = {"/confirmation"})
public class ConfirmationServlet extends BookstoreServlet {


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("con", new ConfirmationViewModel(request));
        request.setAttribute("bse", new BaseViewModel(request));
        request.setAttribute("crt", new CartViewModel(request));
        forwardToJsp(request, response, "/confirmation");
    }


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {


    }
}
