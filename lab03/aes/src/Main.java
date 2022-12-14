import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        AES aes = new AES();
        System.out.println(aes);
        try {
            aes.enciphFile("input.txt", "key.txt", "output.txt");
            aes.deciphFile("output.txt", "key.txt", "output2.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
