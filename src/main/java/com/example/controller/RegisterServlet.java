package com.example.controller;

import com.example.model.User;
import com.example.service.RegisterService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

public class RegisterServlet extends HttpServlet {

    private RegisterService registerService;
    @Override
    public void init() throws ServletException {
        registerService = new RegisterService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/signup.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String hash = BCrypt.hashpw(req.getParameter("password"), BCrypt.gensalt());
        Long role = 1L;

        User userDTO = new User();
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPassword(hash);
        userDTO.setRole(role);

        boolean isRegister = registerService.createNewUser(userDTO);

        if (isRegister) {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/signup");
        }
    }
}
