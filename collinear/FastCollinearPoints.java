// /* *****************************************************************************
//  *  Name:
//  *  Date:
//  *  Description:
//  **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] points_cpy = Arrays.copyOf(points, points.length);
        Merge.sort(points_cpy);
        for (int i = 0; i < points_cpy.length; i++) {
            if (i != points_cpy.length - 1) {
                if (points_cpy[i].compareTo(points_cpy[i + 1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        for (int i = 0; i < points.length; i++) {
            Point origin = points[i];
            Insertion.sort(points_cpy, origin.slopeOrder());
            for (int j = 0; j < points_cpy.length; j++) {
                if (j <= points_cpy.length - 3 && points_cpy[j] != origin) {
                    int init_j = j;
                    if (origin.slopeTo(points_cpy[j]) == origin.slopeTo(points_cpy[j + 1])) {
                        j = j + 1;
                        if (origin.slopeTo(points_cpy[j]) == origin.slopeTo(points_cpy[j + 1])) {
                            j = j + 1;
                            while (j < points_cpy.length - 1 && origin.slopeTo(points_cpy[j]) == origin
                                    .slopeTo(points_cpy[j + 1])) {
                                j = j + 1;
                                continue;
                            }
                            Point from = origin;
                            Point to = origin;
                            for (int k = init_j; k <= j; k++) {
                                if (from.compareTo(points_cpy[k]) < 0) {
                                    from = points_cpy[k];
                                }
                                if (to.compareTo(points_cpy[k]) > 0) {
                                    to = points_cpy[k];
                                }
                            }
                            LineSegment newLineSegment = new LineSegment(from, to);
                            if (lineSegments.size() == 0) {
                                lineSegments.add(newLineSegment);
                                numberOfSegments += 1;
                            }
                            else {
                                for (int m = 0; m < lineSegments.size(); m++) {
                                    if (lineSegments.get(m).toString()
                                                    .equals(newLineSegment.toString())) {
                                        break;
                                    }
                                    if (m == lineSegments.size() - 1) {
                                        lineSegments.add(newLineSegment);
                                        numberOfSegments += 1;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        if (lineSegments.size() > 0) {
            LineSegment[] lineSegmentlist = new LineSegment[lineSegments.size()];
            lineSegmentlist = lineSegments.toArray(lineSegmentlist);
            return lineSegmentlist;
        }
        else {
            return new LineSegment[0];
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
