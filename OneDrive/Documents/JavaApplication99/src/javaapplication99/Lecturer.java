/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication99;

public class Lecturer extends LibraryMember {
    private String lecturerCode;

    public Lecturer(String name, int memberId, String address, String lecturerCode) {
        super(name, memberId, address);
        this.lecturerCode = lecturerCode;
    }

    public String getLecturerCode() {
        return lecturerCode;
    }

    public void setLecturerCode(String lecturerCode) {
        this.lecturerCode = lecturerCode;
    }

    @Override
    public void borrowBook(Buku book) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            borrowedBooks.add(book);
            System.out.println(getName() + " (Lecturer) has borrowed the book: " + book.getTitle());
        } else {
            System.out.println("Sorry, " + book.getTitle() + " is not available.");
        }
    }
}