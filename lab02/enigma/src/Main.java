import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("ENIGMA START");

        Enigma enigma = new Enigma();
        Enigma enigma2 = new Enigma();

        try {
            enigma.cipherFile("src.txt", "src2.txt");
            System.out.println("\n");
            enigma2.cipherFile("src2.txt", "src3.txt");
            System.out.println("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("ENIGMA STOP");
    }
}
