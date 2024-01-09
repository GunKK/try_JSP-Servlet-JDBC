package com.example.controller;

import com.example.model.User;
import com.example.service.LoginService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private LoginService loginService;
    @Override
    public void init() throws ServletException {
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (loginService.authenticate(email, password)) {
            HttpSession session = req.getSession();
            User user = loginService.getUserByEmail(email);
            session.setAttribute("email",email);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
