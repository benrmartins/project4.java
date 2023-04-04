import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {

    // length of the board
    int n;
    // tiles in the board
    int[][] tiles;
    // Hemming distance
    int hamming;
    // Manhattan distance
    int manhattan;
    // position of the blank tile
    int blankPos;

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        // initialized the variables
        this.tiles = tiles;
        this.n = tiles.length;
        this.hamming = 0;
        this.manhattan = 0;

        // uses a nested the for loop through the array
        int value = 1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                // checks if the tiles is equal to the goal board
                if (tiles[i][j] != value && tiles[i][j] != 0) {
                    // counts and adds it to hamming
                    hamming++;
                }
                value++;
                // skips the first blank item
                if (tiles[i][j] != 0) {
                    // uses manhattan equation and stores into manhattan
                    manhattan += Math.abs(i-(tiles[i][j] - 1) / n)+Math.abs(j-(tiles[i][j] -1) % n);
                } else {
                    // updates the blank positions
                    blankPos = n * i + j + 1;
                }
            }
        }


    }

    // Returns the size of this board.
    public int size() {
        return n;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        // returns the tiles
        return tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        return hamming;

    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        return manhattan;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        // if hamming and goal is at 0
        return manhattan == 0 && hamming == 0;
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        int h = 0;
        int count = 0;
        // Create an array of size n**2 − 1
        int[] rowMajorOrder = new int[(n * n) - 1];
        // uses a nested for loop through the array
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // skips the first blank item
                if (tiles[i][j] != 0 && n * i + j + 1 != blankPos) {
                    rowMajorOrder[h] = tiles[i][j];
                    h++;
                } else {
                    count = i;
                }

            }
        }
        // Inversions.count() to compute the number of inversions in the array
        if (n % 2 != 0) {
            return Inversions.count(rowMajorOrder) % 2 == 0;
        } else {
            return Inversions.count(rowMajorOrder) + count % 2 != 0;

        }




    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        // Create a queue q of Board objects.
        LinkedQueue<Board> q = new LinkedQueue<Board>();
        // iterations
        int i = (blankPos - 1) / n;
        int j = (blankPos - 1) % n;

        if (i + 1 < n) {
            // Clone the tiles of the board.
            int[][] clone = cloneTiles();
            // Exchange an appropriate tile with the blank tile in the clone.
            int temp = clone[i+1][j];
            clone[i + 1][j] = clone[i][j];
            clone[i][j] = temp;
            // Construct a Board object from the clone, and enqueue it into q
            q.enqueue(new Board(clone));

        }

        if (i - 1 >= 0) {
            // Clone the tiles of the board.
            int[][] clone = cloneTiles();
            // Exchange an appropriate tile with the blank tile in the clone.
            int temp = clone[i - 1][j];
            clone[i - 1][j] = clone[i][j];
            clone[i][j] = temp;
            // Construct a Board object from the clone, and enqueue it into q
            q.enqueue(new Board(clone));
        }

        if (j + 1 < n) {
            // Clone the tiles of the board.
            int[][] clone = cloneTiles();
            // Exchange an appropriate tile with the blank tile in the clone.
            int temp = clone[i][j + 1];
            clone[i][j + 1] = clone[i][j];
            clone[i][j] = temp;
            // Construct a Board object from the clone, and enqueue it into q
            q.enqueue(new Board(clone));
        }

        if (j - 1 >= 0) {
            // Clone the tiles of the board.
            int[][] clone = cloneTiles();
            // Exchange an appropriate tile with the blank tile in the clone.

            int temp = clone[i][j - 1];
            clone[i][j - 1] = clone[i][j];
            clone[i][j] = temp;
            // Construct a Board object from the clone, and enqueue it into q
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

        for (int i = 0; i < n; i++) {
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
