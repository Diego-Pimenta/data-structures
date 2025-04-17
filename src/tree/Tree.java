package tree;

public interface Tree<T> {

    void insert(T value);

    boolean contains(T value);

    void remove(T value);

    T smallest();

    T biggest();

    void traverseInOrder();

    void traversePreOrder();

    void traversePostOrder();

    void traverseLevelOrder();
}
