/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication99;

public class student extends LibraryMember {
    private String nim;

    public student(String name, int memberId, String address, String nim) {
        super(name, memberId, address);
        this.nim = nim;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    @Override
    public void borrowBook(Buku book) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            borrowedBooks.add(book);
            System.out.println(getName() + " (Student) has borrowed the book: " + book.getTitle());
        } else {
            System.out.println("Sorry, " + book.getTitle() + " is not available.");
        }
    }
}