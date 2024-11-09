package Pemanggil;

import perpustakaan.Book;
import perpustakaan.Member;
import perpustakaan.Visitur;

public class Main {
    public static void main(String[] args) {
        //Daftar Buku
        Book[] daftarBuku = {
            new Book("Java Programming", "Luthfi Fathillah", "Buku pemrograman Java untuk pemula", 150000.0, 10000.0),
            new Book("Database Design", "Luthfi Fathillah", "Panduan lengkap desain database", 200000.0, 15000.0),
            new Book("Web Development", "Luthfi Fathillah", "Tutorial pengembangan web modern", 175000.0, 12000.0),
            new Book("Artificial Intelligence", "Luthfi Fathillah", "Pengenalan ke AI dan Machine Learning", 250000.0, 20000.0),
            new Book("Network Security", "Luthfi Fathillah", "Fundamental keamanan jaringan", 180000.0, 13000.0)
        };

        // Data member dan Visitor
        Member mbr1 = new Member("Luthfi Fathillah", 1001, 12345, 0, "Jl. Sukajdi No. 123", 0.0);
        Member mbr2 = new Member("A. Rahman", 1002, 12346, 0, "Jl. Arab Saudi No. 45", 0.0);

        Visitur vst1 = new Visitur("M. Ikhwan Fathulloh, S.Kom", 2001, 0, 101, "Jl. Gatau No. 78", 0.0);
        Visitur vst2 = new Visitur("Widi Linggih Jaelani, S.Kom., M.T.", 2002, 0, 102, "Jl. Sudirman No. 90", 0.0);

        
        System.out.println("============= DAFTAR BUKU PERPUSTAKAAN =============");
        for (Book buku : daftarBuku) {
            System.out.println("\nJudul: " + buku.getTitle());
            System.out.println("Pengarang: " + buku.getAuthor());
            System.out.println("Deskripsi: " + buku.getDeskripsi());
            System.out.println("Harga: Rp " + String.format("%,.2f", buku.getHarga()));
            System.out.println("Denda per hari: Rp " + String.format("%,.2f", buku.getDenda()));
            System.out.println("Status: " + (buku.tersedia() ? "Tersedia" : "Dipinjam"));
            System.out.println("------------------------------------------------");
        }

        
        System.out.println("\n============= PEMINJAMAN BUKU =============");
        
        System.out.println("\nPeminjaman oleh Member:");
        mbr1.meminjamBuku(daftarBuku[0]);  // mbr1 borrows Java Programming
        mbr2.meminjamBuku(daftarBuku[1]);  // mbr2 borrows Database Design

        System.out.println("\nPeminjaman oleh Visitor:");
        vst1.meminjamBuku(daftarBuku[2]);  // vst1 borrows Web Development
        vst2.meminjamBuku(daftarBuku[3]);  // vst2 borrows AI

        
        System.out.println("\n========= STATUS BUKU SETELAH PEMINJAMAN ==========");
        for (Book buku : daftarBuku) {
            System.out.println(buku.getTitle() + ": " + 
                (buku.tersedia() ? "Tersedia" : "Dipinjam"));
        }

       
        System.out.println("\n============= INFORMASI PEMINJAM =============");

        
        System.out.println("\nInformasi Member 1:");
        System.out.println("Nama: " + mbr1.getName());
        System.out.println("ID Anggota: " + mbr1.getMemberId());
        System.out.println("Alamat: " + mbr1.getAlamat());
        System.out.println("Buku yang Dipinjam: " + (daftarBuku[0].tersedia() ? "Tidak ada" : daftarBuku[0].getTitle()));

        System.out.println("\nInformasi Member 2:");
        System.out.println("Nama: " + mbr2.getName());
        System.out.println("ID Anggota: " + mbr2.getMemberId());
        System.out.println("Alamat: " + mbr2.getAlamat());
        System.out.println("Buku yang Dipinjam: " + (daftarBuku[1].tersedia() ? "Tidak ada" : daftarBuku[1].getTitle()));

       
        System.out.println("\nInformasi Visitor 1:");
        System.out.println("Nama: " + vst1.getName());
        System.out.println("ID Anggota: " + vst1.getMemberId());
        System.out.println("Alamat: " + vst1.getAlamat());
        vst1.tampilkanBukuDipinjam();

        System.out.println("\nInformasi Visitor 2:");
        System.out.println("Nama: " + vst2.getName());
        System.out.println("ID Anggota: " + vst2.getMemberId());
        System.out.println("Alamat: " + vst2.getAlamat());
        vst2.tampilkanBukuDipinjam();

        
        System.out.println("\n============= PENGEMBALIAN BUKU =============");
        vst1.kembalikanBuku(daftarBuku[2]);  // vst1 returns Web Development

        
        System.out.println("\n============= STATUS AKHIR PERPUSTAKAAN =============");
        System.out.println("\nBuku yang tersedia:");
        for (Book buku : daftarBuku) {
            if (buku.tersedia()) {
                System.out.println("- " + buku.getTitle());
            }
        }

        System.out.println("\nBuku yang sedang dipinjam:");
        for (Book buku : daftarBuku) {
            if (!buku.tersedia()) {
                System.out.println("- " + buku.getTitle());
            }
        }
    }
}
