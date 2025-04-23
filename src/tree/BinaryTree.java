package tree;

public class BinaryTree<T extends Comparable<T>, E> implements Tree<T, E> {

    private Node root;

    public BinaryTree() {
        root = null;
    }

    @Override
    public void insert(T value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node current, T value) {
        if (isNodeEmpty(current)) return new Node(value);

        if (isValueLessThanNode(current, value)) {
            current.left = insertRecursive(current.left, value);
        } else if (isValueGreaterThanNode(current, value)) {
            current.right = insertRecursive(current.right, value);
        }
        return current;
    }

    @Override
    public E find(T value) {
        return findRecursive(root, value);
    }

    private E findRecursive(Node current, T value) {
        if (isNodeEmpty(current)) return null;
        if (isValueEqualToNode(current, value)) return (E) current;

        return isValueLessThanNode(current, value)
                ? findRecursive(current.left, value)
                : findRecursive(current.right, value);
    }

    @Override
    public void remove(T value) {
        root = removeRecursive(root, value);
    }

    private Node removeRecursive(Node current, T value) {
        if (isNodeEmpty(current)) return null;

        if (isValueEqualToNode(current, value)) {
            if (isNodeEmpty(current.left) || isNodeEmpty(current.right)) {
                current = isNodeEmpty(current.left) ? current.right : current.left;
            } else {
                current = reorganizeSubTree(current); // In case of 2 children
            }
            return current;
        }

        if (isValueLessThanNode(current, value)) {
            current.left = removeRecursive(current.left, value);
            return current;
        }
        current.right = removeRecursive(current.right, value);
        return current;
    }

    // Take the smallest value in the RIGHT subtree to replace the removed parent.
    // Another option would be to pick the biggest value int the left subtree.
    // After the process we clean the right/left subtree by removing the old child.
    private Node reorganizeSubTree(Node root) {
        T smallestValue = mostLeftChild(root.right).value;
        root.value = smallestValue;
        root.right = removeRecursive(root.right, smallestValue);
        return root;
    }

    private Node mostLeftChild(Node root) {
        return isNodeEmpty(root.left) ? root : mostLeftChild(root.left);
    }

    @Override
    public String traverseInOrder() {
        StringBuilder sb = new StringBuilder();
        sb = traverseInOrder(root, sb);
        return sb.toString();
    }

    private StringBuilder traverseInOrder(Node node, StringBuilder sb) {
        if (!isNodeEmpty(node)) {
            traverseInOrder(node.left, sb);
            sb.append(String.format("%d ", node.value));
            traverseInOrder(node.right, sb);
        }
        return sb;
    }

    @Override
    public String traversePreOrder() {
        StringBuilder sb = new StringBuilder();
        sb = traversePreOrder(root, sb);
        return sb.toString();
    }

    private StringBuilder traversePreOrder(Node node, StringBuilder sb) {
        if (!isNodeEmpty(node)) {
            sb.append(String.format("%d ", node.value));
            traversePreOrder(node.left, sb);
            traversePreOrder(node.right, sb);
        }
        return sb;
    }

    @Override
    public String traversePostOrder() {
        StringBuilder sb = new StringBuilder();
        sb = traversePostOrder(root, sb);
        return sb.toString();
    }

    private StringBuilder traversePostOrder(Node node, StringBuilder sb) {
        if (!isNodeEmpty(node)) {
            traversePostOrder(node.left, sb);
            traversePostOrder(node.right, sb);
            sb.append(String.format("%d ", node.value));
        }
        return sb;
    }

    private boolean isNodeEmpty(Node current) {
        return current == null;
    }

    private boolean isValueEqualToNode(Node current, T value) {
        return value.compareTo(current.value) == 0;
    }

    private boolean isValueLessThanNode(Node current, T value) {
        return value.compareTo(current.value) < 0;
    }

    private boolean isValueGreaterThanNode(Node current, T value) {
        return value.compareTo(current.value) > 0;
    }

    private class Node {
        private T value;
        private Node left;
        private Node right;

        public Node(T value) {
            this.value = value;
            left = right = null;
        }
    }
}
