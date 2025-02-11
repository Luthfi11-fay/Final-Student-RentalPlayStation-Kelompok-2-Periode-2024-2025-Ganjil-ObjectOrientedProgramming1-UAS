package com.mycompany.rental_playstation;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class BookingView {
    private RentalOperations rentalOps;
    private TableView<Console> consoleTable;
    private TextField hoursField;
    private final User currentUser;
    private Console selectedConsole;

    public BookingView(User user) {
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
        Text headerText = new Text("PlayStation Booking");
        headerText.setFont(Font.font("System", FontWeight.BOLD, 24));
        headerText.setStyle("-fx-fill: #1a237e;");

        // Available Consoles Table
        consoleTable = createConsoleTable();
        VBox.setVgrow(consoleTable, Priority.ALWAYS);
        
        // Booking Form
        VBox bookingForm = createBookingForm();

        mainContainer.getChildren().addAll(
            headerText,
            new Separator(),
            new Label("Available Consoles:"),
            consoleTable,
            bookingForm
        );

        refreshAvailableConsoles();

        return mainContainer;
    }

    private TableView<Console> createConsoleTable() {
        TableView<Console> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Console, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());

        TableColumn<Console, String> roomColumn = new TableColumn<>("Room");
        roomColumn.setCellValueFactory(data -> data.getValue().roomProperty());

        TableColumn<Console, Number> rateColumn = new TableColumn<>("Rate/Hour (Rp)");
        rateColumn.setCellValueFactory(data -> data.getValue().hourlyRateProperty());

        table.getColumns().addAll(typeColumn, roomColumn, rateColumn);
        table.setMinHeight(200);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedConsole = newVal;
        });

        return table;
    }

    private VBox createBookingForm() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5;");

        Label formTitle = new Label("Booking Details");
        formTitle.setFont(Font.font("System", FontWeight.BOLD, 16));

        hoursField = new TextField();
        hoursField.setPromptText("Enter number of hours");
        hoursField.setMaxWidth(200);

        Button bookButton = createStyledButton("Book Console", "#4CAF50");
        bookButton.setOnAction(e -> handleBooking());

        form.getChildren().addAll(
            formTitle,
            new Label("Hours:"),
            hoursField,
            bookButton
        );

        return form;
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(String.format(
            "-fx-background-color: %s; -fx-text-fill: white; " +
            "-fx-font-weight: bold; -fx-padding: 10 20; " +
            "-fx-cursor: hand;", color
        ));
        button.setMaxWidth(200);
        return button;
    }

    private void handleBooking() {
        if (!validateBookingInput()) {
            return;
        }

        try {
            int hours = Integer.parseInt(hoursField.getText().trim());
            rentalOps.rentConsole(currentUser.getUsername(), selectedConsole.getRoom(), hours);
            
            showSuccess("Console booked successfully!");
            clearForm();
            refreshAvailableConsoles();
        } catch (Exception e) {
            showError("Booking Error", "Failed to book console: " + e.getMessage());
        }
    }

    private boolean validateBookingInput() {
        if (selectedConsole == null) {
            showError("Validation Error", "Please select a console");
            return false;
        }

        try {
            int hours = Integer.parseInt(hoursField.getText().trim());
            if (hours <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showError("Validation Error", "Please enter valid number of hours");
            return false;
        }

        return true;
    }

    private void refreshAvailableConsoles() {
        List<Console> allConsoles = rentalOps.getAllConsoles();
        List<Console> availableConsoles = allConsoles.stream()
            .filter(Console::isAvailable)
            .collect(Collectors.toList());
        consoleTable.setItems(FXCollections.observableArrayList(availableConsoles));
    }

    private void clearForm() {
        hoursField.clear();
        selectedConsole = null;
        consoleTable.getSelectionModel().clearSelection();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}