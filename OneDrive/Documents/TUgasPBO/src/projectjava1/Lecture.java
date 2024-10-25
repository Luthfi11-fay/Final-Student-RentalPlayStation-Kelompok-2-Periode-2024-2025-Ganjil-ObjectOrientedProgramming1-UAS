package projectjava1;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Lecture extends AnggotaPerpus {
    private LocalDate tanggalPinjam;
    private static final int PEMINJAMAN = 3;
    private final Buku[] bukuPinjaman;
    private final LocalDate[] tanggalPinjamArray;
    private int jumlahBukuPinjaman;
    private static final int MAX_BUKU = 3; // Maksimal buku yang bisa dipinjam

    public Lecture(String name, int memberId, int Nim, int codedosen, String alamat, double denda) {
        super(name, memberId, Nim, codedosen, alamat, denda);
        this.bukuPinjaman = new Buku[MAX_BUKU];
        this.tanggalPinjamArray = new LocalDate[MAX_BUKU];
        this.jumlahBukuPinjaman = 0;
    }

    @Override
    public void meminjamBuku(Buku buku) {
        if (jumlahBukuPinjaman >= MAX_BUKU) {
            System.out.println("Maaf, Anda sudah meminjam maksimal " + MAX_BUKU + " buku");
            return;
        }

        if (buku.tersedia()) {
            buku.setTersedia(false);
            this.tanggalPinjam = LocalDate.now();
            
            // Menyimpan buku dan tanggal peminjaman ke array
            bukuPinjaman[jumlahBukuPinjaman] = buku;
            tanggalPinjamArray[jumlahBukuPinjaman] = tanggalPinjam;
            jumlahBukuPinjaman++;

            System.out.println(getName() + " Dosen meminjam buku : " + buku.getTitle());
            System.out.println("Tanggal Peminjaman: " + tanggalPinjam);
            System.out.println("Batas Pengembalian: " + tanggalPinjam.plusDays(PEMINJAMAN));
            System.out.println("Jumlah buku yang dipinjam: " + jumlahBukuPinjaman + " dari " + MAX_BUKU);
        } else {
            System.out.println("Maaf ya, " + buku.getTitle() + " tidak tersedia disini ");
        }
    }

    // Method untuk menghitung denda per buku
    public double hitungDenda(int indexBuku) {
        if (indexBuku >= jumlahBukuPinjaman) {
            return 0.0;
        }

        LocalDate tanggalKembali = LocalDate.now();
        LocalDate batasPengembalian = tanggalPinjamArray[indexBuku].plusDays(PEMINJAMAN);
        long hariTerlambat = ChronoUnit.DAYS.between(batasPengembalian, tanggalKembali);

        if (hariTerlambat > 0) {
            return hariTerlambat * bukuPinjaman[indexBuku].getDenda();
        }
        return 0.0;
    }

    // Method untuk menampilkan daftar buku yang dipinjam
    public void tampilkanBukuDipinjam() {
        if (jumlahBukuPinjaman == 0) {
            System.out.println(getName() + " belum meminjam buku");
            return;
        }

        System.out.println("\nDaftar Buku yang Dipinjam oleh " + getName() + ":");
        System.out.println("============================================");
        double totalDenda = 0.0;

        for (int i = 0; i < jumlahBukuPinjaman; i++) {
            System.out.println("Buku ke-" + (i + 1) + ":");
            System.out.println("Judul: " + bukuPinjaman[i].getTitle());
            System.out.println("Pengarang: " + bukuPinjaman[i].getAuthor());
            System.out.println("Tanggal Pinjam: " + tanggalPinjamArray[i]);
            System.out.println("Batas Kembali: " + tanggalPinjamArray[i].plusDays(PEMINJAMAN));
            
            double dendaBuku = hitungDenda(i);
            totalDenda += dendaBuku;
            
            if (dendaBuku > 0) {
                System.out.println("Denda: Rp. " + String.format("%,.2f", dendaBuku));
            }
            System.out.println("--------------------------------------------");
        }

        if (totalDenda > 0) {
            System.out.println("Total Denda: Rp. " + String.format("%,.2f", totalDenda));
        }
    }

    // Method untuk mengembalikan buku
    public void kembalikanBuku(Buku buku) {
        for (int i = 0; i < jumlahBukuPinjaman; i++) {
            if (bukuPinjaman[i].equals(buku)) {
                double dendaBuku = hitungDenda(i);
                
                // Mengembalikan status buku menjadi tersedia
                buku.setTersedia(true);
                
                // Menggeser array untuk menghapus buku yang dikembalikan
                for (int j = i; j < jumlahBukuPinjaman - 1; j++) {
                    bukuPinjaman[j] = bukuPinjaman[j + 1];
                    tanggalPinjamArray[j] = tanggalPinjamArray[j + 1];
                }
                jumlahBukuPinjaman--;

                System.out.println("Buku " + buku.getTitle() + " berhasil dikembalikan");
                if (dendaBuku > 0) {
                    System.out.println("Denda yang harus dibayar: Rp. " + 
                        String.format("%,.2f", dendaBuku));
                }
                return;
            }
        }
        System.out.println("Buku " + buku.getTitle() + " tidak ditemukan dalam daftar pinjaman");
    }

    // Method untuk mendapatkan jumlah buku yang dipinjam
    public int getJumlahBukuPinjaman() {
        return jumlahBukuPinjaman;
    }
}