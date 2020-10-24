/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] RQ;
    private int high = 0;

    public RandomizedQueue() {
        RQ = (Item []) new Object[1];
    }

    private void resize(int n) {
        Item[] newArray = Arrays.copyOfRange(RQ, 0, n);
        RQ = newArray;
    }

    public boolean isEmpty() {
        return high == 0;
    }

    public void enqueue(Item item) {
        if (item != null) {
            if (high == RQ.length) {
                resize(RQ.length * 2);
            }
            RQ[high++] = item;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (high < (RQ.length / 4)) {
            resize((RQ.length)/2);
        }
        int randomIndex;
        if (high > 1) {
            randomIndex = (int) (Math.random() * high);
            Item returnval = RQ[randomIndex];
            RQ[randomIndex] = RQ[--high];
            return returnval;
        }
        else {
            return RQ[--high];
        }
    }

    public int size() {
        return high;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = 0;
        if (high > 1) {
            randomIndex = (int) (Math.random() * high);
        }
        return RQ[randomIndex];
    }

    private class RQItertable implements Iterator<Item> {
        private Item[] RQIter;
        private int high_iter = high;

        public RQItertable() {
            RQIter = RQ;
        }

        public boolean hasNext() {
            return high_iter != 0;
        }

        public Item next() {
            if (high_iter == 0) {
                throw new NoSuchElementException();
            }
            if (high_iter > 1) {
                int randomIndex = (int) (Math.random() * high_iter);
                Item returnval = RQIter[randomIndex];
                RQIter[randomIndex] = RQIter[--high_iter];
                return returnval;
            }
            else {
                return RQIter[--high_iter];
            }

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new RQItertable();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            test.enqueue(i);
            System.out.println("Added element: " + i);
            System.out.println("Current number of elements in queue: " + test.size() + "\n");

        }


        System.out.print("\nIterator test:\n[");
        for (Integer elem: test)
            System.out.print(elem + " ");
        System.out.println("]\n");
    }
}
