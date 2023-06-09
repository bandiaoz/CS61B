package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 * <p>
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author banidaoz
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* The initial size of the hash table */
    private Collection<Node>[] buckets;

    /* The number of key-value pairs in the map */
    private int size;

    /* The max load factor of the map */
    private final double maxLoad;

    /* The current load factor of the map */
    private int loadFactor() {
        return size / buckets.length;
    }

    /** Constructors */
    public MyHashMap() {
        this.buckets = createTable(16);
        this.maxLoad = 0.75;
        this.clear();
    }

    public MyHashMap(int initialSize) {
        this.buckets = createTable(initialSize);
        this.maxLoad = 0.75;
        this.clear();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.buckets = createTable(initialSize);
        this.maxLoad = maxLoad;
        this.clear();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new HashSet<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    @Override
    public void clear() {
        this.size = 0;
        for (Collection<Node> bucket : this.buckets) {
            bucket.clear();
        }
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    @Override
    public boolean containsKey(K key) {
        int hashCode = hash(key);
        for (Node node : buckets[hashCode]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int hashCode = hash(key);
        for (Node node : buckets[hashCode]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize(int newSize) {
        MyHashMap<K, V> newMap = new MyHashMap<>(newSize);
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                newMap.put(node.key, node.value);
            }
        }
        this.buckets = newMap.buckets;
        this.size = newMap.size;
    }

    @Override
    public void put(K key, V value) {
        if (loadFactor() > maxLoad) {
            resize(size * 2);
        }
        int hashCode = hash(key);
        for (Node node : buckets[hashCode]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        buckets[hashCode].add(createNode(key, value));
        size++;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                keySet.add(node.key);
            }
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        int hashCode = hash(key);
        if (containsKey(key)) {
            for (Node node : buckets[hashCode]) {
                if (node.key.equals(key)) {
                    buckets[hashCode].remove(node);
                    size--;
                    return node.value;
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        int hashCode = hash(key);
        if (containsKey(key)) {
            for (Node node : buckets[hashCode]) {
                if (node.key.equals(key) && node.value.equals(value)) {
                    buckets[hashCode].remove(node);
                    size--;
                    return node.value;
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
