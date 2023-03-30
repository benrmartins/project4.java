import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    int[][] tiles;
    int n;
    int hamming;
    int manhattan;
    int blankPos;

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        this.tiles = cloneTiles();
        this.n = tiles.length;
        this.hamming = 0;
        this.manhattan = 0;

    }

    // Returns the size of this board.
    public int size() {
        return n;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; i++) {
                if (((tiles[i][j] + 1) % (n * n) != 0) && ((tiles[i][j] + 1) % (n * n) != tiles[i][j])) {
                    hamming++;
                }
            }
        }
        return hamming;

    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; i++) {
                if (tiles[i][j] != 0) {
                    manhattan += Math.abs(tiles[i][j] - 1 / (n - i)) + Math.abs(tiles[i][j] - 1 / (n - j));
                } else {
                    blankPos = n * i + j + 1;
                }
            }
        }
        return manhattan;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
            return manhattan == 0;

    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        int h = 0;
        int[] rowMajorOrder = new int[n * n -1];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(tiles[i][j] != 0) {
                    rowMajorOrder[h++] = tiles[i][j];
                }
            }
        }
        long num = 0;
        if (n % 2 != 0) {
            num = Inversions.count(rowMajorOrder);
            return num % 2 == 0;

        }
        return num % 2 != 0;



    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        LinkedQueue<Board> q = new LinkedQueue<Board>();
        int i = (blankPos - 1) / n;
        int j = (blankPos - 1) % n;

        if(i + 1 < n) {
            int[][] clone = cloneTiles();
            int temp = clone[i+1][j];
            clone[i + 1][j] = clone[i][j];
            clone[i][j] = temp;
            q.enqueue(new Board(clone));

        }

        if(i - 1 >= 0) {
            int[][] clone = cloneTiles();
            int temp = clone[i - 1][j];
            clone[i - 1][j] = clone[i][j];
            clone[i][j] = temp;
            q.enqueue(new Board(clone));
        }

        if(j + 1 < n) {
            int[][] clone = cloneTiles();
            int temp = clone[i][j + 1];
            clone[i][j + 1] = clone[i][j];
            clone[i][j] = temp;
            q.enqueue(new Board(clone));
        }

        if(j - 1 >= 0) {
            int[][] clone = cloneTiles();
            int temp = clone[i][j - 1];
            clone[i][j - 1] = clone[i][j];
            clone[i][j] = temp;
            q.enqueue(new Board(clone));
        }
        return q;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != ((Board) other).tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
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
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}
