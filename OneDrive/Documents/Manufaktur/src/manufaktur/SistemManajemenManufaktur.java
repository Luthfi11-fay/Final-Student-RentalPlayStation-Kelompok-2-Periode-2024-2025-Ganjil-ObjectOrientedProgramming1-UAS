
package manufaktur;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SistemManajemenManufaktur {
    private final List<BarangProduksi> daftarProduk;

    public SistemManajemenManufaktur() {
        daftarProduk = new ArrayList<>();
    }

    // Metode untuk menambah produk
    public void tambahProduk(BarangProduksi produk) {
        daftarProduk.add(produk);
    }

    // Laporan total produksi
    public void laporanProduksi() {
        System.out.println("===== LAPORAN PRODUKSI ELEKTRONIK =====");
        List<Elektronik> elektronikList = daftarProduk.stream()
            .filter(p -> p instanceof Elektronik)
            .map(p -> (Elektronik) p)
            .collect(Collectors.toList());

        for (Elektronik produk : elektronikList) {
            System.out.println("Produk: " + produk.getNama());
            System.out.println("Kategori: " + produk.getKategori());
            System.out.println("Spesifikasi: " + produk.getSpesifikasiTeknologi());
            System.out.println("Total Stok: " + produk.getStokProduk());
            System.out.println("Jumlah Komponen: " + produk.getJumlahKomponen());
            System.out.println("Proses Produksi: " + produk.getProsesProduksi());
            System.out.println("Biaya Produksi per Unit: Rp " + 
                String.format("%.2f", produk.kalkulasiBiayaProduksi()));
            System.out.println("--------------------");
        }
    }

    // Kalkulasi total biaya produksi
    public double totalBiayaProduksi() {
        return daftarProduk.stream()
            .filter(p -> p instanceof Elektronik)
            .mapToDouble(p -> ((Elektronik) p).kalkulasiBiayaProduksi())
            .sum();
    }

    // Mencari produk berdasarkan nama
    public Elektronik cariProduk(String nama) {
        return daftarProduk.stream()
            .filter(p -> p instanceof Elektronik && p.getNama().equals(nama))
            .map(p -> (Elektronik) p)
            .findFirst()
            .orElse(null);
    }
}