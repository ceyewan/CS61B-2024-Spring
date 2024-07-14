import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int[][] grid;
    int[][] d = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private final int size;
    private int openSize;
    private final WeightedQuickUnionUF unionSet;

    public Percolation(int N) {
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = -1;
            }
        }
        unionSet = new WeightedQuickUnionUF(N * N);
        size = N;
        openSize = 0;
    }

    public void open(int row, int col) {
        if (grid[row][col] == -1) {
            grid[row][col] = 0;
            boolean full = (row == 0);
            for (int i = 0; i < 4; i++) {
                int r = row + d[i][0], c = col + d[i][1];
                if (0 <= r && r < size && 0 <= c && c < size && isOpen(r, c)) {
                    int tmp = unionSet.find(r * size + c);
                    full = full || (grid[tmp / size][tmp % size] == 1);
                    unionSet.union(r * size + c, row * size + col);
                }
            }
            int tmp = unionSet.find(row * size + col);
            grid[tmp / size][tmp % size] = (full ? 1 : 0);
            openSize++;
        }
    }

    public boolean isOpen(int row, int col) {
        return grid[row][col] != -1;
    }

    public boolean isFull(int row, int col) {
        int tmp = unionSet.find(row * size + col);
        return grid[tmp / size][tmp % size] == 1;
    }

    public int numberOfOpenSites() {
        return openSize;
    }

    public boolean percolates() {
        for (int i = 0; i < size; i++) {
            if (isFull(size - 1, i)) {
                return true;
            }
        }
        return false;
    }
}
