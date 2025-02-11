# 🎮 Sistem Rental PlayStation


## 📖 Deskripsi
Sebuah aplikasi manajemen rental PlayStation berbasis JavaFX yang dikembangkan untuk memudahkan pengelolaan dan pemantauan penyewaan konsol game. Aplikasi ini menyediakan antarmuka yang user-friendly dan fitur-fitur komprehensif untuk manajemen rental PlayStation.

## ✨ Fitur Utama
- 🔐 **Sistem Autentikasi**: Login multiuser dengan role admin dan user
- 📊 **Dashboard**: Tampilan real-time status konsol dan penyewaan
- 💻 **Manajemen Konsol**: Kelola inventaris PS3, PS4, dan PS5
- 📝 **Sistem Booking**: Pencatatan dan pengelolaan penyewaan
- 📈 **Laporan**: Generasi laporan penyewaan dan pendapatan
- 🔄 **Status Real-time**: Pemantauan ketersediaan konsol secara real-time

## 👥 Tim Pengembang
| Nama | NPM | Role |
|------|-----|------|
| Luthfi Fathillah | 23552011209 | Ketua Tim |
| Lutfhi Febrian Noor | - | Anggota |
| Annisa Nur Fitriani | - | Anggota |

## 🎓 Informasi Akademik
- **Mata Kuliah**: Pemrograman Berorientasi Objek 1
- **Dosen**: Muhammad Ikhwan Fathulloh
- **Semester**: Ganjil 2023/2024

## 🛠️ Teknologi yang Digunakan
- Java SE Development Kit 8
- JavaFX untuk antarmuka pengguna
- MySQL untuk manajemen database
- Scene Builder untuk desain UI
- NetBeans IDE 12.0

## 💻 Implementasi OOP

### 1️⃣ Inheritance (Pewarisan)
Inheritance memungkinkan pembuatan hierarki kelas dimana kelas-kelas dapat mewarisi atribut dan metode dari kelas lainnya.

**Deskripsi:**
- Kelas `Console` bertindak sebagai kelas dasar (parent class)
- Kelas `PS3`, `PS4`, dan `PS5` mewarisi properti dan metode dari kelas `Console`
- Setiap kelas turunan memiliki tarif per jam yang spesifik

**Implementasi:**
```java
// Kelas Dasar
public abstract class Console {
    protected IntegerProperty hourlyRate;
    protected StringProperty type;
    protected StringProperty room;
    protected BooleanProperty isAvailable;

    public Console(String type, int hourlyRate, String room) {
        this.type = new SimpleStringProperty(type);
        this.hourlyRate = new SimpleIntegerProperty(hourlyRate);
        this.room = new SimpleStringProperty(room);
        this.isAvailable = new SimpleBooleanProperty(true);
    }
}

// Kelas Turunan
public class PS4 extends Console {
    public PS4(String room) {
        super("PS4", 10000, room); // Mewarisi dan menginisialisasi dengan nilai spesifik
    }
}
```

### 2️⃣ Encapsulation (Enkapsulasi)
Enkapsulasi menyembunyikan detail implementasi dan memproteksi data dengan membatasi akses langsung.

**Deskripsi:**
- Menggunakan access modifier untuk kontrol akses data
- Menyediakan getter dan setter untuk akses terkontrol
- Menggunakan properti JavaFX untuk binding data

**Implementasi:**
```java
public abstract class Console {
    // Data private/protected
    private IntegerProperty hourlyRate;
    protected StringProperty type;
    
    // Getter publik
    public int getHourlyRate() { 
        return hourlyRate.get(); 
    }
    
    // Property methods untuk JavaFX
    public IntegerProperty hourlyRateProperty() { 
        return hourlyRate; 
    }
    
    // Setter dengan validasi
    public void setRentedHours(int hours) {
        if (hours > 0) {
            this.rentedHours.set(hours);
        }
    }
}
```

