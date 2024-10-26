package projectjava1;

public abstract class AnggotaPerpus {
    private final String name;
    private final int memberId;
    private final int Nim;
    private final int codedosen;
    private final String alamat;
    private final double denda; 

    public AnggotaPerpus(String name, int memberId, int Nim, int codedosen, String alamat, double denda) {
        this.name = name;
        this.memberId = memberId;
        this.Nim = Nim;
        this.codedosen = codedosen;
        this.alamat = alamat;
        this.denda = denda; 
    }

    public String getName() { //method Name
        return name;
    }

    public int getMemberId() { // method memberid
        return memberId;
    }

    public int getNim() { //method Nim
        return Nim; 
    }

    public int getCodeDosen() {  // method nim
        return codedosen;
    }

    public String getAlamat() {  // Method Alamat
        return alamat;
    }

    public double getDenda() { // metho Denda 
        return denda;
    }
        
    // Abstraksi metode umum yang harus diimplementasikan oleh subclass
    public abstract void meminjamBuku(Buku buku);
}