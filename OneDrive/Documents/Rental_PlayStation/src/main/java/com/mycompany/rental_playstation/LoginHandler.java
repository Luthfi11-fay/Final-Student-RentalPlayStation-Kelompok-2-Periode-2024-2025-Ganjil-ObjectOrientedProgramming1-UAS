package com.mycompany.rental_playstation;

import java.sql.*;

public class LoginHandler {
    private Connection connection;

    public LoginHandler() {
        connection = DatabaseConnection.getConnection();
    }

    public User authenticate(String username, String password) {
        String query = "SELECT u.username, r.role_name, u.is_active " +
                      "FROM users u " +
                      "JOIN roles r ON u.role_id = r.role_id " +
                      "WHERE u.username = ? AND u.password = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role_name");
                boolean isActive = rs.getBoolean("is_active");
                User user = new User(username, password, role);
                user.setActive(isActive);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, password, role_id, is_active) " +
                      "SELECT ?, ?, r.role_id, ? " +
                      "FROM roles r WHERE r.role_name = ?";
                      
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setBoolean(3, user.isActive());
            stmt.setString(4, user.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String username, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}