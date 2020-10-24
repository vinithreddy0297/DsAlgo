/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private int n;
    private int[][] tiles;
    private int[][] solvedtile;
    private ArrayList<Index> position = new ArrayList<>();

    public Board(int[][] tils) {
        n = tils.length;
        tiles = tils.clone();
        solvedtile = new int[n][n];
        position.add(new Index(n-1, n-1));
        int init = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                solvedtile[i][j] = init++;
                position.add(new Index(i, j));
            }
        }
        solvedtile[n - 1][n - 1] = 0;
    }

    public String toString() {
        // string representation of this board
        String ret_string = String.valueOf(n);
        for (int i = 0; i < n; i++) {
            ret_string = ret_string + "\n";
            for (int j = 0; j < n; j++) {
                ret_string = ret_string + " " + String.valueOf(tiles[i][j]);
            }
        }
        return ret_string;
    }

    public int dimension() {
        // board dimension n
        return n;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    if (solvedtile[i][j] != tiles[i][j]) {
                        count += 1;
                    }
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int manhattanDistance = 0;
        if (hamming() != 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (solvedtile[i][j] != tiles[i][j] && tiles[i][j] != 0) {
                        manhattanDistance += Math.abs(position.get(tiles[i][j]).x - i) +
                                Math.abs(position.get(tiles[i][j]).y - j);
                    }
                }
            }
            return manhattanDistance;
        }
        else {
            return 0;
        }
    }

    private class Index {
        private int x;
        private int y;

        public Index(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public boolean isGoal() {
        if (manhattan() == 0) {
            return true;
        }
        else
            return false;
    }

    public boolean equals(Object y) {
        if (y == null)
        {
            return false;
        }
        if (this.toString().equals(y.toString())) {
            return true;
        }
        return false;
    }

    public Iterable<Board> neighbors()
    {
        return new Neighbours();
    }

    private class Neighbours implements Iterable<Board>
    {
        public Iterator<Board> iterator() {
            return new NeighboursIter();
        }
    }

    private class NeighboursIter implements Iterator<Board>
    {
        private ArrayList<Board> iterArrayList = new ArrayList<>();

        public NeighboursIter() {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (tiles[i][j] == 0) {
                        if (i != 0) {
                            int [][] temptiles = copy(tiles);
                            Board brd = new Board(temptiles);
                            int val = brd.tiles[i][j];
                            brd.tiles[i][j] = brd.tiles[i-1][j];
                            brd.tiles[i-1][j] = val;
                            iterArrayList.add(brd);
                        }
                        if (i != n - 1) {
                            int [][] temptiles = copy(tiles);
                            Board brd = new Board(temptiles);
                            int val = brd.tiles[i][j];
                            brd.tiles[i][j] = brd.tiles[i+1][j];
                            brd.tiles[i+1][j] = val;
                            iterArrayList.add(brd);
                        }
                        if (j != 0) {
                            int [][] temptiles = copy(tiles);
                            Board brd = new Board(temptiles);
                            int val = brd.tiles[i][j];
                            brd.tiles[i][j] = brd.tiles[i][j-1];
                            brd.tiles[i][j-1] = val;
                            iterArrayList.add(brd);
                        }
                        if (j != n - 1) {
                            int [][] temptiles = copy(tiles);
                            Board brd = new Board(temptiles);
                            int val = brd.tiles[i][j];
                            brd.tiles[i][j] = brd.tiles[i][j+1];
                            brd.tiles[i][j+1] = val;
                            iterArrayList.add(brd);
                        }
                        return;
                    }
                }
            }
        }

        public boolean hasNext() {
            return !iterArrayList.isEmpty();
        }

        public Board next() {
            Board item = iterArrayList.remove(0);
            return item;
        }
    }

    private int[][] copy(int[][] blocks)
    {
        int[][] copy = new int[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                copy[row][col] = blocks[row][col];

        return copy;
    }

    private Board exch(Board board, int fromx, int fromy, int tox, int toy) {
        int val = board.tiles[fromx][fromy];
        board.tiles[fromx][fromy] = board.tiles[tox][toy];
        board.tiles[tox][toy] = val;
        return board;
    }

    public Board twin() {
        int[][] copy = copy(tiles);
        Board brd = new Board(copy);
        int i = 0, j = 0, k = n-1, l = n - 1;
        if (brd.tiles[i][j] == 0) {
            brd = brd.exch(brd, i + 1, j, k, l);
        }
        else if (brd.tiles[k][l] == 0) {
            brd = brd.exch(brd, i, j, k - 1, l);
        }
        else
            brd = brd.exch(brd, i, j, k, l);

        return brd;
    }

    public static void main(String[] args) {

    }

}
