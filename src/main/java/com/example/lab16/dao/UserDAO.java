package com.example.lab16.dao;

import com.example.lab16.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    private static final String INSERT_USER = "INSERT INTO users(name,email,country) VALUES(?,?,?)";
    private static final String SELECT_USER_BY_ID = "SELECT id,name,email,country  FROM users WHERE id=?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE users SET name = ?, email = ?, country = ? WHERE id = ?";

    private static final String SELECT_ALL_USERS = "SELECT *  FROM users";
    public void insertUser(User user) throws SQLException {
        conn = DBConnection.getMySQLConnection();
        ps = conn.prepareStatement(INSERT_USER);
        ps.setString(1, user.getName());
        ps.setString(2,user.getEmail());
        ps.setString(3, user.getCountry());
        ps.execute();

    }
    public User selectUser(int id) throws SQLException {
        User user = null;
        conn = DBConnection.getMySQLConnection();
        ps = conn.prepareStatement(SELECT_USER_BY_ID);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if(rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setCountry(rs.getString("country"));

        }
        return user;
    }
    public List<User> selectAllUsers() throws SQLException {
        List<User> users = new ArrayList<User>();
        conn = DBConnection.getMySQLConnection();
        ps = conn.prepareStatement(SELECT_ALL_USERS);
        rs = ps.executeQuery();
        while(rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setCountry(rs.getString("country"));
            users.add(user);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        conn = DBConnection.getMySQLConnection();
        ps = conn.prepareStatement(DELETE_USER);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    public boolean updateUser(User user) throws SQLException {
        conn = DBConnection.getMySQLConnection();
        ps = conn.prepareStatement(UPDATE_USER);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getCountry());
        ps.setInt(4, user.getId());
        return ps.executeUpdate() > 0;

    }
}
