import util.PropertiesUtil;

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
}
