package Trees;

/**
 * Node class represents a node in an AVL Tree.
 * Each node contains a key, height, and references to left and right children.
 */
class Node {
    int key;         // The value of the node
    int height;      // Height of the node in the tree
    Node left;       // Reference to the left child
    Node right;      // Reference to the right child

    /**
     * Constructs a new node with the given key.
     *
     * @param key the value to be stored in the node
     */
    public Node(int key) {
        this.key = key;
        this.height = 1;  // New node has height 1
    }
}
