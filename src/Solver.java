import dsa.LinkedStack;
// import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

// import java.util.Iterator;

// A data type that implements the A* algorithm for solving the 8-puzzle and its generalizations.
public class Solver {

    // Minimum number of moves
    int moves;
    //  Sequence of boards in a shortest solution of the initial board
    LinkedStack<Board> solution;

    // Finds a solution to the initial board using the A* algorithm.
    public Solver(Board board) {

        if (board == null) {
            throw new NullPointerException("board is null");
        }

        if (!(board.isSolvable())) {
            throw new IllegalArgumentException("board is unsolvable");
        }

    }

    // Returns the minimum number of moves needed to solve the initial board.
    public int moves() {
        return moves;
    }

    // Returns a sequence of boards in a shortest solution of the initial board.
    public Iterable<Board> solution() {
        return solution;
    }

    // A data type that represents a search node in the grame tree. Each node includes a
    // reference to a board, the number of moves to the node from the initial node, and a
    // reference to the previous node.
    private class SearchNode implements Comparable<SearchNode> {

        // The board represented by this node
        Board board;
        // Number of moves it took to get to this node from the node
        int moves;
        // The previous search node, SearchNode previous.
        SearchNode previous;

        // Constructs a new search node.
        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = 0;
            this.previous = previous;
        }

        // Returns a comparison of this node and other based on the following sum:
        //   Manhattan distance of the board in the node + the # of moves to the node
        public int compareTo(SearchNode other) {
            // compares Manhattan distance of the board in the node
            // plus the number of moves to the node
            int x = this.board.manhattan() + this.moves;
            int y = other.board.manhattan() + other.moves;
            return x - y;
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.printf("Solution (%d moves):\n", solver.moves());
            StdOut.println(initial);
            StdOut.println("----------");
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("----------");
            }
        } else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
