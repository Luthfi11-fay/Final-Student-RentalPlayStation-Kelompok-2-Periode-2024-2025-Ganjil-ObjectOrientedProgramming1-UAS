/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rental_playstation;

/**
 *
 * @author user
 */




import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private TextField usernameField;
    private PasswordField passwordField;
    private LoginHandler loginHandler;
    private Stage primaryStage;

    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loginHandler = new LoginHandler();
    }

    public VBox getView() {
        VBox loginBox = new VBox(10);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(20));
        loginBox.setStyle("-fx-background-color: #f0f0f0;");

        Label titleLabel = new Label("PlayStation Rental System");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(200);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        Button loginButton = new Button("Login");
        loginButton.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 8px 16px;"
        );
        loginButton.setOnAction(e -> handleLogin());

        loginBox.getChildren().addAll(
            titleLabel,
            new Label("Username:"),
            usernameField,
            new Label("Password:"),
            passwordField,
            loginButton
        );

        return loginBox;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginHandler.authenticate(username, password)) {
            showMainMenu();
        } else {
            showAlert("Login Failed", "Invalid username or password");
        }
    }

    private void showMainMenu() {
        try {
            MainMenuView mainMenuView = new MainMenuView(primaryStage);
            Scene scene = new Scene(mainMenuView.getView(), 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main Menu - PlayStation Rental System");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load main menu");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}