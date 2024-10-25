package projectjava1;

public class Mahasiswa extends AnggotaPerpus {
    public Mahasiswa(String name, int memberId, int Nim, int codedosen, String alamat, double denda) {
        super(name, memberId, Nim, codedosen, alamat, denda);
    }

    @Override
    public void meminjamBuku(Buku buku) {
        if (buku.tersedia()) {
            buku.setTersedia(false);
            System.out.println(getName() + " Mahasiswa meminjam Buku : " + buku.getTitle());
        } else {
            System.out.println("Maaf ya, " + buku.getTitle() + " tidak tersedia disini.");
        }
    }
}