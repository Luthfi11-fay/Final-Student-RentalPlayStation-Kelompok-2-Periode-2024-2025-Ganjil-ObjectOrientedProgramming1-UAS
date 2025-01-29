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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenuView {
    private Stage primaryStage;
    private Scene previousScene;

    public MainMenuView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public VBox getView() {
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(30));
        menuBox.setStyle("-fx-background-color: #f0f0f0;");

        // Header
        Text headerText = new Text("PlayStation Rental System");
        headerText.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Menu buttons
        Button rentalManagementBtn = createMenuButton("Rental Management", () -> showRentalManagement());         
        Button logoutBtn = createMenuButton("Logout", () -> handleLogout());

        menuBox.getChildren().addAll(
            headerText,
            createSeparator(),
            rentalManagementBtn,
            createSeparator(),
            logoutBtn
        );

        return menuBox;
    }

    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setMaxWidth(200);
        button.setMinWidth(200);
        button.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10px 20px;"
        );
        button.setOnAction(e -> action.run());
        return button;
    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        return separator;
    }

    private void showRentalManagement() {
        try {
            RentalView rentalView = new RentalView();
            switchToScene(rentalView.getView(), "Rental Management");
        } catch (Exception e) {
            showError("Error loading Rental Management", e.getMessage());
        }
    }

    
    

    private void handleLogout() {
        try {
            LoginView loginView = new LoginView(primaryStage);
            Scene loginScene = new Scene(loginView.getView(), 400, 300);
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Login - PlayStation Rental System");
        } catch (Exception e) {
            showError("Error during logout", e.getMessage());
        }
    }

    private void switchToScene(VBox content, String title) {
        previousScene = primaryStage.getScene();
        
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> primaryStage.setScene(previousScene));
        
        container.getChildren().addAll(backButton, content);
        
        Scene newScene = new Scene(container, 1000, 600);
        primaryStage.setScene(newScene);
        primaryStage.setTitle(title + " - PlayStation Rental System");
    }

    private void showUnderConstruction(String feature) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Under Construction");
        alert.setHeaderText(null);
        alert.setContentText(feature + " is currently under construction.");
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