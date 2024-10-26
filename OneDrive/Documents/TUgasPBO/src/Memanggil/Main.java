package Memanggil;

import projectjava1.Buku;
import projectjava1.Lecture;
import projectjava1.Mahasiswa;

public class Main {    
    public static void main(String[] args) {
        // daftar buku menggunakan list array
        Buku[] daftarBuku = {
            new Buku("Java Programming", "Luthfi Fathillah", 
                    "Buku pemrograman Java untuk pemula", 150000.0, 10000.0),
            new Buku("Database Design", "Luthfi Fathillah", 
                    "Panduan lengkap desain database", 200000.0, 15000.0),
            new Buku("Web Development", "Luthfi Fathillah", 
                    "Tutorial pengembangan web modern", 175000.0, 12000.0),
            new Buku("Artificial Intelligence", "Luthfi Fathillah", 
                    "Pengenalan ke AI dan Machine Learning", 250000.0, 20000.0),
            new Buku("Network Security", "Luthfi Fathillah", 
                    "Fundamental keamanan jaringan", 180000.0, 13000.0)
        };

        // Data Mahasiswa dan Dosen
        Mahasiswa mhs1 = new Mahasiswa("Luthfi Fathillah", 1001, 12345, 0, 
                                     "Jl. Sukajdi No. 123", 0.0);
        Mahasiswa mhs2 = new Mahasiswa("Luthfi Fathillah", 1002, 12346, 0, 
                                     "Jl.Arab saudi No. 45", 0.0);
        
        Lecture dosen1 = new Lecture("M.ikhwan fathulloh,S.kom ", 2001, 0, 101, 
                                   "Jl. gatau No. 78", 0.0);
        Lecture dosen2 = new Lecture("Widi Linggih Jaelani,S.kom.,M.T.", 2002, 0, 102, 
                                   "Jl. sudirman No. 90", 0.0);

        // Menampilkan daftar buku awal
        System.out.println("============= DAFTAR BUKU PERPUSTAKAAN =============");
        for (Buku buku : daftarBuku) {
            System.out.println("\nJudul: " + buku.title());
            System.out.println("Pengarang: " + buku.author());
            System.out.println("Deskripsi: " + buku.deskripsi());
            System.out.println("Harga: Rp " + String.format("%,.2f", buku.harga()));
            System.out.println("Denda per hari: Rp " + String.format("%,.2f", buku.denda()));
            System.out.println("Status: " + (buku.tersedia() ? "Tersedia" : "Dipinjam"));
            System.out.println("------------------------------------------------");
        }

        System.out.println("\n============= PEMINJAMAN BUKU =============");
        
        // Peminjaman oleh mahasiswa
        System.out.println("\nPeminjaman oleh Mahasiswa:");
        System.out.println("-------------------------");
        mhs1.meminjamBuku(daftarBuku[0]);  // Pinjam Java Programming
        mhs2.meminjamBuku(daftarBuku[0]);  // Coba pinjam buku yang sudah dipinjam
        mhs2.meminjamBuku(daftarBuku[1]);  // Pinjam Database Design
        
        // Peminjaman oleh dosen
        System.out.println("\nPeminjaman oleh Dosen:");
        System.out.println("---------------------");
        dosen1.meminjamBuku(daftarBuku[2]); // Pinjam Web Development
        dosen2.meminjamBuku(daftarBuku[3]); // Pinjam AI

        // Status buku setelah peminjaman
        System.out.println("\n========= STATUS BUKU SETELAH PEMINJAMAN ==========");
        for (Buku buku : daftarBuku) {
            System.out.println(buku.title() + ": " + 
                (buku.tersedia() ? "Tersedia" : "Dipinjam"));
        }

        // Menampilkan informasi peminjam
        System.out.println("\n============= INFORMASI PEMINJAM =============");
        
        // Informasi Mahasiswa
        System.out.println("\nInformasi Mahasiswa 1:");
        System.out.println("Nama: " + mhs1.name());
        System.out.println("NIM: " + mhs1.Nim());
        System.out.println("ID Anggota: " + mhs1.memberId());
        System.out.println("Alamat: " + mhs1.alamat());

        System.out.println("\nInformasi Mahasiswa 2:");
        System.out.println("Nama: " + mhs2.name());
        System.out.println("NIM: " + mhs2.Nim());
        System.out.println("ID Anggota: " + mhs2.memberId());
        System.out.println("Alamat: " + mhs2.alamat());

        // Informasi Dosen
        System.out.println("\nInformasi Dosen 1:");
        System.out.println("Nama: " + dosen1.name());
        System.out.println("Kode Dosen: " + dosen1.codedosen());
        System.out.println("ID Anggota: " + dosen1.memberId());
        System.out.println("Alamat: " + dosen1.alamat());

        System.out.println("\nInformasi Dosen 2:");
        System.out.println("Nama: " + dosen2.name());
        System.out.println("Kode Dosen: " + dosen2.codedosen());
        System.out.println("ID Anggota: " + dosen2.memberId());
        System.out.println("Alamat: " + dosen2.alamat());

        // State akhir perpustakaan
        System.out.println("\n============= STATUS AKHIR PERPUSTAKAAN =============");
        System.out.println("\nBuku yang tersedia:");
        for (Buku buku : daftarBuku) {
            if (buku.tersedia()) {
                System.out.println("- " + buku.title());
            }
        }

        System.out.println("\nBuku yang sedang dipinjam:");
        for (Buku buku : daftarBuku) {
            if (!buku.tersedia()) {
                System.out.println("- " + buku.title());
            }
        }
    }
}
