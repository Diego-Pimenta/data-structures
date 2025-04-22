package tree;

public class BinaryTree<T extends Comparable<T>, E> implements Tree<T, E> {

    private class Node {
        private T value;
        private Node left;
        private Node right;

        public Node(T value) {
            this.value = value;
            left = right = null;
        }
    }

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
                return isNodeEmpty(current.left) ? current.right : current.left;
            } else {
                return reorganizeSubTree(current); // In case of 2 children
            }
        }

        return isValueLessThanNode(current, value)
                ? removeRecursive(current.left, value)
                : removeRecursive(current.right, value);
    }

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
    public void traverseInOrder() {
        traverseInOrder(root);
    }

    private void traverseInOrder(Node current) {
        if (!isNodeEmpty(current)) {
            traverseInOrder(current.left);
            System.out.print(" " + current.value);
            traverseInOrder(current.right);
        }
    }

    @Override
    public void traversePreOrder() {
        traversePreOrder(root);
    }

    private void traversePreOrder(Node current) {
        if (!isNodeEmpty(current)) {
            System.out.print(" " + current.value);
            traversePreOrder(current.left);
            traversePreOrder(current.right);
        }
    }

    @Override
    public void traversePostOrder() {
        traversePostOrder(root);
    }

    private void traversePostOrder(Node current) {
        if (!isNodeEmpty(current)) {
            traversePostOrder(current.left);
            traversePostOrder(current.right);
            System.out.print(" " + current.value);
        }
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
}
