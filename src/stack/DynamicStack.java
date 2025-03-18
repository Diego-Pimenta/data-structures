package stack;

public class DynamicStack<T> implements Stack<T> {

    private class Element {
        private T value;
        private Element next;

        public Element(T value) {
            this.value = value;
            next = null;
        }
    }

    private Element top;
    private int size;

    public DynamicStack() {
        top = null;
        size = 0;
    }

    @Override
    public void push(T element) {
        Element e = new Element(element);
        if (isEmpty()) {
            top = e;
            size++;
            return;
        }
        e.next = top;
        top = e;
        size++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty!");
        }
        Element e = top;
        top = e.next;
        size--;
        return e.value;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty!");
        }
        return top.value;
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
