package tree;

public class AVLTree implements Tree<Integer, AVLTree.Node> {

    private Node root;

    public AVLTree() {
        root = null;
    }

    // Left element of root now becomes root
    private Node rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;

        Node.updateHeight(node);
        Node.updateHeight(left);

        return left;
    }

    // Right element of root now becomes root
    private Node rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;

        Node.updateHeight(node);
        Node.updateHeight(right);

        return right;
    }

    private Node rebalance(Node node) {
        Node.updateHeight(node);
        int balance = Node.balanceFactor(node);
        // Positive unbalance in root tree (right has more levels than left)
        // and not positive (un)balance in right subtree requires double rotation.
        // The same happens with negative unbalance in root tree. If the left
        // subtree doesn't have a negative (un)balance too, we rotate two times.
        if (isUnbalancePositive(balance)) {
            int rightBalance = Node.balanceFactor(node.right);
            if (!isBalanceFactorPositive(rightBalance)) {
                // Rotation necessary to reestablish balance (RL -> Right Rotation and Left Rotation)
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        } else if (isUnbalanceNegative(balance)) {
            int leftBalance = Node.balanceFactor(node.left);
            if (!isBalanceFactorNegative(leftBalance)) {
                // Rotation necessary to reestablish balance (LR -> Left Rotation and Right Rotation)
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        }
        return node;
    }

    @Override
    public void insert(Integer value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node node, Integer value) {
        if (isNodeEmpty(node)) return new Node(value);

        if (isValueLessThanNode(node, value)) {
            node.left = insertRecursive(node.left, value);
        } else if (isValueGreaterThanNode(node, value)) {
            node.right = insertRecursive(node.right, value);
        } else {
            return node; // Number already exists
        }
        return rebalance(node);
    }

    @Override
    public Node find(Integer value) {
        return findRecursive(root, value);
    }

    private Node findRecursive(Node node, Integer value) {
        if (isNodeEmpty(node)) return null;
        if (isValueEqualToNode(node, value)) return node;

        return isValueLessThanNode(node, value)
                ? findRecursive(node.left, value)
                : findRecursive(node.right, value);
    }

    @Override
    public void remove(Integer value) {
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node node, Integer value) {
        if (isNodeEmpty(node)) return null;

        if (isValueEqualToNode(node, value)) {
            if (isNodeEmpty(node.left) || isNodeEmpty(node.right)) {
                node = isNodeEmpty(node.left) ? node.right : node.left;
            } else {
                node = reorganizeSubTree(node); // In case of 2 children
            }
            return !isNodeEmpty(node) ? rebalance(node) : null;
        }

        return isValueLessThanNode(node, value)
                ? deleteRecursive(node.left, value)
                : deleteRecursive(node.right, value);
    }

    private Node reorganizeSubTree(Node root) {
        Integer smallestValue = mostLeftChild(root.right).value;
        root.value = smallestValue;
        root.right = deleteRecursive(root.right, smallestValue);
        return root;
    }

    private Node mostLeftChild(Node node) {
        return isNodeEmpty(node.left) ? node : mostLeftChild(node.left);
    }

    @Override
    public void traverseInOrder() {
        traverseInOrder(root);
    }

    private void traverseInOrder(Node node) {
        if (!isNodeEmpty(node)) {
            traverseInOrder(node.left);
            System.out.print(node.value + " ");
            traverseInOrder(node.right);
        }
    }

    @Override
    public void traversePreOrder() {
        traversePreOrder(root);
    }

    private void traversePreOrder(Node node) {
        if (!isNodeEmpty(node)) {
            System.out.print(node.value + " ");
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    @Override
    public void traversePostOrder() {
        traversePostOrder(root);
    }

    private void traversePostOrder(Node node) {
        if (!isNodeEmpty(node)) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.print(node.value + " ");
        }
    }

    private boolean isUnbalancePositive(int balance) {
        return balance > 1;
    }

    private boolean isUnbalanceNegative(int balance) {
        return balance < -1;
    }

    private boolean isBalanceFactorPositive(int balance) {
        return balance >= 0;
    }

    private boolean isBalanceFactorNegative(int balance) {
        return balance <= 0;
    }

    private boolean isNodeEmpty(Node node) {
        return node == null;
    }

    private boolean isValueEqualToNode(Node node, Integer value) {
        return value.equals(node.value);
    }

    private boolean isValueLessThanNode(Node node, Integer value) {
        return value < node.value;
    }

    private boolean isValueGreaterThanNode(Node node, Integer value) {
        return value > node.value;
    }

    protected class Node {
        private Integer value;
        private Integer height;
        private Node left;
        private Node right;

        public Node(Integer value) {
            this.value = value;
            this.height = 0;
            left = right = null;
        }

        private static int height(Node node) {
            return node != null ? node.height : -1;
        }

        public static void updateHeight(Node node) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }

        public static int balanceFactor(Node node) {
            return height(node.right) - height(node.left);
        }
    }
}
