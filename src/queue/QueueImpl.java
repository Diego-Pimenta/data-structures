package queue;

public class QueueImpl<T> implements Queue<T> {

    private class Element {
        private T value;
        private Element next;

        public Element(T value) {
            this.value = value;
            next = null;
        }
    }

    private Element first, last;
    private int size;

    public QueueImpl() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public void enqueue(T element) {
        Element e = new Element(element);
        if (isEmpty()) {
            first = e;
            last = e;
            size++;
            return;
        }
        last.next = e;
        last = e;
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty!");
        }
        Element e = first;
        first = first.next;
        if (first == null) {
            last = null;
        }
        size--;
        return e.value;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty!");
        }
        return first.value;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    @Override
    public int size() {
        return size;
    }
}
