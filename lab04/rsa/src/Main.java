import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        RSA rsa = new RSA();
        rsa.generateKeys(1000, 9999);
        rsa.printNED();
        rsa.enciphFile("input.txt", "output.txt");
        rsa.deciphFile("output.txt", "output2.txt");
//        System.out.println(rsa.getPrivateKey(3, 3016));
//        Utils utils = new Utils();
//        utils.fillSieveOfEratosthenes(50);
//        Utils.exponentiationBySquaring(2, 10, 10000);
//        System.out.println(Utils.gcd(10, 5));
//        System.out.println(Utils.coprime(24));
    }
}