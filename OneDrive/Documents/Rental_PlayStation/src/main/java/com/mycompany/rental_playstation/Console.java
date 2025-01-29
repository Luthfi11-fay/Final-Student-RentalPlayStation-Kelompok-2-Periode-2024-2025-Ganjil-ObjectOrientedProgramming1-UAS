/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rental_playstation;

/**
 *
 * @author Luthfi Fathillah
 */
import javafx.beans.property.*;

public abstract class Console {
    protected IntegerProperty hourlyRate;
    protected StringProperty type;
    protected StringProperty room;
    protected BooleanProperty isAvailable;
    protected StringProperty rentedTo;
    protected IntegerProperty rentedHours;
    protected StringProperty rentedTime;

    public Console(String type, int hourlyRate, String room) {
        this.type = new SimpleStringProperty(type);
        this.hourlyRate = new SimpleIntegerProperty(hourlyRate);
        this.room = new SimpleStringProperty(room);
        this.isAvailable = new SimpleBooleanProperty(true);
        this.rentedTo = new SimpleStringProperty("");
        this.rentedHours = new SimpleIntegerProperty(0);
        this.rentedTime = new SimpleStringProperty("");
    }

    // Abstract methods that must be implemented by subclasses
    public abstract void calculateRentalCost();
    public abstract void checkMaintenance();

    // Getters and Property methods
    public int getHourlyRate() { return hourlyRate.get(); }
    public IntegerProperty hourlyRateProperty() { return hourlyRate; }
    
    public String getType() { return type.get(); }
    public StringProperty typeProperty() { return type; }
    
    public String getRoom() { return room.get(); }
    public StringProperty roomProperty() { return room; }
    
    public boolean isAvailable() { return isAvailable.get(); }
    public BooleanProperty isAvailableProperty() { return isAvailable; }
    
    public String getRentedTo() { return rentedTo.get(); }
    public StringProperty rentedToProperty() { return rentedTo; }
    
    public int getRentedHours() { return rentedHours.get(); }
    public IntegerProperty rentedHoursProperty() { return rentedHours; }
    
    public String getRentedTime() { return rentedTime.get(); }
    public StringProperty rentedTimeProperty() { return rentedTime; }
    
    // Setters
    public void setAvailable(boolean available) { 
        this.isAvailable.set(available); 
    }
    
    public void setRentedTo(String customer) {
        this.rentedTo.set(customer);
    }
    
    public void setRentedHours(int hours) {
        this.rentedHours.set(hours);
    }
    
    public void setRentedTime(String time) {
        this.rentedTime.set(time);
    }
}