package controller;

import viewmodel.BaseViewModel;
import viewmodel.CartViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ServletSecurity(@HttpConstraint(transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL))
@WebServlet(name = "Bookstore",
        loadOnStartup = 1)
public class BookstoreServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bse", new BaseViewModel(request));
        request.setAttribute("crt", new CartViewModel(request));
    }

    // Forwards the request to [userPath].jsp
    protected void forwardToJsp(HttpServletRequest request,
                                HttpServletResponse response, String userPath) {

        String url = "/WEB-INF/jsp" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}