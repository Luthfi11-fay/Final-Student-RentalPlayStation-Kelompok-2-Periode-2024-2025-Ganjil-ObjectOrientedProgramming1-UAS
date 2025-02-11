package com.mycompany.rental_playstation;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.List;

public class UserManagementView {
    private UserOperations userOps;
    private TableView<User> userTable;
    private User selectedUser;
    private User currentUser;

    public UserManagementView() {
        this.userOps = new UserOperations();
        this.currentUser = null;
    }

    public UserManagementView(User user) {
        this.userOps = new UserOperations();
        this.currentUser = user;
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
        mainContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        // Header
        Text headerText = new Text("User Management");
        headerText.setFont(Font.font("System", FontWeight.BOLD, 24));
        headerText.setStyle("-fx-fill: #1a237e;");

        // Create sections
        VBox userTableSection = createUserTableSection();
        HBox actionButtonsSection = createActionButtons();

        mainContainer.getChildren().addAll(
            headerText,
            new Separator(),
            userTableSection,
            actionButtonsSection
        );

        refreshUserTable();

        return mainContainer;
    }

    private VBox createUserTableSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));

        userTable = new TableView<>();
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(data -> data.getValue().usernameProperty());
        usernameCol.setPrefWidth(150);

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(data -> data.getValue().roleProperty());
        roleCol.setPrefWidth(100);

        TableColumn<User, Boolean> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> data.getValue().isActiveProperty());
        statusCol.setCellFactory(col -> new TableCell<User, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Active" : "Inactive");
                    setStyle(item ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                }
            }
        });
        statusCol.setPrefWidth(100);

        userTable.getColumns().addAll(usernameCol, roleCol, statusCol);
        userTable.setMinHeight(300);

        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedUser = newVal;
        });

        section.getChildren().add(userTable);
        VBox.setVgrow(userTable, Priority.ALWAYS);

        return section;
    }

    private HBox createActionButtons() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        Button addButton = createStyledButton("Add User", "#4CAF50");
        addButton.setOnAction(e -> showAddUserDialog());

        Button editButton = createStyledButton("Edit User", "#2196F3");
        editButton.setOnAction(e -> showEditUserDialog());

        Button toggleStatusButton = createStyledButton("Toggle Status", "#FF9800");
        toggleStatusButton.setOnAction(e -> toggleUserStatus());

        Button resetPasswordButton = createStyledButton("Reset Password", "#9C27B0");
        resetPasswordButton.setOnAction(e -> showResetPasswordDialog());

        buttonBox.getChildren().addAll(addButton, editButton, toggleStatusButton, resetPasswordButton);
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

    private void showAddUserDialog() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Add New User");
        dialog.setHeaderText("Enter user details");

        // Create the form grid
        GridPane grid = createUserFormGrid();

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        ComboBox<String> roleCombo = new ComboBox<>(FXCollections.observableArrayList("USER", "ADMIN"));
        roleCombo.setValue("USER");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Role:"), 0, 2);
        grid.add(roleCombo, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new User(
                    usernameField.getText(),
                    passwordField.getText(),
                    roleCombo.getValue()
                );
            }
            return null;
        });

        dialog.showAndWait().ifPresent(user -> {
            if (userOps.addUser(user)) {
                showAlert("Success", "User added successfully", Alert.AlertType.INFORMATION);
                refreshUserTable();
            } else {
                showAlert("Error", "Failed to add user", Alert.AlertType.ERROR);
            }
        });
    }

    private void showEditUserDialog() {
        if (selectedUser == null) {
            showAlert("Error", "Please select a user to edit", Alert.AlertType.ERROR);
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit User Role");
        dialog.setHeaderText("Edit role for user: " + selectedUser.getUsername());

        GridPane grid = createUserFormGrid();
        ComboBox<String> roleCombo = new ComboBox<>(FXCollections.observableArrayList("USER", "ADMIN"));
        roleCombo.setValue(selectedUser.getRole());

        grid.add(new Label("Role:"), 0, 0);
        grid.add(roleCombo, 1, 0);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return roleCombo.getValue();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(role -> {
            if (userOps.updateUserRole(selectedUser.getUsername(), role)) {
                showAlert("Success", "User role updated successfully", Alert.AlertType.INFORMATION);
                refreshUserTable();
            } else {
                showAlert("Error", "Failed to update user role", Alert.AlertType.ERROR);
            }
        });
    }

    private void toggleUserStatus() {
        if (selectedUser == null) {
            showAlert("Error", "Please select a user", Alert.AlertType.ERROR);
            return;
        }

        if (selectedUser.getUsername().equals("admin")) {
            showAlert("Error", "Cannot modify admin status", Alert.AlertType.ERROR);
            return;
        }

        boolean newStatus = !selectedUser.isActive();
        if (userOps.updateUserStatus(selectedUser.getUsername(), newStatus)) {
            showAlert("Success", "User status updated successfully", Alert.AlertType.INFORMATION);
            refreshUserTable();
        } else {
            showAlert("Error", "Failed to update user status", Alert.AlertType.ERROR);
        }
    }

    private void showResetPasswordDialog() {
        if (selectedUser == null) {
            showAlert("Error", "Please select a user", Alert.AlertType.ERROR);
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Reset Password");
        dialog.setHeaderText("Reset password for user: " + selectedUser.getUsername());

        GridPane grid = createUserFormGrid();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();

        grid.add(new Label("New Password:"), 0, 0);
        grid.add(passwordField, 1, 0);
        grid.add(new Label("Confirm Password:"), 0, 1);
        grid.add(confirmPasswordField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                    showAlert("Error", "Passwords do not match", Alert.AlertType.ERROR);
                    return null;
                }
                return passwordField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(password -> {
            if (password != null && userOps.resetPassword(selectedUser.getUsername(), password)) {
                showAlert("Success", "Password reset successfully", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to reset password", Alert.AlertType.ERROR);
            }
        });
    }

    private GridPane createUserFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        return grid;
    }

    private void refreshUserTable() {
        List<User> users = userOps.getAllUsers();
        userTable.setItems(FXCollections.observableArrayList(users));
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}