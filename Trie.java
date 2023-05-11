//Jake Li 1320187

public class Trie {
    private static final int R = 65536; // number of characters in the alphabet
    private Node root; // root of trie
    // inner class for nodes in the trie
    private class Node {
        private int index; // index assigned to this node
        private Node[] next = new Node[R]; // array of child nodes
    }

    // constructor
    public Trie() {
        root = new Node();
    }

    // put a key-value pair into the trie
    public void put(int val, String key) {
        root = put(root, key, val, 0); // call recursive helper function
    }

    // recursive helper function for put()
    private Node put(Node x, String key, int val, int d) {
        if (x == null) x = new Node(); // create new node if current node is null
        if (d == key.length()) { // if we've reached the end of the key
            x.index = val; // assign the index to the current node
            return x;
        }
        char c = key.charAt(d); // get the current character in the key
        x.next[c] = put(x.next[c], key, val, d+1); // recursively add the next character to the trie
        return x;
    }

    // get the value associated with a key
    public int get(String key) {
        Node x = get(root, key, 0); // call recursive helper function
        if (x == null) return 0; // if the key is not in the trie, return 0
        return x.index; // otherwise, return the index of the final node
    }

    // recursive helper function for get()
    private Node get(Node x, String key, int d) {
        if (x == null) return null; // if we've reached a null node, the key is not in the trie
        if (d == key.length()) return x; // if we've reached the end of the key, return the final node
        char c = key.charAt(d); // get the current character in the key
        return get(x.next[c], key, d+1); // recursively search for the next character in the trie
    }

    // check if the trie contains a key
    public boolean contains(String key) {
        return get(key) != 0; // if the index of the final node is not 0, the key is in the trie
    }

}