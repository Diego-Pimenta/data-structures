package tree;

public interface Tree<T, E> {

    void insert(T value);

    E find(T value);

    void remove(T value);

    void traverseInOrder();

    void traversePreOrder();

    void traversePostOrder();
}
