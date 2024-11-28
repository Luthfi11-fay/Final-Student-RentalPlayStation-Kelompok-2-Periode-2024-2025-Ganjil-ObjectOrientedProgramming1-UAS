package manufaktur;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BarangProduksi {
    
    protected final String idProduk;
    protected String nama;
    protected String kategori;
    protected LocalDateTime tanggalPembuatan;
    
    
    public BarangProduksi(String nama, String kategori) {
        this.idProduk = UUID.randomUUID().toString();
        this.nama = nama;
        this.kategori = kategori;
        this.tanggalPembuatan = LocalDateTime.now();
    }
    
    
    public abstract void produksi(int jumlahUnit);
    
    
    public abstract double kalkulasiBiayaProduksi();
    
    
    public String getIdProduk() { return idProduk; }
    public String getNama() { return nama; }
    public String getKategori() { return kategori; }
    public LocalDateTime getTanggalPembuatan() { return tanggalPembuatan; }
}