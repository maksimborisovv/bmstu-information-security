import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Huffman huffman = new Huffman();
        huffman.compressFile("input.txt", "output.txt");
        huffman.uncompressFile("output.txt", "output2.txt");
    }
}
