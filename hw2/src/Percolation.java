import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final boolean[][] grid;
    int[][] d = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private final int size;
    private int openSize;
    private final WeightedQuickUnionUF unionSet;
    private final int topVirtualNode;

    public Percolation(int N) {
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        unionSet = new WeightedQuickUnionUF(N * N + 1);
        topVirtualNode = N * N;
        size = N;
        openSize = 0;
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            openSize++;
            grid[row][col] = true;
            for (int i = 0; i < 4; i++) {
                int r = row + d[i][0], c = col + d[i][1];
                if (0 <= r && r < size && 0 <= c && c < size && isOpen(r, c)) {
                    int tmp = unionSet.find(r * size + c);
                    unionSet.union(r * size + c, row * size + col);
                }
            }
            if (row == 0) {
                unionSet.union(col, topVirtualNode);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        return unionSet.connected(row * size + col, topVirtualNode);
    }

    public int numberOfOpenSites() {
        return openSize;
    }

    /* 可以再额外添加一个并查集，增加设置一个 bottomVirtualNode，专门用来判断有没有渗透。
     * 如果并查集中既有 topVirtualNode 也有 bottomVirtualNode，
     * 则一个点可能通过 bottomVirtualNode 的中间作用连接上 topVirtualNode
     * 造成 isFull 的误判。额外的并查集只用来判断是否渗透，没有这个问题 */
    public boolean percolates() {
        for (int i = 0; i < size; i++) {
            if (isFull(size - 1, i)) {
                return true;
            }
        }
        return false;
    }
}
