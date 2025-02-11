package com.mycompany.rental_playstation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class RentalView {
    private RentalOperations rentalOps;
    private TableView<Console> consoleTable;
    private TableView<RentalReport> reportTable;
    private TextField customerNameField;
    private TextField roomField;
    private TextField hoursField;
    private Label selectedConsoleLabel;
    private Console selectedConsole;
    private ComboBox<String> consoleTypeFilter;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private User currentUser;

    public RentalView() {
        this(null);
    }

    public RentalView(User user) {
        this.currentUser = user;
        try {
            rentalOps = new RentalOperations();
        } catch (SQLException e) {
            showError("Database Error", "Failed to connect to database: " + e.getMessage());
        }
    }

    public VBox getView() {
        // Cek apakah user memiliki akses admin
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            VBox deniedContainer = new VBox(20);
            deniedContainer.setAlignment(Pos.CENTER);
            deniedContainer.setPadding(new Insets(20));
            
            Label deniedLabel = new Label("Access Denied");
            deniedLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: red;");
            
            Label detailLabel = new Label("This page requires administrator privileges.");
            detailLabel.setStyle("-fx-font-size: 16px;");
            
            deniedContainer.getChildren().addAll(deniedLabel, detailLabel);
            return deniedContainer;
        }

        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setAlignment(Pos.CENTER);

        Text headerText = new Text("PlayStation Rental Management");
        headerText.setFont(Font.font("System", FontWeight.BOLD, 20));

        VBox rentalSection = createRentalSection();
        VBox reportSection = createReportSection();

        mainContainer.getChildren().addAll(
            headerText,
            new Separator(),
            rentalSection,
            new Separator(),
            reportSection
        );

        refreshTables();

        return mainContainer;
    }

    
    private VBox createRentalSection() {
        VBox section = new VBox(15);
        section.setPadding(new Insets(10));

        Text sectionTitle = new Text("Console Rentals");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 16));

        consoleTable = createConsoleTable();
        VBox.setVgrow(consoleTable, Priority.ALWAYS);
        
        GridPane rentalForm = createRentalForm();
        rentalForm.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 10px;");

        HBox buttonBox = createActionButtons();

        section.getChildren().addAll(
            sectionTitle, 
            consoleTable, 
            rentalForm, 
            buttonBox
        );
        return section;
    }

    private VBox createReportSection() {
        VBox section = new VBox(15);
        section.setPadding(new Insets(10));

        Text sectionTitle = new Text("Rental Reports");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 16));

        HBox filterBox = createReportFilterBox();
        reportTable = createReportTable();
        VBox.setVgrow(reportTable, Priority.ALWAYS);

        section.getChildren().addAll(
            sectionTitle, 
            filterBox, 
            reportTable
        );
        return section;
    }

    private HBox createReportFilterBox() {
        HBox filterBox = new HBox(10);
    filterBox.setAlignment(Pos.CENTER_LEFT);
    filterBox.setPadding(new Insets(10.0, 0.0, 10.0, 0.0));
    
        consoleTypeFilter = new ComboBox<>();
        consoleTypeFilter.getItems().addAll("All", "PS3", "PS4", "PS5");
        consoleTypeFilter.setValue("All");
        consoleTypeFilter.setOnAction(e -> applyReportFilter());

        startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Start Date");
        startDatePicker.setOnAction(e -> applyReportFilter());

        endDatePicker = new DatePicker();
        endDatePicker.setPromptText("End Date");
        endDatePicker.setOnAction(e -> applyReportFilter());

        filterBox.getChildren().addAll(
            new Label("Console Type:"), 
            consoleTypeFilter, 
            new Label("From:"), 
            startDatePicker, 
            new Label("To:"), 
            endDatePicker
        );

        return filterBox;
    }

    private void applyReportFilter() {
        List<RentalReport> allReports = rentalOps.getRentalReports();
        
        List<RentalReport> filteredReports = allReports.stream()
            .filter(report -> {
                boolean consoleTypeMatch = consoleTypeFilter.getValue().equals("All") || 
                    report.getConsoleType().equals(consoleTypeFilter.getValue());
                
                LocalDate reportDate = LocalDate.parse(report.getDate());
                boolean dateRangeMatch = true;
                
                if (startDatePicker.getValue() != null) {
                    dateRangeMatch = dateRangeMatch && !reportDate.isBefore(startDatePicker.getValue());
                }
                
                if (endDatePicker.getValue() != null) {
                    dateRangeMatch = dateRangeMatch && !reportDate.isAfter(endDatePicker.getValue());
                }
                
                return consoleTypeMatch && dateRangeMatch;
            })
            .collect(Collectors.toList());

        reportTable.setItems(FXCollections.observableArrayList(filteredReports));
    }

    private TableView<Console> createConsoleTable() {
        TableView<Console> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Console, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());

        TableColumn<Console, String> roomColumn = new TableColumn<>("Room");
        roomColumn.setCellValueFactory(data -> data.getValue().roomProperty());

        TableColumn<Console, Boolean> availableColumn = new TableColumn<>("Status");
        availableColumn.setCellValueFactory(data -> data.getValue().isAvailableProperty());
        availableColumn.setCellFactory(col -> new TableCell<Console, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item ? "Available" : "Rented");
                    setStyle(item ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                }
            }
        });

        TableColumn<Console, String> rentedToColumn = new TableColumn<>("Rented To");
        rentedToColumn.setCellValueFactory(data -> data.getValue().rentedToProperty());

        TableColumn<Console, Number> hoursColumn = new TableColumn<>("Hours");
        hoursColumn.setCellValueFactory(data -> data.getValue().rentedHoursProperty());

        table.getColumns().addAll(typeColumn, roomColumn, availableColumn, rentedToColumn, hoursColumn);
        table.setMinHeight(200);
        table.setMaxHeight(300);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedConsole = newVal;
            updateSelectedConsoleLabel();
            if (newVal != null) {
                roomField.setText(newVal.getRoom());
            }
        });

        return table;
    }

    private TableView<RentalReport> createReportTable() {
        TableView<RentalReport> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<RentalReport, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());

        TableColumn<RentalReport, String> consoleColumn = new TableColumn<>("Console");
        consoleColumn.setCellValueFactory(data -> data.getValue().consoleTypeProperty());

        TableColumn<RentalReport, String> customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(data -> data.getValue().customerNameProperty());

        TableColumn<RentalReport, Number> hoursColumn = new TableColumn<>("Hours");
        hoursColumn.setCellValueFactory(data -> data.getValue().totalHoursProperty());

        TableColumn<RentalReport, Number> revenueColumn = new TableColumn<>("Revenue");
        revenueColumn.setCellValueFactory(data -> data.getValue().revenueProperty());
        revenueColumn.setCellFactory(col -> new TableCell<RentalReport, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("Rp. %.2f", item.doubleValue()));
                }
            }
        });

        table.getColumns().addAll(dateColumn, consoleColumn, customerColumn, hoursColumn, revenueColumn);
        table.setMinHeight(200);

        return table;
    }

    private GridPane createRentalForm() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));
        form.setAlignment(Pos.CENTER);

        selectedConsoleLabel = new Label("No console selected");
        selectedConsoleLabel.setStyle("-fx-font-weight: bold;");
        
        Label consoleLabel = new Label("Selected Console:");
        consoleLabel.setStyle("-fx-font-weight: bold;");
        
        form.add(consoleLabel, 0, 0);
        form.add(selectedConsoleLabel, 1, 0);

        customerNameField = new TextField();
        customerNameField.setPromptText("Enter customer name");
        addFormRow(form, "Customer Name:", customerNameField, 1);

        roomField = new TextField();
        roomField.setPromptText("Enter room number");
        roomField.setEditable(false);
        addFormRow(form, "Room:", roomField, 2);

        hoursField = new TextField();
        hoursField.setPromptText("Enter rental hours");
        addFormRow(form, "Hours:", hoursField, 3);

        return form;
    }

    private void addFormRow(GridPane form, String labelText, Control field, int row) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        form.add(label, 0, row);
        form.add(field, 1, row);
    }

    private HBox createActionButtons() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        Button rentButton = createStyledButton("Rent Console", "#4CAF50");
        rentButton.setOnAction(e -> handleRentConsole());

        Button endRentalButton = createStyledButton("End Rental", "#f44336");
        endRentalButton.setOnAction(e -> handleEndRental());

        Button refreshButton = createStyledButton("Refresh", "#2196F3");
        refreshButton.setOnAction(e -> refreshTables());

        buttonBox.getChildren().addAll(rentButton, endRentalButton, refreshButton);
        return buttonBox;
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(String.format(
            "-fx-background-color: %s; -fx-text-fill: white; " +
            "-fx-font-weight: bold; -fx-padding: 10 20; " +
            "-fx-cursor: hand;", color
        ));
        button.setMinWidth(120);
        return button;
    }

    private void handleRentConsole() {
        if (!validateRentalInput()) {
            return;
        }

        try {
            String customerName = customerNameField.getText();
            String room = roomField.getText();
            int hours = Integer.parseInt(hoursField.getText());

            rentalOps.rentConsole(customerName, room, hours);
            refreshTables();
            clearForm();
            showSuccess("Console rented successfully!");
        } catch (Exception e) {
            showError("Error", "Failed to rent console: " + e.getMessage());
        }
    }

    private void handleEndRental() {
        if (selectedConsole == null) {
            showError("Error", "Please select a console");
            return;
        }

        if (selectedConsole.isAvailable()) {
            showError("Error", "This console is not currently rented");
            return;
        }

        try {
            rentalOps.endRental(selectedConsole.getRoom());
            refreshTables();
            clearForm();
            showSuccess("Rental ended successfully!");
        } catch (Exception e) {
            showError("Error", "Failed to end rental: " + e.getMessage());
        }
    }

    private void refreshTables() {
        try {
            List<Console> consoles = rentalOps.getAllConsoles();
            consoleTable.setItems(FXCollections.observableArrayList(consoles));

            List<RentalReport> reports = rentalOps.getRentalReports();
            reportTable.setItems(FXCollections.observableArrayList(reports));
        } catch (Exception e) {
            showError("Error", "Failed to refresh data: " + e.getMessage());
        }
    }

    private void updateSelectedConsoleLabel() {
        if (selectedConsole != null) {
            selectedConsoleLabel.setText(String.format("%s (Room: %s)", 
                selectedConsole.getType(), selectedConsole.getRoom()));
        } else {
            selectedConsoleLabel.setText("No console selected");
        }
    }

    private void clearForm() {
        customerNameField.clear();
        roomField.clear();
        hoursField.clear();
        selectedConsole = null;
        updateSelectedConsoleLabel();
    }

    private boolean validateRentalInput() {
        if (selectedConsole == null) {
            showError("Validation Error", "Please select a console");
            return false;
        }

        if (customerNameField.getText().trim().isEmpty()) {
            showError("Validation Error", "Please enter customer name");
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

        if (!selectedConsole.isAvailable()) {
            showError("Validation Error", "Selected console is not available");
            return false;
        }

        return true;
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