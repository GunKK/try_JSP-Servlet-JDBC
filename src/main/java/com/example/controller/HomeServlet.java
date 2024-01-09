package com.example.controller;

import com.example.service.PostService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.example.model.Post;
import com.example.utils.RespData;
import com.google.gson.Gson;

@WebServlet(name = "HomeServlet", urlPatterns = "/")
public class HomeServlet extends HttpServlet {

    private Gson gson;
    private PostService postService;

    @Override
    public void init() throws ServletException {
        postService = new PostService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        if (page == null) {
            List<Post> posts = postService.getAll();
            req.setAttribute("posts", posts);
            RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
            rd.forward(req, resp);
        } else {
            switch (page) {
                case "about":
                    RequestDispatcher rd = req.getRequestDispatcher("/about.jsp");
                    rd.forward(req, resp);
                    break;

                case "contact":
                    rd = req.getRequestDispatcher("/contact.jsp");
                    rd.forward(req, resp);
                    break;
                default:
                    RespData data = new RespData();
                    data.setStatus(false);
                    data.setMessage("404");
                    data.setData("page not found!!!");

                    String json = gson.toJson(data);

                    req.setCharacterEncoding("UTF-8");
                    resp.setContentType("application/json");
                    PrintWriter out = resp.getWriter();
                    out.print(json);
                    out.flush();
            }
        }
    }
}
