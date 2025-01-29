/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rental_playstation;

/**
 *
 * @author Lutfi Fathillah
 */
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RentalOperations {
    private Connection connection;
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RentalOperations() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    public void rentConsole(String customerName, String room, int hours) {
        String query = "UPDATE consoles SET is_available = FALSE, " +
                      "rented_to = ?, rented_hours = ?, rented_time = ? WHERE room = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerName);
            stmt.setInt(2, hours);
            stmt.setString(3, LocalDateTime.now().format(formatter));
            stmt.setString(4, room);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Console> getAllConsoles() {
        List<Console> consoles = new ArrayList<>();
        String query = "SELECT * FROM consoles";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Console console;
                String type = rs.getString("type");
                String room = rs.getString("room");
                
                switch (type) {
                    case "PS3":
                        console = new PS3(room);
                        break;
                    case "PS4":
                        console = new PS4(room);
                        break;
                    case "PS5":
                        console = new PS5(room);
                        break;
                    default:
                        continue;
                }
                
                console.setAvailable(rs.getBoolean("is_available"));
                console.setRentedTo(rs.getString("rented_to"));
                console.setRentedHours(rs.getInt("rented_hours"));
                console.setRentedTime(rs.getString("rented_time"));
                consoles.add(console);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consoles;
    }

    public void endRental(String room) {
        String query = "UPDATE consoles SET is_available = TRUE, " +
                      "rented_to = NULL, rented_hours = 0, rented_time = NULL WHERE room = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, room);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}