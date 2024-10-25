package projectjava1;

public abstract class AnggotaPerpus {
    private final String name;
    private final int memberId;
    private final int Nim;
    private final int codedosen;
    private final String alamat;
    private final double denda;  // Dihapus inisialisasi yang salah

    public AnggotaPerpus(String name, int memberId, int Nim, int codedosen, String alamat, double denda) {
        this.name = name;
        this.memberId = memberId;
        this.Nim = Nim;
        this.codedosen = codedosen;
        this.alamat = alamat;
        this.denda = denda; 
    }

    public String getName() {
        return name;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getNim() {
        return Nim; 
    }

    public int getCodeDosen() {  // Perbaikan nama method sesuai konvensi
        return codedosen;
    }

    public String getAlamat() {  // Perbaikan penulisan
        return alamat;
    }

    public double getDenda() {  // Perbaikan method denda
        return denda;
    }
        
    // Abstraksi metode umum yang harus diimplementasikan oleh subclass
    public abstract void meminjamBuku(Buku buku);
}