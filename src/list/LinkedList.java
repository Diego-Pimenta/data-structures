package list;

public class LinkedList<T> implements List<T> {

    private class Element {
        private T value;
        private Element next;

        public Element(T value) {
            this.value = value;
            next = null;
        }
    }

    private Element first, last, current;
    private int size;

    public LinkedList() {
        first = null;
        last = null;
        current = null;
        size = 0;
    }

    @Override
    public void insertAtBegin(T element) {
        Element e = new Element(element);
        if (isEmpty()) {
            insertFirstElement(e);
            return;
        }
        e.next = first;
        first = e;
        size++;
    }

    @Override
    public void insertAtEnd(T element) {
        Element e = new Element(element);
        if (isEmpty()) {
            insertFirstElement(e);
            return;
        }
        last.next = e;
        last = e;
        size++;
    }

    @Override
    public void insert(T element, int index) {
        if (index < 0 || index > size) {
            throw new NullPointerException("Index out of boundaries!");
        }
        if (index == 0) {
            insertAtBegin(element);
            return;
        }
        if (index == size) {
            insertAtEnd(element);
            return;
        }
        setCurrentAtIndex(index - 1);

        Element e = new Element(element);
        e.next = current.next;
        current.next = e;
        size++;
    }

    @Override
    public void removeFirst() {
        if (isEmpty()) {
            throw new EmptyListException("List is empty!");
        }
        Element e = first.next;
        first = e;
        current = e;
        size--;
    }

    @Override
    public void removeLast() {
        if (isEmpty()) {
            throw new EmptyListException("List is empty!");
        }
        setCurrentAtIndex(size - 2);

        current.next = null;
        last = current;
        size--;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new NullPointerException("Index out of boundaries!");
        }
        if (index == 0) {
            removeFirst();
            return;
        }
        if (index == size - 1) {
            removeLast();
            return;
        }
        setCurrentAtIndex(index - 1);

        Element e = new Element(current.next.value);
        current.next = e.next;
        size--;
    }

    @Override
    public T getFirst() {
        if (isEmpty()) {
            throw new EmptyListException("List is empty!");
        }
        return first.value;
    }

    @Override
    public T getLast() {
        if (isEmpty()) {
            throw new EmptyListException("List is empty!");
        }
        return last.value;
    }

    @Override
    public T get(int index) {
        if (isEmpty()) {
            throw new EmptyListException("List is empty!");
        }
        if (index < 0 || index >= size) {
            throw new NullPointerException("Index out of boundaries!");
        }
        setCurrentAtIndex(index);
        return current.value;
    }

    @Override
    public int indexOf(T element) {
        current = first;
        for (int i = 0; i < size; i++) {
            if (element.equals(current)) {
                return i;
            }
        }
        throw new NullPointerException("Element does not exist!");
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    @Override
    public int size() {
        return size;
    }

    private void insertFirstElement(Element e) {
        first = e;
        last = e;
        current = e;
        size++;
    }

    private void setCurrentAtIndex(int index) {
        current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
    }
}
