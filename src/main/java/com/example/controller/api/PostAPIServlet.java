package com.example.controller.api;

import com.example.common.SystemConst;
import com.example.model.Post;
import com.example.service.LoginService;
import com.example.service.PostService;
import com.example.utils.RespData;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PostAPIServlet", urlPatterns = SystemConst.URL_API + "/post")
public class PostAPIServlet extends HttpServlet {
    private PostService postService;
    private LoginService loginService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        postService = new PostService();
        loginService = new LoginService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        HttpSession session = req.getSession(false);
        String email = (String) session.getAttribute("email");
        Long currentUserId = loginService.getUserByEmail(email).getId();

        Post postDTO = new Post();
        postDTO.setTitle(title);
        postDTO.setDescription(description);
        postDTO.setUserId(currentUserId);

        Post post = postService.create(postDTO);

        RespData data = new RespData();
        if (post != null) {
            data.setStatus(true);
            data.setMessage("insert post successfully");
            data.setData(post);
        } else {
            data.setStatus(false);
            data.setMessage("fail !!!");
            data.setData("");
        }

        String json = gson.toJson(data);
//        return json servlet
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");

        Post postDTO = new Post();
        postDTO.setId(id);
        postDTO.setTitle(title);
        postDTO.setDescription(description);

        boolean checkExist = postService.getById(id) != null ? true : false;
        RespData data = new RespData();
        if (checkExist) {
            Post post = postService.update(postDTO);
            if (post != null) {
                data.setStatus(true);
                data.setMessage("update post successfully");
                data.setData(post);
            } else {
                data.setStatus(false);
                data.setMessage("fail !!!");
                data.setData("");
            }
        } else {
            data.setStatus(false);
            data.setMessage("not found post with id");
            data.setData("");
        }


        String json = gson.toJson(data);
//        return json servlet
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        boolean isDelete =  postService.deleteById(id);

        RespData data = new RespData();
        data.setStatus(isDelete);
        data.setMessage("");
        data.setData("");

        String json = gson.toJson(data);

//        return json servlet
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
