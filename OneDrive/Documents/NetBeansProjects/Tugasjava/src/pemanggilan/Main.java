package pemanggilan;

import tugasjava.Datadiri;
import tugasjava.Mobil;
import tugasjava.Motor;


public class Main {
    public static void main(String[] args) {
        Mobil mobilku = new Mobil("Toyota Supra");
        Motor motorku = new Motor("Yamaha R1m");
        Datadiri mahasiswa = new Datadiri();
        
        mahasiswa.Data();
        System.out.println("");
        
        System.out.println("Informasi Mobil:Sebagai mobil sport yang banyak diidamkan oleh para pecinta otomotif, harga bekas dari mobil ini pun terus meningkat");
        mobilku.infoKendaraan();
        mobilku.nyalakanMesin();

        System.out.println("\nInformasi Sepeda Motor:Dengan desain yang futuristik dan performa yang luar biasa, R1M mampu memberikan pengalaman berkendara yang tak tertandingi");
        motorku.infoKendaraan();
        motorku.nyalakanMesin();
    }
}