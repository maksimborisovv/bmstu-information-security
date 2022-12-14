import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {
    public static void main(String[] args) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        DigitalSignature digitalSignature = new DigitalSignature();
        digitalSignature.generateKeys();
//        digitalSignature.readKeys();
        digitalSignature.sign("test.txt");
        try {
            if (digitalSignature.verify("test.txt", "digitalSignature2.txt")) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        } catch (Exception e) {
            System.out.println("NO");
        }
    }
}
