package projectjava1;

public record Buku(String title, String author, String deskripsi, double harga, double denda, boolean tersedia) {
    // Konstruktor 
    public Buku(String title, String author, String deskripsi, double harga, double denda) {
        this(title, author, deskripsi, harga, denda, true);
    }

    // Setter untuk mengubah status ketersediaan buku
    public Buku setTersedia(boolean tersedia) {
        return new Buku(title, author, deskripsi, harga, denda, tersedia);
    }
}
