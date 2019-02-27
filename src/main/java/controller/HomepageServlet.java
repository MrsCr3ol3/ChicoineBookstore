package controller;

import viewmodel.CategoryViewModel;
import viewmodel.HomepageViewModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Homepage", urlPatterns = {"/home"})
public class HomepageServlet extends BookstoreServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        request.setAttribute("hm", new HomepageViewModel(request));
        request.setAttribute("cat", new CategoryViewModel(request));
        String userPath = "/homepage";
        String url = "/WEB-INF/jsp" + userPath + ".jsp";
        request.getRequestDispatcher(url).forward(request, response);
    }
}
