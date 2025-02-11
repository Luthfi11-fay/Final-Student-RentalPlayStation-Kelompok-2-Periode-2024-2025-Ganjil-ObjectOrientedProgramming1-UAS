🎮 Sistem Rental PlayStation
📖 Deskripsi
Sebuah aplikasi manajemen rental PlayStation berbasis JavaFX yang dikembangkan untuk memudahkan pengelolaan dan pemantauan penyewaan konsol game. Aplikasi ini menyediakan antarmuka yang user-friendly dan fitur-fitur komprehensif untuk manajemen rental PlayStation.
✨ Fitur Utama

🔐 Sistem Autentikasi: Login multiuser dengan role admin dan user
📊 Dashboard: Tampilan real-time status konsol dan penyewaan
💻 Manajemen Konsol: Kelola inventaris PS3, PS4, dan PS5
📝 Sistem Booking: Pencatatan dan pengelolaan penyewaan
📈 Laporan: Generasi laporan penyewaan dan pendapatan
🔄 Status Real-time: Pemantauan ketersediaan konsol secara real-time

👥 Tim Pengembang
NamaNPMRoleLuthfi Fathillah23552011209Ketua TimLutfhi Febrian Noor-AnggotaAnnisa Nur Fitriani-Anggota
🎓 Informasi Akademik

Mata Kuliah: Pemrograman Berorientasi Objek 1
Dosen: Muhammad Ikhwan Fathulloh
Semester: Ganjil 2023/2024

🛠️ Teknologi yang Digunakan

Java SE Development Kit 8
JavaFX untuk antarmuka pengguna
MySQL untuk manajemen database
Scene Builder untuk desain UI
NetBeans IDE 12.0

💻 Implementasi OOP
1️⃣ Inheritance (Pewarisan)
javaCopypublic abstract class Console {
    protected IntegerProperty hourlyRate;
    protected StringProperty type;
    // ... kode lainnya
}

public class PS4 extends Console {
    public PS4(String room) {
        super("PS4", 10000, room);
    }
    // ... kode lainnya
}
2️⃣ Encapsulation (Enkapsulasi)
javaCopypublic class Console {
    private IntegerProperty hourlyRate;
    
    public int getHourlyRate() { 
        return hourlyRate.get(); 
    }
    
    public void setHourlyRate(int rate) {
        this.hourlyRate.set(rate);
    }
}
3️⃣ Polymorphism (Polimorfisme)
javaCopypublic List<Console> getAllConsoles() {
    List<Console> consoles = new ArrayList<>();
    // ... kode implementasi
    Console console;
    switch (type) {
        case "PS3": console = new PS3(room); break;
        case "PS4": console = new PS4(room); break;
        case "PS5": console = new PS5(room); break;
    }
}
4️⃣ Abstraction (Abstraksi)
javaCopypublic abstract class Console {
    public abstract void calculateRentalCost();
    public abstract void checkMaintenance();
}
📥 Instalasi dan Penggunaan
Prasyarat
bashCopy- JDK 8 atau lebih tinggi
- MySQL Server
- NetBeans IDE (disarankan)
Langkah Instalasi

Clone repository

bashCopygit clone https://github.com/username/rental-playstation.git

Import database

bashCopymysql -u username -p rental_ps < database/rental_ps.sql

Konfigurasi database di src/main/java/com/mycompany/rental_playstation/DatabaseConnection.java

javaCopyprivate static final String URL = "jdbc:mysql://localhost:3306/rental_ps";
private static final String USER = "username_anda";
private static final String PASSWORD = "password_anda";

Build dan jalankan aplikasi

bashCopycd rental-playstation
mvn clean install
java -jar target/rental-playstation-1.0.jar
📸 Screenshot Aplikasi
<p align="center">
  <img src="screenshots/login.png" alt="Login Screen" width="200"/>
  <img src="screenshots/dashboard.png" alt="Dashboard" width="200"/>
  <img src="screenshots/rental.png" alt="Rental Management" width="200"/>
</p>
📊 Struktur Database
Show Image
🤝 Kontribusi
Kontribusi selalu disambut baik! Silakan ikuti langkah-langkah berikut:

Fork repository
Buat branch baru (git checkout -b fitur-baru)
Commit perubahan (git commit -m 'Menambahkan fitur baru')
Push ke branch (git push origin fitur-baru)
Buat Pull Request

📝 Lisensi
Proyek ini dilisensikan di bawah [Nama Lisensi] - lihat file LICENSE.md untuk detail.
🙏 Ucapan Terima Kasih

Bapak Muhammad Ikhwan Fathulloh selaku dosen pembimbing
Tim pengembang atas dedikasi dan kerja kerasnya
Semua pihak yang telah membantu dalam pengembangan proyek ini

📞 Kontak

Email: email@example.com
Website: https://www.example.com
LinkedIn: Your LinkedIn
