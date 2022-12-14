import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    private List<Long> sieveOfEratosthenes = new ArrayList<>();

    public long exponentiationBySquaring(long a, long k, long m) {
        long res = 1;

        while (k > 0) {
            if (k % 2 != 0) {
                res = res * a % m;
            }

            k /= 2;
            a = a * a % m;
        }
        return res;
    }

    public long getRandomPrime(long left, long right) {
        fillSieveOfEratosthenes(right);
        while (sieveOfEratosthenes.get(0) < left) {
            sieveOfEratosthenes.remove(0);
        }

        Random random = new Random();

        return sieveOfEratosthenes.get(random.nextInt(sieveOfEratosthenes.size()));
    }

    public void fillSieveOfEratosthenes(long maxVal) {
        if (sieveOfEratosthenes.size() == maxVal)
            return;

        sieveOfEratosthenes = new ArrayList<>();
        for (long i = 2; i <= maxVal; i++) {
            sieveOfEratosthenes.add(i);
        }

        for (int i = 0; i < sieveOfEratosthenes.size(); i++) {
            long t = sieveOfEratosthenes.get(i);
            long v = t + t;
            while (v <= maxVal) {
                sieveOfEratosthenes.remove(v);
                v += t;
            }
        }
    }

    public static long gcd(long a, long b) {
        while (b > 0) {
            long t = a % b;
            a = b;
            b = t;
        }

        return a;
    }

    public long coprime(long val) {
        Random random = new Random();
        long a = random.nextLong(val - 2) + 2;
        for (long i = a; i >= 2; i--) {
            if (gcd(val, i) == 1) {
                return i;
            }
        }

        for (long i = a + 1; ; i++) {
            if (gcd(val, i) == 1) {
                return i;
            }
        }
    }
}
