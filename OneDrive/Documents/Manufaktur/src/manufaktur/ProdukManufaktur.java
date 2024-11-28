package manufaktur;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class ProdukManufaktur extends BarangProduksi {
    // Atribut produksi
    protected double biayaBahanBaku;
    protected double kompleksitasProduksi;
    protected int stokProduk;

    
    // Kelas internal untuk riwayat produksi
    protected class RiwayatProduksi {
        private final LocalDateTime waktuProduksi;
        private final int jumlahUnit;
        private final double totalBiaya;

        public RiwayatProduksi(int jumlahUnit, double totalBiaya) {
            this.waktuProduksi = LocalDateTime.now();
            this.jumlahUnit = jumlahUnit;
            this.totalBiaya = totalBiaya;
        }

        // Getter terbatas
        public LocalDateTime getWaktuProduksi() { return waktuProduksi; }
        public int getJumlahUnit() { return jumlahUnit; }
        public double getTotalBiaya() { return totalBiaya; }
    }

    // Daftar riwayat produksi
    protected List<RiwayatProduksi> daftarRiwayat;

    // Konstruktor
    public ProdukManufaktur(String nama, String kategori, 
                             double biayaBahanBaku, 
                             double kompleksitasProduksi) {
        super(nama, kategori);
        this.biayaBahanBaku = biayaBahanBaku;
        this.kompleksitasProduksi = kompleksitasProduksi;
        this.stokProduk = 0;
        this.daftarRiwayat = new ArrayList<>();
    }

    @Override
    public void produksi(int jumlahUnit) {
        double biayaTotal = kalkulasiBiayaProduksi() * jumlahUnit;
        RiwayatProduksi riwayat = new RiwayatProduksi(jumlahUnit, biayaTotal);
        
        stokProduk += jumlahUnit;
        daftarRiwayat.add(riwayat);
    }

    @Override
    public double kalkulasiBiayaProduksi() {
        // Perhitungan biaya dasar dengan faktor kompleksitas
        return biayaBahanBaku * (1 + kompleksitasProduksi);
    }

    // Getter untuk informasi produk
    public int getStokProduk() { return stokProduk; }
    public List<RiwayatProduksi> getRiwayatProduksi() { return daftarRiwayat; }
}