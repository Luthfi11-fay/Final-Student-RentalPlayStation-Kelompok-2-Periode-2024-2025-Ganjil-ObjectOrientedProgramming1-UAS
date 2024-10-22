package tugasjava;


//method class kendaraan
class Kendaraan {
     String jenis;
     String merk;

     //kendaraan constructor
    Kendaraan(String jenis, String merk) {
        this.jenis = jenis;
        this.merk = merk;
    }
//info kendaraan
    public void infoKendaraan() {
        System.out.println("Jenis Kendaraan: " + jenis);//pemanggilan class jenis
        System.out.println("Merk: " + merk);//pemanggilan class merk
    }
}