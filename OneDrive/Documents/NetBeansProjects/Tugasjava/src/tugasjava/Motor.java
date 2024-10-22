package tugasjava;

//class motor mewarisi kendaraan

public class Motor extends Kendaraan {
    public Motor(String merk) {
        super("Motor Sport", merk);
    }
//untuk menyalakan sebuah mesin kendaraan
    public void nyalakanMesin() {
        System.out.println("Motor " + merk + " "+ "punya saya  sedang dinyalakan dan saya akan balapan di Sirkuit Mandalika .");
    }
}
