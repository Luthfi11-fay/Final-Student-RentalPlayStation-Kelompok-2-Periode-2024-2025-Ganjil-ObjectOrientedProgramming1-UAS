package projectjava1;

public class Buku {
    private final String title;
    private final String author;
    private boolean tersedia;
    private final String deskripsi;
    private final double harga;
    private final double denda;
  
    // Constructor
    public Buku(String title, String author, String deskripsi, double harga, double denda) {
        this.title = title;
        this.author = author;
        this.tersedia = true; // Default: Book is available when created
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.denda = denda;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Getter for author
    public String getAuthor() {
        return author;
    }

    // Getter untuk tersedia
    public boolean tersedia() {
        return tersedia;
    }
   
    public String getDeskripsi() {
        return deskripsi;
    }
    
    public double getHarga() {  // Diubah dari setHarga ke getHarga
        return harga;
    }
    
    public double getDenda() {  // Diubah dari setDenda ke getDenda
        return denda;
    }

    // Setter untuk Tersedia
    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }
}