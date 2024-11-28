package Pemanggil;

import manufaktur.Elektronik;
import manufaktur.SistemManajemenManufaktur;

public class Main {
    public static void main(String[] args) {
        SistemManajemenManufaktur sistem = new SistemManajemenManufaktur();

        // Membuat produk elektronik
        Elektronik laptop = new Elektronik(
            "Laptop Gaming", 
            "Komputer", 
            6000000.0,   // Biaya bahan baku
            0.8,         // Kompleksitas
            "High-End",  // Spesifikasi teknologi
            25,          // Jumlah komponen
            "Otomatis"   // Proses produksi
        );
        laptop.produksi(50);  // Produksi 50 unit

        Elektronik smartphone = new Elektronik(
            "Iphone 15 Promax", 
            "Telekomunikasi", 
            4000000.0,   // Biaya bahan baku
            0.6,         // Kompleksitas
            "Mid-Range", 
            15,           
            "Semi-Otomatis" 
        );
        smartphone.produksi(75); 

       
        sistem.tambahProduk(laptop);
        sistem.tambahProduk(smartphone);

       
        sistem.laporanProduksi();

       
        System.out.println("\nTotal Biaya Produksi: Rp " + 
            String.format("%.2f", sistem.totalBiayaProduksi()));

        
        Elektronik produkDicari = sistem.cariProduk("Laptop Gaming");
        if (produkDicari != null) {
            System.out.println("\nDetail Produk Dicari:");
            System.out.println("Nama: " + produkDicari.getNama());
            System.out.println("Biaya Produksi: Rp " + 
                String.format("%.2f", produkDicari.kalkulasiBiayaProduksi()));
        }
    }
}