### 3️⃣ Polymorphism (Polimorfisme)
Polimorfisme memungkinkan objek untuk diproses berbeda sesuai dengan tipenya.

**Deskripsi:**
- Menggunakan tipe `Console` untuk menangani semua jenis PlayStation
- Method yang sama dapat berperilaku berbeda tergantung implementasi
- Memudahkan pengelolaan berbagai jenis konsol dalam satu sistem

**Implementasi:**
```java
public class RentalOperations {
    public List<Console> getAllConsoles() {
        List<Console> consoles = new ArrayList<>();
        // Satu method menangani berbagai jenis konsol
        Console console;
        switch (type) {
            case "PS3": console = new PS3(room); break;
            case "PS4": console = new PS4(room); break;
            case "PS5": console = new PS5(room); break;
        }
        return consoles;
    }
}
```

### 4️⃣ Abstraction (Abstraksi)
Abstraksi menyembunyikan kompleksitas dengan menunjukkan hanya fungsionalitas yang diperlukan.

**Deskripsi:**
- Kelas `Console` sebagai template abstrak
- Mendefinisikan kontrak untuk implementasi di kelas turunan
- Menyederhanakan interaksi dengan sistem rental

**Implementasi:**
```java
public abstract class Console {
    // Method abstrak yang harus diimplementasikan
    public abstract void calculateRentalCost();
    public abstract void checkMaintenance();
    
    // Method konkret yang sudah diimplementasikan
    public void rentConsole(String customerName, int hours) {
        if (isAvailable.get()) {
            setRentedTo(customerName);
            setRentedHours(hours);
            setAvailable(false);
            setRentedTime(LocalDateTime.now().toString());
        }
    }
}

// Implementasi di RentalOperations
public class RentalOperations {
    public void rentConsole(String customerName, String room, int hours) {
        // Abstraksi proses rental yang kompleks
        try {
            connection.setAutoCommit(false);
            // Proses rental
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
    }
}
```

## 📥 Instalasi dan Penggunaan

### Prasyarat
```bash
- JDK 8 atau lebih tinggi
- MySQL Server
- NetBeans IDE (disarankan)
```

### Langkah Instalasi
1. Clone repository
```bash
git clone https://github.com/username/rental-playstation.git
```

2. Import database
```bash
mysql -u username -p rental_ps < database/rental_ps.sql
```

3. Konfigurasi database di `src/main/java/com/mycompany/rental_playstation/DatabaseConnection.java`
```java
private static final String URL = "jdbc:mysql://localhost:3306/rental_ps";
private static final String USER = "username_anda";
private static final String PASSWORD = "password_anda";
```

4. Build dan jalankan aplikasi
```bash
cd rental-playstation
mvn clean install
java -jar target/rental-playstation-1.0.jar
```

## 📸 Screenshot Aplikasi
<p align="center">
  <img src="screenshots/login.png" alt="Login Screen" width="200"/>
  <img src="screenshots/dashboard.png" alt="Dashboard" width="200"/>
  <img src="screenshots/rental.png" alt="Rental Management" width="200"/>
</p>

## 📊 Struktur Database
![Database Structure](path_to_database_structure_image.png)

## 🤝 Kontribusi
Kontribusi selalu disambut baik! Silakan ikuti langkah-langkah berikut:
1. Fork repository
2. Buat branch baru (`git checkout -b fitur-baru`)
3. Commit perubahan (`git commit -m 'Menambahkan fitur baru'`)
4. Push ke branch (`git push origin fitur-baru`)
5. Buat Pull Request

## 📝 Lisensi
Proyek ini dilisensikan di bawah [Nama Lisensi] - lihat file [LICENSE.md](LICENSE.md) untuk detail.

## 🙏 Ucapan Terima Kasih
- Bapak Muhammad Ikhwan Fathulloh selaku dosen pembimbing
- Tim pengembang atas dedikasi dan kerja kerasnya
- Semua pihak yang telah membantu dalam pengembangan proyek ini
