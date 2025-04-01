package list;

public interface List<T> {

    void insertAtBegin(T element);

    void insertAtEnd(T element);

    void insert(T element, int index);

    void removeFirst();

    void removeLast();

    void remove(int index);

    T getFirst();

    T getLast();

    T get(int index);

    int indexOf(T element);

    boolean isEmpty();

    int size();
}
