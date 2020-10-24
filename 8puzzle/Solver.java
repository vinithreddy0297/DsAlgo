/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private int moves = 0;
    private Board initial;
    private MinPQ<BoardWrapper> minpqBoard;
    private MinPQ<BoardWrapper> minpqBoard_twin;

    public Solver(Board initial) {
        BoardWrapper previous;
        if (initial == null)
            throw new IllegalArgumentException();
        else {
            minpqBoard = new MinPQ<>(new MinPQComp());
            minpqBoard_twin = new MinPQ<>(new MinPQComp());
            minpqBoard_twin.insert(new BoardWrapper(initial.twin()));
            minpqBoard.insert(new BoardWrapper(initial));
            this.initial = initial;
            while (!minpqBoard_twin.min().board.isGoal() && !minpqBoard.min().board.isGoal()) {
                previous = minpqBoard.delMin();
                moves = previous.getMoves();
                Iterator<Board> iter = previous.board.neighbors().iterator();
                while (iter.hasNext()) {
                    BoardWrapper neighbour = new BoardWrapper(iter.next());
                    neighbour.setPrev(previous);
                    neighbour.setMoves(moves + 1);

                    if (previous.prev == null || !neighbour.board.equals(previous.prev.board)) {
                        minpqBoard.insert(neighbour);
                    }
                }

                BoardWrapper previous_twin = minpqBoard_twin.delMin();
                int moves_twin = previous_twin.getMoves();
                Iterator<Board> iter_twin = previous_twin.board.neighbors().iterator();
                while (iter_twin.hasNext()) {
                    BoardWrapper neighbour_twin = new BoardWrapper(iter_twin.next());
                    neighbour_twin.setPrev(previous_twin);
                    neighbour_twin.setMoves(moves_twin + 1);

                    if (previous_twin.prev == null || !neighbour_twin.board
                            .equals(previous_twin.prev.board)) {
                        minpqBoard_twin.insert(neighbour_twin);
                    }
                }
            }
            moves = minpqBoard.min().getMoves();
        }
    }

    public boolean isSolvable() {
        if (minpqBoard_twin.min().board.isGoal()) {
            return false;
        }
        if (minpqBoard.min().board.isGoal()) {
            return true;
        }
        return false;
    }

    public int moves() {
        if (isSolvable())
        {
            return moves;
        }
        return -1;

    }

    public Iterable<Board> solution()
    {
        if (!isSolvable())
            return null;
        else
            return new MinPQIterable();
    }

    private class MinPQIterable implements Iterable<Board>
    {
        public Iterator<Board> iterator() {
            return new MinPQIterator();
        }
    }

    private class MinPQIterator implements Iterator<Board>
    {
        private ArrayList<Board> iterlist = new ArrayList<>();

        public MinPQIterator() {
            BoardWrapper b = minpqBoard.min();
            while (b.getPrev() != null) {
                iterlist.add(b.board);
                b = b.getPrev();
            }
            iterlist.add(initial);
        }

        public boolean hasNext() {
            return iterlist.size() > 0;
        }

        public Board next() {
            Board ret = iterlist.remove(iterlist.size() - 1);
            return ret;
        }
    }

    private class MinPQComp implements Comparator<BoardWrapper> {
        public int compare(BoardWrapper o1, BoardWrapper o2) {
            if (o1.getManhatton_prior() == 0) {
                o1.setManhatton_prior(o1.board.manhattan() + o1.getMoves());
            }
            if (o2.getManhatton_prior() == 0) {
                o2.setManhatton_prior(o2.board.manhattan() + o2.getMoves());
            }
            if(o1.getManhatton_prior() < o2.getManhatton_prior())
                return -1;
            else if(o1.getManhatton_prior() > o2.getManhatton_prior())
                return 1;
            else
            {
                if (o1.getHamming_prior() == 0)
                {
                    o1.setHamming_prior(o1.board.hamming() + o1.getMoves());
                }
                if (o2.getHamming_prior() == 0)
                {
                    o2.setHamming_prior(o2.board.hamming() + o2.getMoves());
                }
                if (o1.getHamming_prior() < o2.getHamming_prior())
                    return -1;
                else if (o1.getHamming_prior() > o2.getHamming_prior())
                    return 1;
                else
                    return -1;
            }
        }
    }

    private class BoardWrapper
    {
        Board board;
        private int hamming_prior = 0;
        private int manhatton_prior = 0;
        private BoardWrapper prev = null;

        public int getMoves() {
            return moves;
        }

        public void setMoves(int moves) {
            this.moves = moves;
        }

        private int moves = 0;

        public BoardWrapper(Board b)
        {
            board = b;
        }

        public int getHamming_prior() {
            return hamming_prior;
        }

        public void setHamming_prior(int hamming_prior) {
            this.hamming_prior = hamming_prior;
        }

        public int getManhatton_prior() {
            return manhatton_prior;
        }

        public void setManhatton_prior(int manhatton_prior) {
            this.manhatton_prior = manhatton_prior;
        }

        public BoardWrapper getPrev() {
            return prev;
        }

        public void setPrev(BoardWrapper prev) {
            this.prev = prev;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
