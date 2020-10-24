/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private int numberOfSegments = 0;
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if(points[i] == null)
            {
                throw new IllegalArgumentException();
            }
        }
        Point[] pointCopy = Arrays.copyOf(points, points.length);
        Merge.sort(pointCopy);
        for (int k = 0; k < pointCopy.length; k++) {
            if (k != pointCopy.length - 1) {
                if (pointCopy[k].compareTo(pointCopy[k + 1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (points[j] == points[i]) {
                    continue;
                }
                for (int l = 0; l < points.length; l++) {
                    if (points[l] == points[i] || points[l] == points[j]) {
                        continue;
                    }
                    if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                        for (int m = 0; m < points.length; m++) {
                            if (points[m] == points[i] || points[m] == points[j]
                                    || points[m] == points[l]) {
                                continue;
                            }
                            if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[m])) {
                                Point from = points[i];
                                Point to = points[i];
                                if (from.compareTo(points[j]) < 0) {
                                    from = points[j];
                                }
                                else if (to.compareTo(points[j]) > 0) {
                                    to = points[j];
                                }
                                if (from.compareTo(points[l]) < 0) {
                                    from = points[l];
                                }
                                else if (to.compareTo(points[l]) > 0) {
                                    to = points[l];
                                }
                                if (from.compareTo(points[m]) < 0) {
                                    from = points[m];
                                }
                                else if (to.compareTo(points[m]) > 0) {
                                    to = points[m];
                                }

                                LineSegment newLineSegment = new LineSegment(from, to);
                                if (lineSegments.size() == 0) {
                                    lineSegments.add(newLineSegment);
                                    numberOfSegments += 1;
                                }
                                else {
                                    for (int k = 0; k < lineSegments.size(); k++) {
                                        if (lineSegments.get(k).toString()
                                                        .equals(newLineSegment.toString())) {
                                            break;
                                        }
                                        if (k == lineSegments.size() - 1) {
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
