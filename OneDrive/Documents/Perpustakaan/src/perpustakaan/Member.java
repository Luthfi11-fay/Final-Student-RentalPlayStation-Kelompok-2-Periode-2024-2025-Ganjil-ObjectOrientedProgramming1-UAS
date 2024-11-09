package perpustakaan;

public class Member extends Library{
    public Member(String name, int memberId, int ktp, int par2, String alamat, double denda) {
        super(name, memberId, ktp, alamat, denda);
    }

    @Override
    public void meminjamBuku(Book buku) {
        if (buku.tersedia()) {
            buku.setTersedia(false);
            System.out.println(getName() + " Member meminjam Buku : " + buku.getTitle());
        } else {
            System.out.println("Maaf ya, " + buku.getTitle() + " tidak tersedia disini.");
        }
    }
}