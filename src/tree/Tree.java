package tree;

public interface Tree<T, E> {

    void insert(T value);

    E find(T value);

    void remove(T value);

    String traverseInOrder();

    String traversePreOrder();

    String traversePostOrder();
}
