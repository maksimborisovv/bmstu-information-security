import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Huffman {
    public Node root = null;
    public ArrayList<Node> nodes = new ArrayList<>();
    public Map<Integer, String> map = new HashMap<>();

    public void createTree(byte[] bytes) {
        ArrayList<Integer> message = new ArrayList<>();
        for (byte aByte : bytes) {
            message.add(Byte.toUnsignedInt(aByte));
        }

        ArrayList<Integer> alph = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            alph.add(0);
        }
        int total = 0;
        for (int m : message) {
            total++;
            alph.set(m, alph.get(m) + 1);
        }

        for (int i = 0; i < alph.size(); i++) {
            if (alph.get(i) > 0) {
                nodes.add(new Node(i, alph.get(i) * 100 / total));
            }
        }

        while (nodes.size() > 1) {
            nodes.sort(new NodeComparator());

            Node a = nodes.get(0);
            Node b = nodes.get(1);
            Node newNode = new Node(-1, a.freq + b.freq);
            newNode.left = a;
            newNode.right = b;

            nodes.remove(0);
            nodes.remove(0);
            nodes.add(newNode);
        }

        root = nodes.get(0);
    }

    public void fillMap(Node cur, String code) {
        if (cur.right == null && cur.left == null) {
            map.put(cur.data, code);
        }

        if (cur.left != null) {
            fillMap(cur.left, code + "0");
        }

        if (cur.right != null) {
            fillMap(cur.right, code + "1");
        }
    }

    public void uncompressFile(String inFile, String outFile) throws IOException {
        byte[] inBytes = Files.readAllBytes(Paths.get(inFile));
        FileOutputStream fileOutputStream = new FileOutputStream(outFile, false);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        ArrayList<Byte> res = new ArrayList<>();

        int eps = Byte.toUnsignedInt(inBytes[0]);
        int d = 128;
        int i = 1;
        Node curNode = root;

        int cur = Byte.toUnsignedInt(inBytes[i]);
        while (i < inBytes.length - 1) {
            if (cur / d == 1) {
                curNode = curNode.right;
            } else {
                curNode = curNode.left;
            }
            cur = cur % d;

            assert curNode != null;
            if (curNode.left == null && curNode.right == null) {
                res.add((byte) curNode.data);
                curNode = root;
            }
            d /= 2;
            if (d == 0) {
                d = 128;
                i++;
                cur = Byte.toUnsignedInt(inBytes[i]);
            }
        }

        d = 128;
        while (eps != 0) {
            cur /= 2;
            d /= 2;
            eps--;
        }
        while (d != 0) {
            if (cur / d == 1) {
                curNode = curNode.right;
            } else {
                curNode = curNode.left;
            }
            cur = cur % d;

            assert curNode != null;
            if (curNode.left == null && curNode.right == null) {
                res.add((byte) curNode.data);
                curNode = root;
            }

            d /= 2;
        }

        for (Byte re : res) {
            dataOutputStream.writeByte(re);
        }
    }

    public void compressFile(String inFile, String outFile) throws IOException {
        byte[] inBytes = Files.readAllBytes(Paths.get(inFile));
        createTree(inBytes);
        fillMap(root, "");
        FileOutputStream fileOutputStream = new FileOutputStream(outFile, false);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        ArrayList<Byte> res = new ArrayList<>();
        int cur = 0;
        int blockSize = 0;

        for (byte inByte : inBytes) {
            String code = map.get(Byte.toUnsignedInt(inByte));
            for (int j = 0; j < code.length(); j++) {
                cur *= 2;
                cur += code.charAt(j) - '0';
                blockSize++;
                if (blockSize == 8) {
                    res.add((byte) cur);
                    cur = 0;
                    blockSize = 0;
                }
            }
        }

        byte eps = (byte) ((8 - res.size() % 8) % 8);

        if (blockSize != 0) {
            while (blockSize != 8) {
                cur *= 2;
                blockSize++;
            }
            res.add((byte) cur);
        }

        dataOutputStream.writeByte(eps);
        for (Byte re : res) {
            dataOutputStream.writeByte(re);
        }
    }
}

class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.freq, o2.freq);
    }
}