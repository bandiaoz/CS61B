package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V>  implements Map61B<K, V> {
    private class BSTNode {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private BSTNode left;
        private BSTNode right;

        public BSTNode(K key_, V value_) {
            key = key_;
            value = value_;
            left = null;
            right = null;
        }
    }

    /* Root node of the tree. */
    private BSTNode root;

    /* The number of key-value pairs in the tree */
    private int size;

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private boolean containsKeyHelper(BSTNode node, K key) {
        if (node == null) {
            return false;
        }
        if (node.key.equals(key)) {
            return true;
        }
        if (node.key.compareTo(key) > 0) {
            return containsKeyHelper(node.left, key);
        } else {
            return containsKeyHelper(node.right, key);
        }
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    private V getHelper(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        if (node.key.equals(key)) {
            return node.value;
        }
        if (node.key.compareTo(key) > 0) {
            return getHelper(node.left, key);
        } else {
            return getHelper(node.right, key);
        }
    }

    /* Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.*/
    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    private BSTNode putHelper(BSTNode node, K key, V value) {
        if (node == null) {
            size += 1;
            return new BSTNode(key, value);
        }
        if (node.key.equals(key)) {
            node.value = value;
        } else if (node.key.compareTo(key) > 0) {
            node.left = putHelper(node.left, key, value);
        } else {
            node.right = putHelper(node.right, key, value);
        }
        return node;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
    }

    /* Returns a Set view of the keys contained in this map. */
    private void traverseAdd(BSTNode p, Set<K> set) {
        if (p == null) {
            return;
        }
        set.add(p.key);
        traverseAdd(p.left, set);
        traverseAdd(p.right, set);
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        traverseAdd(root, set);
        return set;
    }

    private void printInOrderHelper(BSTNode node) {
        if (node == null) {
            return;
        }
        printInOrderHelper(node.left);
        System.out.println(node.key + " " + node.value);
        printInOrderHelper(node.right);
    }

    public void printInOrder() {
        printInOrderHelper(root);
    }

    private BSTNode removeHelper(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        if (node.key.equals(key)) {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                BSTNode p = node.right;
                while (p.left != null) {
                    p = p.left;
                }
                node.key = p.key;
                node.value = p.value;
                node.right = removeHelper(node.right, p.key);
            }
        } else if (node.key.compareTo(key) > 0) {
            node.left = removeHelper(node.left, key);
        } else {
            node.right = removeHelper(node.right, key);
        }
        return node;
    }

    /* Removes the mapping for the specified key from this map if present. */
    @Override
    public V remove(K key) {
        V value = get(key);
        if (value == null) {
            return null;
        } else {
            root = removeHelper(root, key);
            size -= 1;
            return value;
        }
    }

    @Override
    public V remove(K key, V value) {
        V valueInMap = get(key);
        if (valueInMap == null || !valueInMap.equals(value)) {
            return null;
        } else {
            root = removeHelper(root, key);
            size -= 1;
            return valueInMap;
        }
    }


    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
