package list;

public class CircularLinkedList<T> implements List<T> {

    private class Element {
        private T value;
        private Element next;
        private Element prev;

        public Element(T value) {
            this.value = value;
            next = null;
            prev = null;
        }
    }

    private Element first, last, current;
    private int size;

    public CircularLinkedList() {
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
        e.prev = last;
        last.next = e;
        first.prev = e;
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
        e.next = last.next;
        e.prev = last;
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
        setCurrentAtIndex(index);

        Element e = new Element(element);
        e.next = current.next;
        e.prev = current.prev;
        current.prev.next = e;
        current.next.prev = e;
        size++;
    }

    @Override
    public void removeFirst() {
        if (isEmpty()) {
            throw new EmptyListException("List is empty!");
        }
        first = first.next;
        first.prev = last;
        last.next = first;
        current = null;
        size--;
    }

    @Override
    public void removeLast() {
        if (isEmpty()) {
            throw new EmptyListException("List is empty!");
        }
        last = last.prev;
        last.next = first;
        first.prev = last;
        current = null;
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
        setCurrentAtIndex(index);

        current.prev.next = current.next;
        current.next.prev = current.prev;
        current = null;
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
        if (isIndexLessThanHalfSize(index)) {
            setCurrentGoingForward(index);
        } else {
            setCurrentGoingBackward(index);
        }
    }

    private boolean isIndexLessThanHalfSize(int index) {
        return index <= size / 2;
    }

    private void setCurrentGoingForward(int index) {
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
    }

    private void setCurrentGoingBackward(int index) {
        for (int i = size; i > index + 1; i--) {
            current = current.prev;
        }
    }
}
