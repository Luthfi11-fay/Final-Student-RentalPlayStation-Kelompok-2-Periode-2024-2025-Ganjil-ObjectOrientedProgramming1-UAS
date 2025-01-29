/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rental_playstation;

/**
 *
 * @author user
 */

import javafx.beans.property.*;

public class RentalReport {
    private final StringProperty date;
    private final StringProperty consoleType;
    private final IntegerProperty totalHours;
    private final DoubleProperty revenue;

    public RentalReport(String date, String consoleType, int totalHours, double revenue) {
        this.date = new SimpleStringProperty(date);
        this.consoleType = new SimpleStringProperty(consoleType);
        this.totalHours = new SimpleIntegerProperty(totalHours);
        this.revenue = new SimpleDoubleProperty(revenue);
    }

    // Getters
    public String getDate() { return date.get(); }
    public String getConsoleType() { return consoleType.get(); }
    public int getTotalHours() { return totalHours.get(); }
    public double getRevenue() { return revenue.get(); }

    // Property getters
    public StringProperty dateProperty() { return date; }
    public StringProperty consoleTypeProperty() { return consoleType; }
    public IntegerProperty totalHoursProperty() { return totalHours; }
    public DoubleProperty revenueProperty() { return revenue; }
}