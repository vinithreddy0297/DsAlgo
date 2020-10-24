/* *****************************************************************************
 *  Name: DVS Vinith Reddy
 *  Date:
 *  Description: Deque Implementation
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private DequeIterable dequeObj;
    private Node DQ;
    private Node FirstNode;
    private int noOfItems = 0;
    public static void main(String[] args) {
        Deque<Integer> DQ = new Deque<Integer>();
        DQ.addFirst(1);
        DQ.addLast(2);
        DQ.addLast(3);
        System.out.println(DQ.removeFirst());
        System.out.println(DQ.removeLast());
        System.out.println(DQ.size());
    }

    public Deque() {
        DQ = null;
    }

    private class Node {
        Item item;
        Node next;
        Node pre;
    }

    private class DequeIterable implements Iterator<Item> {
        Node DequeIter = FirstNode;

        public boolean hasNext() {
            return DequeIter != null;
        }

        public Item next() {
            if (DequeIter != null) {
                Node oldNode = DequeIter;
                DequeIter = DequeIter.next;
                return oldNode.item;
            }
            else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isEmpty() {
        return DQ == null;
    }

    public int size() {
        return noOfItems;
    }

    public void addFirst(Item item) {
        if (item != null) {
            if (isEmpty()) {
                DQ = new Node();
                DQ.item = item;
                DQ.next = null;
                DQ.pre = null;
                FirstNode = DQ;
            }
            else {
                Node newNode = new Node();
                newNode.item = item;
                newNode.next = FirstNode;
                FirstNode.pre = newNode;
                newNode.pre = null;
                FirstNode = newNode;
            }
            noOfItems += 1;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            DQ = new Node();
            DQ.item = item;
            DQ.next = null;
            FirstNode = DQ;
        }
        else {
            Node oldNode = DQ;
            DQ = new Node();
            DQ.item = item;
            DQ.next = null;
            DQ.pre = oldNode;
            oldNode.next = DQ;
        }
        noOfItems += 1;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else if (noOfItems > 1) {
            Node firstNodeCopy = FirstNode;
            FirstNode = FirstNode.next;
            FirstNode.pre = null;
            noOfItems -= 1;
            return firstNodeCopy.item;
        }
        else {
            Item firstNodeItem = FirstNode.item;
            FirstNode = null;
            DQ = null;
            noOfItems -= 1;
            return firstNodeItem;
        }
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else if (noOfItems > 1) {
            Node lastNode = DQ;
            DQ = DQ.pre;
            DQ.next = null;
            noOfItems -= 1;
            return lastNode.item;
        }
        else {
            Item firstNodeItem = FirstNode.item;
            FirstNode = null;
            DQ = null;
            noOfItems -= 1;
            return firstNodeItem;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterable();
    }
}
