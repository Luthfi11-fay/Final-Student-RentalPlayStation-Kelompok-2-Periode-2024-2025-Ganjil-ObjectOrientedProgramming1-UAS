package tugasjava;

//class mobil mewarisi kendaraan

public class Mobil extends Kendaraan {
    public Mobil(String merk) {
        super("Mobil Sport", merk);
    }
//untuk menyalakan sebuah mesin
    public void nyalakanMesin() {
        System.out.println("mobil " + merk + " saya sedang dinyalakan dan saat ini saya akan balapan bersama teman teman saya ");  
        }
}
