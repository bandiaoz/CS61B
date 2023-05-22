package deque;

import java.util.Iterator;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    @SuppressWarnings("unchecked")
    private static <T> T[] cast(Object[] a) {
        return (T[]) a;
    }

    /**
     * Creates an empty array deque.
     */
    public ArrayDeque() {
        items = cast(new Object[8]);
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     * Returns the index immediately before a given index.
     */
    private int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    /**
     * Returns the index immediately after a given index.
     */
    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /**
     * Resizes the underlying array to the target capacity.
     */
    private void resize(int capacity) {
        T[] newItems = cast(new Object[capacity]);
        int firstIndex = plusOne(nextFirst);
        for (int i = 0; i < size; i++, firstIndex = plusOne(firstIndex)) {
            newItems[i] = items[firstIndex];
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /**
     * Adds an item of type T to the front of the deque. You can assume that item is never null.
     */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque. You can assume that item is never null.
     */
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        int p = plusOne(nextFirst);
        while (p != nextLast) {
            System.out.print(items[p] + " ");
            p = plusOne(p);
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int firstIndex = plusOne(nextFirst);
        T firstItem = items[firstIndex];
        items[firstIndex] = null;
        nextFirst = firstIndex;
        size -= 1;
        if (items.length >= 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
        return firstItem;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int lastIndex = minusOne(nextLast);
        T lastItem = items[lastIndex];
        items[lastIndex] = null;
        nextLast = lastIndex;
        size -= 1;
        if (items.length >= 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
        return lastItem;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        int firstIndex = plusOne(nextFirst);
        return items[(firstIndex + index) % items.length];
    }

    /**
     * The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator.
     */
    private class ArrayDequeIterator implements Iterator<T> {
        private int index;

        public ArrayDequeIterator() {
            index = 0;
        }

        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            T item = get(index);
            index += 1;
            return item;
        }
    }

    /**
     * The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator.
     */
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    /**
     * Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents (as goverened by
     *  the generic T’s equals method) in the same order.
     * (ADDED 2/12: You’ll need to use the instance of keywords for this)
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof ArrayDeque)) {
            return false;
        }
        ArrayDeque<T> other = (ArrayDeque<T>) o;
        if (size != other.size) {
            return false;
        }
        Iterator<T> thisIterator = iterator();
        Iterator<T> otherIterator = other.iterator();
        while (thisIterator.hasNext()) {
            if (!thisIterator.next().equals(otherIterator.next())) {
                return false;
            }
        }
        return true;
    }
}
