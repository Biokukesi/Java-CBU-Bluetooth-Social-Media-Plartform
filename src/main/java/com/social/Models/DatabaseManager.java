package com.social.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbu_social_network", "root", "Biokukesi");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
    
    public boolean testConnection() {
    try (Statement stmt = connection.createStatement()) {
        ResultSet rs = stmt.executeQuery("SELECT 1");
        if(rs.next()) {
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    //  Register user
    public boolean registerUser(String username, String password, String email, String phoneNumber) throws SQLException {
        Statement stmt = connection.createStatement();
        String query = "INSERT INTO users (username, passwordHash, email, phoneNumber) VALUES ('" + username + "', '" + password + "', '" + email + "', '" + phoneNumber + "')";
        int result = stmt.executeUpdate(query);
        stmt.close();
        return result == 1;
    }
    // Login user
    public boolean loginUser(String username, String password) throws SQLException {
        Statement stmt = connection.createStatement();
        String query = "SELECT * FROM users WHERE username='" + username + "' AND passwordHash='" + password + "'";
        ResultSet rs = stmt.executeQuery(query);
        boolean result = rs.next();
        stmt.close();
        return result;
    }
      


    public void closeConnection() throws SQLException {
        this.connection.close();
    }


    
}