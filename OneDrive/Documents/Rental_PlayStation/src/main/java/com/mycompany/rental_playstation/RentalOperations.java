package com.mycompany.rental_playstation;

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
        try {
            // Mulai transaksi
            connection.setAutoCommit(false);

            // Update status konsol
            String consoleQuery = "UPDATE consoles SET " +
                "is_available = FALSE, " +
                "rented_to = ?, " +
                "rented_hours = ?, " +
                "rented_time = ? " +
                "WHERE room = ?";
            
            try (PreparedStatement consoleStmt = connection.prepareStatement(consoleQuery)) {
                consoleStmt.setString(1, customerName);
                consoleStmt.setInt(2, hours);
                consoleStmt.setString(3, LocalDateTime.now().format(formatter));
                consoleStmt.setString(4, room);
                consoleStmt.executeUpdate();
            }

            // Tambahkan laporan rental
            String reportQuery = "INSERT INTO rental_reports " +
                "(rental_date, console_type, customer_name, room, rental_hours, total_revenue) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement reportStmt = connection.prepareStatement(reportQuery)) {
                // Ambil informasi konsol
                String consoleType = getConsoleTypeByRoom(room);
                double hourlyRate = getHourlyRateByRoom(room);
                double totalRevenue = hours * hourlyRate;

                reportStmt.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                reportStmt.setString(2, consoleType);
                reportStmt.setString(3, customerName);
                reportStmt.setString(4, room);
                reportStmt.setInt(5, hours);
                reportStmt.setDouble(6, totalRevenue);
                reportStmt.executeUpdate();
            }

            // Commit transaksi
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaksi jika terjadi kesalahan
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Kembalikan ke mode auto-commit
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void endRental(String room) {
        String query = "UPDATE consoles SET " +
            "is_available = TRUE, " +
            "rented_to = NULL, " +
            "rented_hours = 0, " +
            "rented_time = NULL " +
            "WHERE room = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, room);
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

    public List<RentalReport> getRentalReports() {
        List<RentalReport> reports = new ArrayList<>();
        String query = "SELECT * FROM rental_reports ORDER BY rental_date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                RentalReport report = new RentalReport(
                    rs.getString("rental_date"),
                    rs.getString("console_type"),
                    rs.getString("customer_name"),
                    rs.getInt("rental_hours"),
                    rs.getDouble("total_revenue")
                );
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    private String getConsoleTypeByRoom(String room) throws SQLException {
        String query = "SELECT type FROM consoles WHERE room = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, room);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("type");
            }
        }
        return "Unknown";
    }

    private double getHourlyRateByRoom(String room) throws SQLException {
        String query = "SELECT hourly_rate FROM consoles WHERE room = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, room);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("hourly_rate");
            }
        }
        return 0;
    }
}