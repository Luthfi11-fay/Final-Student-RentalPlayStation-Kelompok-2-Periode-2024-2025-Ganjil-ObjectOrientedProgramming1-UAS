package manufaktur;

public class Elektronik extends ProdukManufaktur {
    // Spesifikasi khusus elektronik
    private final String spesifikasiTeknologi;
    private final int jumlahKomponen;
    private final String prosesProduksi;

    public Elektronik(String nama, String kategori, 
                      double biayaBahanBaku, 
                      double kompleksitasProduksi,
                      String spesifikasiTeknologi,
                      int jumlahKomponen,
                      String prosesProduksi) {
        super(nama, kategori, biayaBahanBaku, kompleksitasProduksi);
        this.spesifikasiTeknologi = spesifikasiTeknologi;
        this.jumlahKomponen = jumlahKomponen;
        this.prosesProduksi = prosesProduksi;
    }

    // Override kalkulasi biaya khusus elektronik
    @Override
    public double kalkulasiBiayaProduksi() {
        double biayaDasar = super.kalkulasiBiayaProduksi();
        double biayaTambahan = 0;

        // Perhitungan biaya berdasarkan kompleksitas teknologi
        biayaTambahan = switch (spesifikasiTeknologi) {
            case "High-End" -> biayaDasar * 0.25;
            case "Mid-Range" -> biayaDasar * 0.15;
            default -> biayaDasar * 0.05;
        }; // 25% tambahan
        // 15% tambahan
        // 5% tambahan

        // Tambahan biaya berdasarkan jumlah komponen
        biayaTambahan += (jumlahKomponen * 1000);

        // Variasi biaya berdasarkan proses produksi
        switch(prosesProduksi) {
            case "Otomatis" -> biayaTambahan *= 0.9;  // 10% pengurangan biaya
            case "Semi-Otomatis" -> biayaTambahan *= 1.1;  // 10% penambahan biaya
        }

        return biayaDasar + biayaTambahan;
    }

    // Override metode produksi untuk validasi
    @Override
    public void produksi(int jumlahUnit) {
        if (jumlahUnit > 0 && jumlahUnit <= 100) {
            super.produksi(jumlahUnit);
        } else {
            System.out.println("Jumlah produksi tidak valid. Harus antara 1-100 unit.");
        }
    }

    // Getter spesifik elektronik
    public String getSpesifikasiTeknologi() { return spesifikasiTeknologi; }
    public int getJumlahKomponen() { return jumlahKomponen; }
    public String getProsesProduksi() { return prosesProduksi; }
}