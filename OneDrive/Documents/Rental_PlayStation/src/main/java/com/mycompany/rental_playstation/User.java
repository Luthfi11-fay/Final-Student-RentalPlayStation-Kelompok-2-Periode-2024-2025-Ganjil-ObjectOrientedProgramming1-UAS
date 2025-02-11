package com.mycompany.rental_playstation;

import javafx.beans.property.*;

public class User {
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty role;
    private final BooleanProperty isActive;

    public User(String username, String password, String role) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
        this.isActive = new SimpleBooleanProperty(true);
    }

    // Getters
    public String getUsername() { return username.get(); }
    public String getPassword() { return password.get(); }
    public String getRole() { return role.get(); }
    public boolean isActive() { return isActive.get(); }

    // Property getters
    public StringProperty usernameProperty() { return username; }
    public StringProperty passwordProperty() { return password; }
    public StringProperty roleProperty() { return role; }
    public BooleanProperty isActiveProperty() { return isActive; }

    // Setters
    public void setPassword(String password) { this.password.set(password); }
    public void setRole(String role) { this.role.set(role); }
    public void setActive(boolean active) { this.isActive.set(active); }
}