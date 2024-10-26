package projectjava1;

public class Mahasiswa {
    private final AnggotaPerpus anggotaPerpus;

    public Mahasiswa(String name, int memberId, int Nim, int codedosen, String alamat, double denda) {
        this.anggotaPerpus = new AnggotaPerpus(name, memberId, Nim, codedosen, alamat, denda);
    }

    public void meminjamBuku(Buku buku) {
        if (buku.tersedia()) {
            buku = buku.setTersedia(false);
            System.out.println(anggotaPerpus.name() + " Mahasiswa meminjam Buku : " + buku.title());
        } else {
            System.out.println("Maaf ya, " + buku.title() + " tidak tersedia disini.");
        }
    }

    // Getter untuk mendapatkan informasi dari anggotaPerpus
      public String name() 
    { return anggotaPerpus.name(); 
    }  
    public int memberId(){ 
        return anggotaPerpus.memberId();
    }
    public int Nim(){
        return anggotaPerpus.Nim();
    }
    public String alamat() { 
        return anggotaPerpus.alamat();
    }
}
