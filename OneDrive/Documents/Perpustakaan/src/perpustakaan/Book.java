package perpustakaan;

public class Book extends Library {
    private final String title;
    private final String author;
    private boolean tersedia;
    private final String deskripsi;
    private final double harga;
    private final double denda;

    public Book(String title, String author, String deskripsi, double harga, double denda) {
        super(title, 0, 0, "Library Book Location", denda);  // Placeholder values
        this.title = title;
        this.author = author;
        this.tersedia = true;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.denda = denda;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean tersedia() {
        return tersedia;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public double getHarga() {
        return harga;
    }

    public double getDenda() {
        return denda;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    @Override
    public void meminjamBuku(Book buku) {
        System.out.println("Books cannot borrow books. Invalid operation.");
    }
}
