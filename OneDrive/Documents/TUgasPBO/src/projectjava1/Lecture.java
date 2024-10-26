package projectjava1;

import java.time.LocalDate;

public class Lecture {
    private final AnggotaPerpus anggotaPerpus;
    private LocalDate tanggalPinjam;
    private final Buku[] bukuPinjaman;
    private final LocalDate[] tanggalPinjamArray;
    private int jumlahBukuPinjaman;
    private static final int MAX_BUKU = 3;

    public Lecture(String name, int memberId, int Nim, int codedosen, String alamat, double denda) {
        this.anggotaPerpus = new AnggotaPerpus(name, memberId, Nim, codedosen, alamat, denda);
        this.bukuPinjaman = new Buku[MAX_BUKU];
        this.tanggalPinjamArray = new LocalDate[MAX_BUKU];
        this.jumlahBukuPinjaman = 0;
    }

    public void meminjamBuku(Buku buku) {
        if (jumlahBukuPinjaman >= MAX_BUKU) {
            System.out.println("Maaf, Anda sudah meminjam maksimal " + MAX_BUKU + " buku");
            return;
        }

        if (buku.tersedia()) {
            buku = buku.setTersedia(false);
            this.tanggalPinjam = LocalDate.now();
            bukuPinjaman[jumlahBukuPinjaman] = buku;
            tanggalPinjamArray[jumlahBukuPinjaman] = tanggalPinjam;
            jumlahBukuPinjaman++;

            System.out.println(anggotaPerpus.name() + " Dosen meminjam buku : " + buku.title());
        } else {
            System.out.println("Maaf ya, " + buku.title() + " tidak tersedia disini ");
        }
    }

    // Getter methods 
    public String name() { 
        return anggotaPerpus.name(); 
    }
    public int memberId() {
        return anggotaPerpus.memberId();
    }
    public int codedosen() { 
        return anggotaPerpus.codedosen();
    }
    public String alamat() {
        return anggotaPerpus.alamat();
    }
}
