// /* *****************************************************************************
//  *  Name:
//  *  Date:
//  *  Description:
//  **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> treeset;

    public PointSET() {
        treeset = new TreeSet<>();
    }

    public boolean isEmpty() {
        return treeset.isEmpty();
    }

    public int size() {
        return treeset.size();
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        treeset.add(point);
    }

    public boolean contains(Point2D point) {
        if (point == null)
            throw new IllegalArgumentException();
        if (size() > 0)
            return treeset.contains(point);
        return false;
    }

    public void draw() {
        for (Point2D point : treeset) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        return new RectIterabele(rect);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (size() == 0)
            return null;
        Point2D nearest = treeset.first();
        double nearest_dist = p.distanceTo(nearest);
        for (Point2D point : treeset) {
            if (point.distanceTo(p) < nearest_dist) {
                nearest_dist = point.distanceTo(p);
                nearest = point;
            }
        }
        return nearest;
    }

    private class RectIterabele implements Iterable<Point2D> {
        private RectHV rect;

        private RectIterabele(RectHV rect) {
            this.rect = rect;
        }

        public Iterator<Point2D> iterator() {
            return new RectIterator(rect);
        }
    }

    private class RectIterator implements Iterator<Point2D> {
        private ArrayList<Point2D> point2DS;

        private RectIterator(RectHV rect) {
            point2DS = new ArrayList<>();
            for (Point2D point2D : treeset) {
                if (rect.contains(point2D)) {
                    point2DS.add(point2D);
                }
            }
        }

        public boolean hasNext() {
            return point2DS.size() != 0;
        }

        public Point2D next() {
            return point2DS.remove(0);
        }
    }


    public static void main(String[] args) {
        PointSET kd = new PointSET();
        kd.insert(new Point2D(7, 2));
        kd.insert(new Point2D(5, 4));
        kd.insert(new Point2D(2, 3));
        kd.insert(new Point2D(4, 7));
        kd.insert(new Point2D(9, 6));
        RectHV rectHV = new RectHV(1, 1, 10, 8);
        for (Point2D p : kd.range(rectHV)) {
            System.out.println(p);
        }
    }

}