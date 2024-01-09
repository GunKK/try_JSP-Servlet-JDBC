package com.example.controller;

import com.example.model.Post;
import com.example.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "PostServlet", urlPatterns = "/post/*")
public class PostServlet extends HttpServlet {

    private PostService postService;

    @Override
    public void init() throws ServletException {
        postService = new PostService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        Post post = postService.getById(id);
        req.setAttribute("post", post);
        RequestDispatcher rd = req.getRequestDispatcher("/post.jsp");
        rd.forward(req, resp);
    }
}
