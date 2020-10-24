// /* *****************************************************************************
//  *  Name:
//  *  Date:
//  *  Description:
//  **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class KdTree {
    private Tree<Point2D> treeset;

    public KdTree() {
        treeset = new Tree<>();
    }

    public boolean isEmpty() {
        if (treeset.size == 0)
            return true;
        else
            return false;
    }

    public int size() {
        return treeset.size;
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        treeset.insert(point);
    }

    public boolean contains(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        if (size() > 0)
            return treeset.contains(point);
        return false;
    }

    public void draw() {
        drawlines(treeset.root);
    }

    private void drawlines(DoublyLinkedList<Point2D> node)
    {
        double yaxis = 1;
        double xaxis = 1;
        if(node != null)
        {
            if (node.compareelem % 2 == 0) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.line(node.point.x(), 0, node.point.x(), 1);
            }
            else {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.line(0, node.point.y(), 1, node.point.y());
            }
            drawlines(node.left);
            drawlines(node.right);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        return treeset.range(rect);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (size() > 0)
            return treeset.nearest(p);
        return null;
    }

    private class DoublyLinkedList<T> {
        private DoublyLinkedList<T> left;
        private Point2D point;
        private DoublyLinkedList<T> right;
        // even or odd. odd compare with x-coordinate, even compare with y-coordinate
        private int compareelem;
    }

    private class Tree<T> {
        private int size;
        private DoublyLinkedList<Point2D> root;
        private double minDistance;

        private Tree() {
            size = 0;
        }

        private void insert(Point2D t, DoublyLinkedList<Point2D> node)
        {
            if (node.compareelem % 2 == 0) {
                if (t.x() < node.point.x())
                {
                    if (node.left == null)
                    {
                        DoublyLinkedList<Point2D> newnode = insertnew(t);
                        node.left = newnode;
                        newnode.compareelem = node.compareelem + 1;
                        size += 1;
                    }
                    else
                        insert(t, node.left);
                }
                else if (t.x() > node.point.x()) {
                    if (node.right == null) {
                        DoublyLinkedList<Point2D> newnode = insertnew(t);
                        node.right = newnode;
                        newnode.compareelem = node.compareelem + 1;
                        size += 1;
                    }
                    else
                        insert(t, node.right);
                }
                else
                {
                    if (t.equals(node.point))
                        return;
                    if (t.y() < node.point.y())
                    {
                        if(node.left == null)
                        {
                            DoublyLinkedList<Point2D> newnode = insertnew(t);
                            node.left = newnode;
                            newnode.compareelem = node.compareelem + 1;
                            size += 1;
                        }
                        else
                            insert(t, node.left);
                    }
                    else
                    {
                        if (node.right == null)
                        {
                            DoublyLinkedList<Point2D> newnode = insertnew(t);
                            node.right = newnode;
                            newnode.compareelem = node.compareelem + 1;
                            size += 1;
                        }
                        else
                            insert(t, node.right);
                    }
                }
            }
            else
            {
                if (t.y() < node.point.y())
                {
                    if(node.left == null)
                    {
                        DoublyLinkedList<Point2D> newnode = insertnew(t);
                        node.left = newnode;
                        newnode.compareelem = node.compareelem + 1;
                        size += 1;
                    }
                    else
                        insert(t, node.left);
                }
                else if(t.y() > node.point.y())
                {
                    if (node.right == null)
                    {
                        DoublyLinkedList<Point2D> newnode = insertnew(t);
                        node.right = newnode;
                        newnode.compareelem = node.compareelem + 1;
                        size += 1;
                    }
                    else
                        insert(t, node.right);
                }
                else
                {
                    if (t.equals(node.point))
                        return;
                    if (t.x() < node.point.x())
                    {
                        if (node.left == null)
                        {
                            DoublyLinkedList<Point2D> newnode = insertnew(t);
                            node.left = newnode;
                            newnode.compareelem = node.compareelem + 1;
                            size += 1;
                        }
                        else
                            insert(t, node.left);
                    }
                    else {
                        if (node.right == null) {
                            DoublyLinkedList<Point2D> newnode = insertnew(t);
                            node.right = newnode;
                            newnode.compareelem = node.compareelem + 1;
                            size += 1;
                        }
                        else
                            insert(t, node.right);
                    }
                }
            }
        }

        private DoublyLinkedList<Point2D> insertnew(Point2D t)
        {
            DoublyLinkedList<Point2D> dDoublyLinkedList = new DoublyLinkedList<>();
            dDoublyLinkedList.right = null;
            dDoublyLinkedList.left = null;
            dDoublyLinkedList.point = t;
            return dDoublyLinkedList;
        }

        private void insert(Point2D t) {
            if (size == 0) {
                DoublyLinkedList<Point2D> rootnode = new DoublyLinkedList<>();
                rootnode.left = null;
                rootnode.right = null;
                rootnode.point = t;
                rootnode.compareelem = 0;
                size += 1;
                root = rootnode;
            }
            else
                insert(t, root);
        }

        public boolean contains(Point2D point2D) {
            if (point2D.equals(root.point)) {
                return true;
            }
            else {
                DoublyLinkedList<Point2D> node = root;
                while (true) {
                    if (node.compareelem % 2 == 0) {
                        if (point2D.x() < node.point.x())
                            node = node.left;
                        else if(point2D.x() > node.point.x())
                            node = node.right;
                        else {
                            if (point2D.y() < node.point.y())
                                node = node.left;
                            else
                                node = node.right;
                        }

                        if (node == null)
                            return false;
                        if (node.point.compareTo(point2D) == 0) {
                            return true;
                        }
                    }
                    else {
                        if (point2D.y() < node.point.y())
                            node = node.left;
                        else if (point2D.y() > node.point.y())
                            node = node.right;
                        else {
                            if (point2D.x() < node.point.x())
                                node = node.left;
                            else
                                node = node.right;
                        }
                        if (node == null)
                            return false;
                        if (node.point.compareTo(point2D) == 0)
                            return true;
                    }
                }
            }
        }

        public Iterable<Point2D> range(RectHV rect) {
            return new TreeIter(rect);
        }

        public class TreeIter implements Iterable<Point2D> {

            private RectHV rect;

            public TreeIter(RectHV rect) {
                this.rect = rect;
            }

            public Iterator<Point2D> iterator() {
                return new TreeIterator(rect);
            }
        }

        public class TreeIterator implements Iterator<Point2D> {
            private ArrayList<Point2D> arrayList;

            public TreeIterator(RectHV rect) {
                arrayList = new ArrayList<>();
                arrayList = range(root, rect);
            }

            private ArrayList<Point2D> range(DoublyLinkedList<Point2D> node, RectHV rect) {
                if (node == null) {
                    return arrayList;
                }
                if (rect.contains(node.point)) {
                    arrayList.add(node.point);
                }
                if (node.left == null && node.right == null) {
                    return arrayList;
                }
                if (node.compareelem % 2 == 0) {
                    // compare x
                    if (rect.xmax() < node.point.x()) {
                        arrayList = range(node.left, rect);
                    }
                    if (rect.xmin() > node.point.x()) {
                        arrayList = range(node.right, rect);
                    }
                    if (rect.xmin() < node.point.x() && rect.xmax() > node.point.x()) {
                        arrayList = range(node.left, rect);
                        arrayList = range(node.right, rect);
                    }
                }
                else {
                    // compare y
                    if (rect.ymax() < node.point.y()) {
                        arrayList = range(node.left, rect);
                    }
                    if (rect.ymin() > node.point.y()) {
                        arrayList = range(node.right, rect);
                    }

                    if (rect.ymin() < node.point.y() && rect.ymax() > node.point.y()) {
                        arrayList = range(node.right, rect);
                        arrayList = range(node.left, rect);
                    }
                }
                return arrayList;
            }


            public boolean hasNext() {
                return arrayList.size() != 0;
            }

            public Point2D next() {
                return arrayList.remove(0);
            }
        }

        private Point2D nearest(Point2D p) {
            return nearest(p, root, root.point);
        }

        private Point2D nearest(Point2D p, DoublyLinkedList<Point2D> node, Point2D mindistpoint) {
            if (node == null) {
                return mindistpoint;
            }
            if (node.point.distanceTo(p) <= minDistance) {
                mindistpoint = node.point;
                minDistance = node.point.distanceTo(p);
            }
            if (node.equals(root)) {
                minDistance = node.point.distanceTo(p);
                mindistpoint = node.point;
                if (p.x() < node.point.x())
                {
                    mindistpoint = nearest(p, node.left, mindistpoint);
                    mindistpoint = nearest(p, node.right, mindistpoint);
                }
                else
                {
                    mindistpoint = nearest(p, node.right, mindistpoint);
                    mindistpoint = nearest(p, node.left, mindistpoint);
                }
            }
            else {
                if (node.compareelem % 2 == 0) {
                    if (p.x() < node.point.x()) {
                        mindistpoint = nearest(p, node.left, mindistpoint);
                        mindistpoint = nearest(p, node.right, mindistpoint);
                    }
                    else
                    {
                        mindistpoint = nearest(p, node.right, mindistpoint);
                        mindistpoint = nearest(p, node.left, mindistpoint);
                    }
                }
                else {
                    if (p.y() < node.point.y()) {
                        mindistpoint = nearest(p, node.left, mindistpoint);
                        mindistpoint = nearest(p, node.right, mindistpoint);
                    }
                    else
                    {
                        mindistpoint = nearest(p, node.right, mindistpoint);
                        mindistpoint = nearest(p, node.left, mindistpoint);
                    }
                }
            }
            return mindistpoint;
        }
    }

    public static void main(String[] args) {
        KdTree kd = new KdTree();
        String filename = args[0];
        In in = new In(filename);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kd.insert(p);
        }
        System.out.println(kd.nearest(new Point2D(0.6015625, 0.46875)));
    }
}

