import util.PropertiesUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Enigma {
    public ArrayList<Integer> rightRotor;
    public ArrayList<Integer> middleRotor;
    public ArrayList<Integer> leftRotor;
    public ArrayList<Integer> reflector;

    public int rightRotorPos;
    public int middleRotorPos;
    public int leftRotorPos;

    public Enigma() {
        rightRotor = PropertiesUtil.getRotor("right");
        middleRotor = PropertiesUtil.getRotor("middle");
        leftRotor = PropertiesUtil.getRotor("left");
        reflector = PropertiesUtil.getReflector();

        rightRotorPos = 0;
        middleRotorPos = 0;
        leftRotorPos = 0;

        if (!checkCorrect()) {
            throw new RuntimeException();
        }
    }

    public boolean checkCorrect() {
        if (rightRotor.stream().distinct().count() != 256) {
            return false;
        }
        if (middleRotor.stream().distinct().count() != 256) {
            return false;
        }
        if (leftRotor.stream().distinct().count() != 256) {
            return false;
        }

        if (reflector.stream().distinct().count() != 256) {
            return false;
        } else {
            for (int i = 0; i < 256; i++) {
                if (reflector.get(reflector.get(i)) != i) {
                    return false;
                }
            }
        }

        return true;
    }

    public void next() {
        rightRotorPos++;
        middleRotorPos = middleRotorPos + rightRotorPos / 256;
        leftRotorPos = middleRotorPos + middleRotorPos / 256;
        leftRotorPos %= 256;
        middleRotorPos %= 256;
        rightRotorPos %= 256;
    }

    public int cipherChar(int val) {
        val = rightRotor.get((val + rightRotorPos) % 256);
        val = middleRotor.get((val + middleRotorPos) % 256);
        val = leftRotor.get((val + leftRotorPos) % 256);

        val = reflector.get(val);

        val = (leftRotor.indexOf(val) - leftRotorPos + 256) % 256;
        val = (middleRotor.indexOf(val) - middleRotorPos + 256) % 256;
        val = (rightRotor.indexOf(val) - rightRotorPos + 256) % 256;

        next();

        return val;
    }

    public void cipherFile(String src, String dst) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(src);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        System.out.println("OPEN INPUT");

        FileOutputStream fileOutputStream = new FileOutputStream(dst, false);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        System.out.println("OPEN OUTPUT");

        try {
            while (true) {
                byte bin = dataInputStream.readByte();
                System.out.print(bin + "|");
                int c = cipherChar(Byte.toUnsignedInt(bin));
                byte binRes = (byte)c;
                System.out.print(binRes + " ");
                dataOutputStream.writeByte((byte)c);
            }
        } catch (EOFException e) {
            System.out.println("");
            System.out.println("END OF FILE");
        }

        fileInputStream.close();
        System.out.println("INPUT CLOSED");
        fileOutputStream.close();
        System.out.println("OUTPUT CLOSED");
    }
}
