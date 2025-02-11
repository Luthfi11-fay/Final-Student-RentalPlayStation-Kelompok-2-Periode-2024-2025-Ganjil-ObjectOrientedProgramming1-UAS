package com.mycompany.rental_playstation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class MainMenuView {
    private Stage primaryStage;
    private Scene previousScene;
    private User currentUser;

    public MainMenuView(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.currentUser = user;
    }

    public VBox getView() {
    VBox menuBox = new VBox(20);
    menuBox.setAlignment(Pos.CENTER);
    menuBox.setPadding(new Insets(30));
    menuBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a237e, #0d47a1);");

    // Create a card for the menu
    VBox menuCard = new VBox(20);
    menuCard.setAlignment(Pos.CENTER);
    menuCard.setPadding(new Insets(30));
    menuCard.setMaxWidth(600);
    menuCard.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

    // Add drop shadow effect
    DropShadow shadow = new DropShadow();
    shadow.setColor(Color.rgb(0, 0, 0, 0.3));
    shadow.setRadius(10);
    menuCard.setEffect(shadow);

    // Header
    Text headerText = new Text("PlayStation Rental System");
    headerText.setFont(Font.font("System", FontWeight.BOLD, 24));
    headerText.setStyle("-fx-fill: #1a237e;");

    // Welcome text
    Text welcomeText = new Text("Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
    welcomeText.setFont(Font.font("System", FontWeight.NORMAL, 16));
    welcomeText.setStyle("-fx-fill: #757575;");

    menuCard.getChildren().addAll(
        headerText,
        welcomeText,
        createSeparator()
    );

    // Book Console (for all users)
    Button bookingBtn = createMenuButton("Book Console", () -> showBooking());
    menuCard.getChildren().add(bookingBtn);

    // My Bookings (only for non-admin users)
    if (!"ADMIN".equals(currentUser.getRole())) {
        Button myBookingsBtn = createMenuButton("My Bookings", () -> showMyBookings());
        menuCard.getChildren().add(myBookingsBtn);
    }
    
    // Rental Management (admin only)
    if ("ADMIN".equals(currentUser.getRole())) {
        Button rentalManagementBtn = createMenuButton("Rental Management", () -> showRentalManagement());
        menuCard.getChildren().add(rentalManagementBtn);
    }
    
    // User Management (admin only)
    if ("ADMIN".equals(currentUser.getRole())) {
        Button userManagementBtn = createMenuButton("User Management", () -> showUserManagement());
        menuCard.getChildren().add(userManagementBtn);
    }

    // Logout button
    menuCard.getChildren().addAll(
        createSeparator(),
        createMenuButton("Logout", () -> handleLogout())
    );

    menuBox.getChildren().add(menuCard);

    return menuBox;
}
    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setMaxWidth(300);
        button.setMinWidth(200);
        button.setStyle(
            "-fx-background-color: #1a237e;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 15 30;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        button.setOnAction(e -> action.run());
        return button;
    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        return separator;
    }

    private void showBooking() {
        try {
            BookingView bookingView = new BookingView(currentUser);
            VBox bookingContent = bookingView.getView();
            switchToScene(bookingContent, "Book Console");
        } catch (Exception e) {
            showError("Error loading Booking", e.getMessage());
        }
    }

    private void showMyBookings() {
        try {
            MyBookingsView myBookingsView = new MyBookingsView(currentUser);
            VBox myBookingsContent = myBookingsView.getView();
            switchToScene(myBookingsContent, "My Bookings");
        } catch (Exception e) {
            showError("Error loading My Bookings", e.getMessage());
        }
    }

    private void showRentalManagement() {
        try {
            RentalView rentalView = new RentalView(currentUser);
            VBox rentalContent = rentalView.getView();
            switchToScene(rentalContent, "Rental Management");
        } catch (Exception e) {
            showError("Error loading Rental Management", e.getMessage());
        }
    }

    private void showUserManagement() {
        try {
            UserManagementView userManagementView = new UserManagementView(currentUser);
            VBox userManagementContent = userManagementView.getView();
            switchToScene(userManagementContent, "User Management");
        } catch (Exception e) {
            showError("Error loading User Management", e.getMessage());
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
        container.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a237e, #0d47a1);");
        
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: #1a237e;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10 20;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        backButton.setOnAction(e -> primaryStage.setScene(previousScene));
        
        container.getChildren().addAll(backButton, content);
        
        Scene newScene = new Scene(container, 1000, 600);
        primaryStage.setScene(newScene);
        primaryStage.setTitle(title + " - PlayStation Rental System");
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}