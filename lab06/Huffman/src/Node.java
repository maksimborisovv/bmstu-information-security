public class Node {
    public int data;
    public int freq;
    public Node left;
    public Node right;

    public Node(int data, int freq) {
        this.data = data;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }
}
