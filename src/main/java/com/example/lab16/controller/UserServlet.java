package com.example.lab16.controller;

import com.example.lab16.dao.UserDAO;
import com.example.lab16.entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        try {
            switch (action) {
                case "/new":
                    showNewFrom(req, resp);
                    break;
                case "/insert":
                    insertUser(req, resp);
                    break;
                case "/edit":
                    showEditFrom(req, resp);
                    break;
                case "/update":
                    updateUser(req, resp);
                    break;
                case "/delete":
                    deleteUser(req, resp);
                    break;
                case "/list":
                default:
                    listUsers(req, resp);
                    break;
            }
        }catch (SQLException e){
            throw new ServletException(e);
        }

    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<User> list = userDAO.selectAllUsers();
        req.setAttribute("listUser", list);

        RequestDispatcher dispatcher = req.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(req, resp);
    }

    private void showNewFrom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditFrom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = userDAO.selectUser(id);
        req.setAttribute("user", user);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User newUser = new User(id, name, email, country);
        userDAO.updateUser(newUser);
        resp.sendRedirect(req.getContextPath() + "/list");

    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        userDAO.deleteUser(id);
        resp.sendRedirect(req.getContextPath() + "/list");

    }
    private void insertUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User newUser = new User(name, email, country);
        userDAO.insertUser(newUser);
        resp.sendRedirect(req.getContextPath() + "/list");


    }
}
