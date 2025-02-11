package com.mycompany.rental_playstation;

import javafx.beans.property.*;

public class RentalReport {
    private final StringProperty date;
    private final StringProperty consoleType;
    private final StringProperty customerName;
    private final IntegerProperty totalHours;
    private final DoubleProperty revenue;

    public RentalReport(String date, String consoleType, String customerName, int totalHours, double revenue) {
        this.date = new SimpleStringProperty(date);
        this.consoleType = new SimpleStringProperty(consoleType);
        this.customerName = new SimpleStringProperty(customerName);
        this.totalHours = new SimpleIntegerProperty(totalHours);
        this.revenue = new SimpleDoubleProperty(revenue);
    }

    // Getters
    public String getDate() { return date.get(); }
    public String getConsoleType() { return consoleType.get(); }
    public String getCustomerName() { return customerName.get(); }
    public int getTotalHours() { return totalHours.get(); }
    public double getRevenue() { return revenue.get(); }

    // Property getters
    public StringProperty dateProperty() { return date; }
    public StringProperty consoleTypeProperty() { return consoleType; }
    public StringProperty customerNameProperty() { return customerName; }
    public IntegerProperty totalHoursProperty() { return totalHours; }
    public DoubleProperty revenueProperty() { return revenue; }
}