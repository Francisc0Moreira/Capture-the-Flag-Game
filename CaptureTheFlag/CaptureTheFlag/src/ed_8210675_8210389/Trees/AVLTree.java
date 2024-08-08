package Trees;

/**
 * AVLTree represents a self-balancing binary search tree.
 * It maintains its balance during insertions and deletions
 * to ensure the tree remains relatively balanced.
 */
public class AVLTree {
    private Node root;

    /**
     * Get the height of the specified node.
     *
     * @param node the node to get the height of
     * @return the height of the node or 0 if the node is null
     */
    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * Get the balance factor of the specified node.
     * The balance factor is the difference between the heights
     * of the left and right subtrees.
     *
     * @param node the node to get the balance factor of
     * @return the balance factor of the node or 0 if the node is null
     */
    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    /**
     * Perform a right rotation on the specified node.
     *
     * @param y the node to perform the rotation on
     * @return the new root after the rotation
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    /**
     * Perform a left rotation on the specified node.
     *
     * @param x the node to perform the rotation on
     * @return the new root after the rotation
     */
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /**
     * Insert a key into the AVL tree.
     *
     * @param key the key to be inserted
     */
    public void insert(int key) {
        root = insert(root, key);
    }

    /**
     * Recursive helper method to insert a key into the AVL tree.
     *
     * @param node the current node in the recursion
     * @param key  the key to be inserted
     * @return the new root of the subtree after insertion
     */
    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            // Duplicates are not allowed in AVL tree
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Print the elements of the AVL tree in an in-order traversal.
     */
    // Função de impressão em ordem
    public void inOrder() {
        inOrder(root);
    }

    /**
     * Recursive helper method to perform an in-order traversal and print elements.
     *
     * @param node the current node in the recursion
     */
    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }
}
