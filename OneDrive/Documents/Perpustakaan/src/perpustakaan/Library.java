package perpustakaan;

public abstract class Library {
    final String name;
    final int memberId;
    private final String alamat;
    final double denda;
    private final int ktp;

    public Library(String name, int memberId, int ktp, String alamat, double denda) {
        this.name = name;
        this.memberId = memberId;
        this.alamat = alamat;
        this.denda = denda;
        this.ktp = ktp;
    }

    public String getName() {
        return name;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getKtp() {
        return ktp;
    }

    public String getAlamat() {
        return alamat;
    }

    public double getDenda() {
        return denda;
    }

    
    public abstract void meminjamBuku(Book buku);
}
