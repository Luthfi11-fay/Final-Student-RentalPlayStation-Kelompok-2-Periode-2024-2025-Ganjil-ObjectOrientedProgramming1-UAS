Sistem Rental PlayStation
1. Gambaran Proyek

Aplikasi rental PlayStation berbasis JavaFX
Dikembangkan oleh kelompok 2
Fokus pada manajemen rental konsol PlayStation dengan fitur:

Pelacakan ketersediaan
Pengelolaan penyewaan
Pembuatan laporan



2. Tim Pengembang

Ketua: Luthfi Fathillah (NPM: 23552011209)
Anggota 1: Lutfhi Febrian Noor
Anggota 2: Annisa Nur Fitriani

3. Informasi Akademik

Mata Kuliah: Pemrograman Berorientasi Objek 1
Dosen: Muhammad Ikhwan Fathulloh

4. Fitur Utama

Sistem Autentikasi Pengguna
Manajemen Rental Konsol
Pelacakan Ketersediaan Real-time
Pembuatan Laporan Rental
Dukungan Multi-konsol (PS3, PS4, PS5)

5. Implementasi OOP
A. Pewarisan (Inheritance)

Deskripsi:


Menggunakan kelas dasar Console yang diturunkan ke kelas PS3, PS4, dan PS5
Setiap kelas turunan mewarisi properti dan method umum dengan tarif per jam spesifik


Contoh Kode dari Console.java:

javaCopypublic abstract class Console {
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
}

Contoh Kode Turunan dari PS4.java:

javaCopypublic class PS4 extends Console {
    public PS4(String room) {
        super("PS4", 10000, room); // Mewarisi konstruktor dari kelas Console
    }

    @Override
    public void calculateRentalCost() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void checkMaintenance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
B. Enkapsulasi (Encapsulation)

Deskripsi:


Menggunakan field private dan getter/setter publik
Properti dilindungi dengan metode properti JavaFX
Operasi database dienkapsulasi dalam handler terpisah


Contoh Kode dari Console.java:

javaCopypublic abstract class Console {
    protected IntegerProperty hourlyRate;
    protected StringProperty type;
    
    // Getters
    public int getHourlyRate() { return hourlyRate.get(); }
    public String getType() { return type.get(); }
    
    // Property methods
    public IntegerProperty hourlyRateProperty() { return hourlyRate; }
    public StringProperty typeProperty() { return type; }
    
    // Setters
    public void setAvailable(boolean available) { 
        this.isAvailable.set(available); 
    }
    
    public void setRentedTo(String customer) {
        this.rentedTo.set(customer);
    }
}
C. Polimorfisme (Polymorphism)

Deskripsi:


Variable Console dapat menyimpan berbagai objek turunan
Memungkinkan penanganan fleksibel berbagai jenis PlayStation


Contoh Kode dari RentalOperations.java:

javaCopypublic List<Console> getAllConsoles() {
    List<Console> consoles = new ArrayList<>();
    String query = "SELECT * FROM consoles";
    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            Console console;
            String type = rs.getString("type");
            String room = rs.getString("room");
            
            switch (type) {
                case "PS3":
                    console = new PS3(room);
                    break;
                case "PS4":
                    console = new PS4(room);
                    break;
                case "PS5":
                    console = new PS5(room);
                    break;
                default:
                    continue;
            }
            consoles.add(console);
        }
    }
    return consoles;
}
D. Abstraksi (Abstraction)

Deskripsi:


Kelas Console sebagai template abstrak
Memiliki method abstrak yang harus diimplementasikan oleh kelas turunan
Menggunakan desain berbasis antarmuka untuk operasi rental


Contoh Kode dari Console.java:

javaCopypublic abstract class Console {
    // Abstract methods
    public abstract void calculateRentalCost();
    public abstract void checkMaintenance();
    
    // Concrete methods
    public void setRentedTime(String time) {
        this.rentedTime.set(time);
    }
    
    public void setRentedHours(int hours) {
        this.rentedHours.set(hours);
    }
}

Contoh Implementasi Abstraksi dalam RentalOperations.java:

javaCopypublic class RentalOperations {
    public void rentConsole(String customerName, String room, int hours) {
        try {
            connection.setAutoCommit(false);
            String consoleQuery = "UPDATE consoles SET " +
                "is_available = FALSE, " +
                "rented_to = ?, " +
                "rented_hours = ?, " +
                "rented_time = ? " +
                "WHERE room = ?";
            
            // Implementation details hidden
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }
}
Proyek ini menunjukkan implementasi yang komprehensif dari prinsip-prinsip OOP dalam pengembangan sistem manajemen rental PlayStation.
