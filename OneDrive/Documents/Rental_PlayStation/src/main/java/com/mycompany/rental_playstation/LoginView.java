package com.mycompany.rental_playstation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;

public class LoginView {
    private TextField usernameField;
    private PasswordField passwordField;
    private LoginHandler loginHandler;
    private Stage primaryStage;
    private static User currentUser;

    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loginHandler = new LoginHandler();
    }
    
    public VBox getView() {
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(40));
        mainContainer.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a237e, #0d47a1);");

        // Create a card-like container
        VBox loginCard = new VBox(15);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setPadding(new Insets(30));
        loginCard.setMaxWidth(400);
        loginCard.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        // Add drop shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        shadow.setRadius(10);
        loginCard.setEffect(shadow);

        // Title
        Label titleLabel = new Label("PlayStation Rental System");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #1a237e;");

        // Subtitle
        Label subtitleLabel = new Label("Sign in to continue");
        subtitleLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        subtitleLabel.setStyle("-fx-text-fill: #757575;");

        // Username field
        usernameField = createStyledTextField("Username", "person");
        
        // Password field
        passwordField = createStyledPasswordField();

        // Login button
        Button loginButton = new Button("LOGIN");
        loginButton.setStyle(
            "-fx-background-color: #1a237e;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 12 30;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setOnAction(e -> handleLogin());

        // Register link
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register here");
        registerLink.setStyle("-fx-text-fill: #1a237e;");
        registerLink.setOnAction(e -> showRegistrationDialog());

        loginCard.getChildren().addAll(
            titleLabel,
            subtitleLabel,
            new Separator(),
            usernameField,
            passwordField,
            loginButton,
            registerLink
        );

        mainContainer.getChildren().add(loginCard);
        return mainContainer;
    }

    private TextField createStyledTextField(String prompt, String iconName) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle(
            "-fx-background-color: #f5f5f5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10 15;" +
            "-fx-font-size: 14px;"
        );
        field.setMaxWidth(Double.MAX_VALUE);
        return field;
    }

    private PasswordField createStyledPasswordField() {
        PasswordField field = new PasswordField();
        field.setPromptText("Password");
        field.setStyle(
            "-fx-background-color: #f5f5f5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10 15;" +
            "-fx-font-size: 14px;"
        );
        field.setMaxWidth(Double.MAX_VALUE);
        return field;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = loginHandler.authenticate(username, password);
        if (user != null && user.isActive()) {
            currentUser = user;
            navigateToMainMenu(user);
        } else {
            showAlert("Login Failed", "Invalid username or password", Alert.AlertType.ERROR);
        }
    }

    private void navigateToMainMenu(User user) {
        try {
            MainMenuView mainMenuView = new MainMenuView(primaryStage, user);
            Scene mainMenuScene = new Scene(mainMenuView.getView(), 600, 500);
            primaryStage.setScene(mainMenuScene);
            primaryStage.setTitle("Main Menu - PlayStation Rental System");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load main menu", Alert.AlertType.ERROR);
        }
    }

    private void showRegistrationDialog() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Register New User");
        dialog.setHeaderText("Create your account");

        // Create the registration form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField regUsername = createStyledTextField("Username", "person");
        PasswordField regPassword = createStyledPasswordField();
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("USER", "ADMIN");
        roleCombo.setValue("USER");
        roleCombo.setStyle(
            "-fx-background-color: #f5f5f5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10 15;" +
            "-fx-font-size: 14px;"
        );

        grid.add(new Label("Username:"), 0, 0);
        grid.add(regUsername, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(regPassword, 1, 1);
        grid.add(new Label("Role:"), 0, 2);
        grid.add(roleCombo, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new User(
                    regUsername.getText(),
                    regPassword.getText(),
                    roleCombo.getValue()
                );
            }
            return null;
        });

        dialog.showAndWait().ifPresent(user -> {
            if (loginHandler.registerUser(user)) {
                showAlert("Success", "Registration successful!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Registration failed!", Alert.AlertType.ERROR);
            }
        });
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}