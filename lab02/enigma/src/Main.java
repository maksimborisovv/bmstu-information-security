import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Enigma enigma = new Enigma(0, 0, 0);
        Enigma enigma2 = new Enigma(0, 0, 0);

        try {
            enigma.cipherFile("src.txt", "src2.txt");
            System.out.println("\n");
            enigma2.cipherFile("src2.txt", "src3.txt");
            System.out.println("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
