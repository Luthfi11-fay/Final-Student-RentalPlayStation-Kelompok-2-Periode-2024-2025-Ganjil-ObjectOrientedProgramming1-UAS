package com.mycompany.rental_playstation;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MyBookingsView {
    private RentalOperations rentalOps;
    private TableView<RentalReport> bookingsTable;
    private final User currentUser;

    public MyBookingsView(User user) {
        this.currentUser = user;
        try {
            this.rentalOps = new RentalOperations();
        } catch (SQLException e) {
            showError("Database Error", "Failed to connect to database: " + e.getMessage());
        }
    }

    public VBox getView() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: white;");

        // Header
        Text headerText = new Text("My Bookings");
        headerText.setFont(Font.font("System", FontWeight.BOLD, 24));
        headerText.setStyle("-fx-fill: #1a237e;");

        // Bookings Table
        bookingsTable = createBookingsTable();
        VBox.setVgrow(bookingsTable, Priority.ALWAYS);

        mainContainer.getChildren().addAll(
            headerText,
            new Separator(),
            bookingsTable
        );

        refreshBookings();

        return mainContainer;
    }

    private TableView<RentalReport> createBookingsTable() {
        TableView<RentalReport> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<RentalReport, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());

        TableColumn<RentalReport, String> consoleColumn = new TableColumn<>("Console Type");
        consoleColumn.setCellValueFactory(data -> data.getValue().consoleTypeProperty());

        TableColumn<RentalReport, Number> hoursColumn = new TableColumn<>("Hours");
        hoursColumn.setCellValueFactory(data -> data.getValue().totalHoursProperty());

        TableColumn<RentalReport, Number> costColumn = new TableColumn<>("Total Cost (Rp)");
        costColumn.setCellValueFactory(data -> data.getValue().revenueProperty());

        table.getColumns().addAll(dateColumn, consoleColumn, hoursColumn, costColumn);
        return table;
    }

    private void refreshBookings() {
        List<RentalReport> allReports = rentalOps.getRentalReports();
        List<RentalReport> userReports = allReports.stream()
            .filter(report -> report.getCustomerName().equals(currentUser.getUsername()))
            .collect(Collectors.toList());
        bookingsTable.setItems(FXCollections.observableArrayList(userReports));
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}