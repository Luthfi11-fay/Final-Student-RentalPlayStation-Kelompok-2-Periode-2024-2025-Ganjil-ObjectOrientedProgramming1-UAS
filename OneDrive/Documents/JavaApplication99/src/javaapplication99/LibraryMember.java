/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication99;
import java.util.ArrayList;
import java.util.List;

public abstract class LibraryMember {
    private String name;
    private int memberId;
    private String address;
    protected List<Buku> borrowedBooks = new ArrayList<>();
    private double denda;

    public LibraryMember(String name, int memberId, String address) {
        this.name = name;
        this.memberId = memberId;
        this.address = address;
        this.denda = 0.0;
    }

    public String getName() {
        return name;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract void borrowBook(Buku book);

    public double calculateFine(int daysLate) {
        double finePerDay = 2000.0;
        return daysLate * finePerDay;
    }

    public void setDenda(double denda) {
        this.denda = denda;
    }

    public double getDenda() {
        return denda;
    }
}