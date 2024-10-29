package javaapplication99;
public class memanggil {
    public static void main(String[] args) {
        // Create book instances
        Buku book1 = new Buku("Introduction to Java");
        Buku book2 = new Buku("Advanced OOP Concepts");

        // Create a Student member
        student student = new student("Alice", 101, "123 Campus Rd", "S12345");
        
        // Create a Lecturer member
        Lecturer lecturer = new Lecturer("Dr. Bob", 201, "456 Faculty Ln", "L98765");

        // Student borrows a book
        student.borrowBook(book1);
        
        // Lecturer attempts to borrow the same book (should be unavailable)
        lecturer.borrowBook(book1);
        
        // Lecturer borrows another available book
        lecturer.borrowBook(book2);

        // Calculate and display fine for student returning late
        int daysLate = 3;
        double fine = student.calculateFine(daysLate);
        student.setDenda(fine);
        System.out.println("Student's fine for late return: " + student.getDenda());

        // Display Lecturer's fine for late return with different days
        daysLate = 5;
        fine = lecturer.calculateFine(daysLate);
        lecturer.setDenda(fine);
        System.out.println("Lecturer's fine for late return: " + lecturer.getDenda());
    }
}