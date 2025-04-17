package tree;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<T extends Comparable<T>> implements Tree<T> {

    private class Node {
        private T value;
        private Node left;
        private Node right;

        public Node(T value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

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
    public boolean contains(T value) {
        return containsRecursive(root, value);
    }

    private boolean containsRecursive(Node current, T value) {
        if (isNodeEmpty(current)) return false;
        if (isValueEqualToNode(current, value)) return true;

        return isValueLessThanNode(current, value)
                ? containsRecursive(current.left, value)
                : containsRecursive(current.right, value);
    }

    @Override
    public void remove(T value) {
        root = removeRecursive(root, value);
    }

    private Node removeRecursive(Node current, T value) {
        if (isNodeEmpty(current)) return null;

        if (isValueEqualToNode(current, value)) {
            if (areBothChildrenEmpty(current)) {
                return null;
            } else if (isRightNodeEmpty(current)) {
                return current.left;
            } else if (isLeftNodeEmpty(current)) {
                return current.right;
            } else {
                return reorganizeSubTree(current); // Em caso de 2 filhos
            }
        }

        return isValueLessThanNode(current, value)
                ? removeRecursive(current.left, value)
                : removeRecursive(current.right, value);
    }

    private Node reorganizeSubTree(Node root) {
        T smallestValue = findSmallestValue(root.right);
        root.value = smallestValue;
        root.right = removeRecursive(root.right, smallestValue);
        return root;
    }

    @Override
    public T smallest() {
        if (isNodeEmpty(root)) throw new EmptyTreeException("Tree is empty!");

        return findSmallestValue(root);
    }

    private T findSmallestValue(Node root) {
        return isLeftNodeEmpty(root)
                ? root.value
                : findSmallestValue(root.left);
    }

    @Override
    public T biggest() {
        if (isNodeEmpty(root)) throw new EmptyTreeException("Tree is empty!");

        return findBiggestValue(root);
    }

    private T findBiggestValue(Node root) {
        return isRightNodeEmpty(root)
                ? root.value
                : findBiggestValue(root.right);
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

    @Override
    public void traverseLevelOrder() {
        if (isNodeEmpty(root)) return;

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node node = nodes.remove();

            System.out.println(" " + node.value);

            if (!isLeftNodeEmpty(node)) nodes.add(node.left);
            if (!isRightNodeEmpty(node)) nodes.add(node.right);
        }
    }

    private boolean isNodeEmpty(Node current) {
        return current == null;
    }

    private boolean isLeftNodeEmpty(Node current) {
        return current.left == null;
    }

    private boolean isRightNodeEmpty(Node current) {
        return current.right == null;
    }

    private boolean areBothChildrenEmpty(Node current) {
        return current.left == null && current.right == null;
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
