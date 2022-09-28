package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static void generate() {
        try (FileWriter writer = new FileWriter("./src/application.properties", false)) {
            ArrayList<Integer> range = new ArrayList<>();
            for (int i = 0; i < 256; i++) {
                range.add(i);
            }
            Collections.shuffle(range);
            String rotorVals = range.toString().
                    replaceAll("[\\[\\],]", "").replaceAll(" ", ",");
            writer.write("enigma.rightRotor=" + rotorVals + "\n");

            Collections.shuffle(range);
            rotorVals = range.toString().
                    replaceAll("[\\[\\],]", "").replaceAll(" ", ",");
            writer.write("enigma.middleRotor=" + rotorVals + "\n");

            Collections.shuffle(range);
            rotorVals = range.toString().
                    replaceAll("[\\[\\],]", "").replaceAll(" ", ",");
            writer.write("enigma.leftRotor=" + rotorVals + "\n");

            Collections.shuffle(range);

            for (int i = 0; i < range.size(); i++) {
                range.set(i, range.size() - 1 - i);
            }
            rotorVals = range.toString().
                    replaceAll("[\\[\\],]", "").replaceAll(" ", ",");
            writer.write("enigma.reflector=" + rotorVals + "\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static ArrayList<Integer> getRotor(String key) {
        ArrayList<String> rotorValsStr =
                new ArrayList<>(Arrays.asList(get("enigma." + key + "Rotor").split(",")));
        ArrayList<Integer> rotorValsInt = new ArrayList<>();
        for (var i : rotorValsStr) {
            rotorValsInt.add(Integer.parseInt(i));
        }
        return rotorValsInt;
    }

    public static ArrayList<Integer> getReflector() {
        ArrayList<String> rotorValsStr =
                new ArrayList<>(Arrays.asList(get("enigma.reflector").split(",")));
        ArrayList<Integer> rotorValsInt = new ArrayList<>();
        for (var i : rotorValsStr) {
            rotorValsInt.add(Integer.parseInt(i));
        }
        return rotorValsInt;
    }

    public static void loadProperties() {
        try (var inputStream =
                     PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
