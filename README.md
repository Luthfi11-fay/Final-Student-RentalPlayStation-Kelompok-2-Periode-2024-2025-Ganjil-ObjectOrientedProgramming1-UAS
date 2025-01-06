# Sistem Rental PlayStation

## Gambaran Proyek
Aplikasi berbasis JavaFX untuk mengelola rental konsol PlayStation, dikembangkan oleh kelompok 2. Sistem ini memungkinkan bisnis rental untuk melacak ketersediaan konsol, mengelola penyewaan, dan menghasilkan laporan.

## Anggota Tim
- **Ketua Tim:** Luthfi Fathillah
- **Anggota 1:** Lutfhi Febrian noor
- **Anggota 2:** Annisa Nur Fitriani

## Informasi Mata Kuliah
- **Mata Kuliah:** Pemrograman Berorientasi Objek 1
- **Dosen:** Muhammad Ikhwan Fathulloh

## Fitur
1. Sistem Autentikasi Pengguna
2. Manajemen Rental Konsol
3. Pelacakan Ketersediaan Real-time
4. Pembuatan Laporan Rental
5. Dukungan Multi-konsol (PS3, PS4, PS5)

## Implementasi Prinsip Pemrograman Berorientasi Objek

### 1. Pewarisan (Inheritance)
- Hierarki konsol menunjukkan pewarisan dengan kelas dasar `Console` yang diperluas oleh kelas `PS3`, `PS4`, dan `PS5`
- Setiap jenis konsol mewarisi properti dan metode umum sambil mempertahankan tarif per jam yang spesifik
- Contoh codenya
``` // Kelas induk (parent class)
public abstract class Console {
    protected IntegerProperty hourlyRate;
    protected StringProperty type;
    protected StringProperty room;
    
    public Console(String type, int hourlyRate, String room) {
        this.type = new SimpleStringProperty(type);
        this.hourlyRate = new SimpleIntegerProperty(hourlyRate);
        this.room = new SimpleStringProperty(room);
    }
}

// Kelas turunan (child class)
public class PS4 extends Console {
    public PS4(String room) {
        super("PS4", 10000, room); // Mewarisi konstruktor dari kelas Console
    }
}

public class PS5 extends Console {
    public PS5(String room) {
        super("PS5", 15000, room); // Mewarisi konstruktor dari kelas Console
    }
}
```
### 2. Enkapsulasi (Encapsulation)
- Enkapsulasi data melalui field private dan getter/setter publik
- Properti dilindungi dan diakses melalui metode properti JavaFX
- Operasi database yang aman dienkapsulasi dalam kelas handler terpisah
- berikut ini adalah contoh codenya
  ```public class Console {
    // Data/variable private
    private IntegerProperty hourlyRate;
    private StringProperty type;
    
    // Getter dan Setter publik
    public int getHourlyRate() { 
        return hourlyRate.get(); 
    }
    
    public String getType() { 
        return type.get(); 
    }
    
    public void setAvailable(boolean available) { 
        this.isAvailable.set(available); 
    }
    
    public void setRentedTo(String customer) {
        this.rentedTo.set(customer);
    }
}

### 3. Polimorfisme (Polymorphism)
- Jenis konsol ditangani secara polimorfik melalui kelas dasar `Console`
- Berbagai jenis konsol dapat dikelola secara seragam sambil mempertahankan perilaku spesifik mereka
- Penggantian metode di subclass konsol untuk fungsionalitas spesifik
- berikut ini adalah contoh codenya
``` // Metode abstract di kelas induk
public abstract class Console {
    public abstract void calculateRentalCost();
    public abstract void checkMaintenance();
}

// Implementasi berbeda di setiap kelas turunan
public class PS3 extends Console {
    @Override
    public void calculateRentalCost() {
        // Implementasi khusus untuk PS3
    }
    
    @Override
    public void checkMaintenance() {
        // Implementasi pemeriksaan maintenance PS3
    }
}

public class PS4 extends Console {
    @Override
    public void calculateRentalCost() {
        // Implementasi khusus untuk PS4
    }
    
    @Override
    public void checkMaintenance() {
        // Implementasi pemeriksaan maintenance PS4
    }
}
```
### 4. Abstraksi (Abstraction)
- Kelas abstrak `Console` mendefinisikan template untuk semua jenis konsol
- Metode abstrak `calculateRentalCost()` dan `checkMaintenance()` memastikan implementasi yang konsisten di semua jenis konsol
- Desain berbasis antarmuka untuk operasi rental
- berikut ini adalah contoh codenya
- // Kelas abstrak dengan metode abstrak
public abstract class Console {
    // Metode abstrak yang harus diimplementasikan oleh kelas turunan
    public abstract void calculateRentalCost();
    public abstract void checkMaintenance();
    
    // Metode konkret yang bisa langsung digunakan
    public void displayInfo() {
        System.out.println("Type: " + getType());
        System.out.println("Room: " + getRoom());
        System.out.println("Hourly Rate: " + getHourlyRate());
    }
}

// RentalOperations sebagai abstraksi untuk operasi rental
public class RentalOperations {
    public void rentConsole(String customerName, String room, int hours) {
        // Menyembunyikan kompleksitas proses rental
        String query = "UPDATE consoles SET is_available = FALSE, " +
                      "rented_to = ?, rented_hours = ?, rented_time = ? WHERE room = ?";
        // Implementasi detail database
    }
    
    public void endRental(String room) {
        // Menyembunyikan kompleksitas proses pengembalian
        String query = "UPDATE consoles SET is_available = TRUE, " +
                      "rented_to = NULL, rented_hours = 0, rented_time = NULL WHERE room = ?";
        // Implementasi detail database
    }
}
```
