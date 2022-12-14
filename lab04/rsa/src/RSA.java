import java.io.*;

public class RSA {
    private Long n = null;
    private Long e = null;
    private Long d = null;
    private final int BYTE_COUNT_IN = 1;
    private final int BYTE_COUNT_OUT = 4;

    private final Utils utils = new Utils();

    public void printNED() {
        System.out.println("N = " + n);
        System.out.println("E = " + e);
        System.out.println("D = " + d);
    }

    public void generateKeys(long left, long right) {
        long p = utils.getRandomPrime(left, right);
        long q = utils.getRandomPrime(left, right);
        n = p * q;
        long fi = (p - 1) * (q - 1);
        e = utils.coprime(fi);
        d = getPrivateKey(e, fi);
    }

    public long getPrivateKey(long e, long fi) {
        long a1 = 1;
        long a2 = 0;
        long b1 = 0;
        long b2 = 1;

        long tFi = fi;

        while (fi > 0) {
            long r = e % fi;
            long q = e / fi;

            e = fi;
            fi = r;

            if (fi != 0) {
                long tA1 = a1;
                long tB1 = b1;

                a1 = a2;
                a2 = tA1 - q * a2;

                a2 = (a2 % tFi + tFi) % tFi;

                b1 = b2;
                b2 = tB1 - q * b2;

                b2 = (b2 % tFi + tFi) % tFi;
            }
        }

        return a2;
    }

    long enciph(long m) {
        return utils.exponentiationBySquaring(m, e, n);
    }

    long deciph(long m) {
        return utils.exponentiationBySquaring(m, d, n);
    }

    public void setN(long n) {
        this.n = n;
    }

    public void setE(long e) {
        this.e = e;
    }

    public void setD(long d) {
        this.d = d;
    }

    public long getN() {
        return n;
    }

    public long getE() {
        return e;
    }

    public long getD() {
        return d;
    }

    public void enciphFile(String src, String dst) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(dst, false);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        FileInputStream fileInputStream = new FileInputStream(src);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

        while (dataInputStream.available() > 0) {
            long m = 0;
            for (int i = 0; i < BYTE_COUNT_IN; i++) {
                if (dataInputStream.available() > 0) {
                    m += Byte.toUnsignedLong(dataInputStream.readByte());
                }
            }

            long res = enciph(m);
            for (int i = 0; i < BYTE_COUNT_OUT; i++) {
                dataOutputStream.writeByte((byte) res % 256);
                res /= 256;
            }
        }

        fileInputStream.close();
        fileOutputStream.close();
    }

    public void deciphFile(String src, String dst) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(dst, false);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        FileInputStream fileInputStream = new FileInputStream(src);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

        while (dataInputStream.available() > 0) {
            long m = 0;
            long d = 1;
            for (int i = 0; i < BYTE_COUNT_OUT; i++) {
                if (dataInputStream.available() > 0) {
                    m += Byte.toUnsignedLong(dataInputStream.readByte()) * d;
                    d *= 256;
                }
            }

            long res = deciph(m);
            for (int i = 0; i < BYTE_COUNT_IN; i++) {
                dataOutputStream.writeByte((byte) res % 256);
                res /= 256;
            }
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
