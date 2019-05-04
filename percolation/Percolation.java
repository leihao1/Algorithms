/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int num;
    private final int top;
    private final int bot;
    private final WeightedQuickUnionUF wuf;
    private final WeightedQuickUnionUF fullFind;
    private int openSite;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n can not less or equals to 0");
        }
        this.num = n;
        grid = new boolean[n + 2][n + 2];
        top = (n + 2) * (n + 2);
        bot = (n + 2) * (n + 2) + 1;
        wuf = new WeightedQuickUnionUF((n + 2) * (n + 2) + 2);
        fullFind = new WeightedQuickUnionUF((n + 2) * (n + 2) + 2);
        for (int i = 1; i < n + 1; i++) {
            grid[0][i] = true;
            wuf.union(i, top);
            fullFind.union(i, top);
        }
        for (int i = 1; i < n + 1; i++) {
            grid[n + 1][i] = true;
            wuf.union((n + 1) * (n + 2) + i, bot);
        }
    }

    private int transfer(int row, int col) {
        int index = row * (num + 2) + col;
        return index;
    }


    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > num || col > num) {
            throw new IllegalArgumentException("out of range");
        }
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSite++;
            int index = transfer(row, col);
            if (grid[row - 1][col]) {
                int nextIndex = transfer(row - 1, col);
                wuf.union(index, nextIndex);
                fullFind.union(index, nextIndex);
            }
            if (grid[row + 1][col]) {
                int nextIndex = transfer(row + 1, col);
                wuf.union(index, nextIndex);
                fullFind.union(index, nextIndex);
            }
            if (grid[row][col - 1]) {
                int nextIndex = transfer(row, col - 1);
                wuf.union(index, nextIndex);
                fullFind.union(index, nextIndex);
            }
            if (grid[row][col + 1]) {
                int nextIndex = transfer(row, col + 1);
                wuf.union(index, nextIndex);
                fullFind.union(index, nextIndex);
            }
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > num || col > num) {
            throw new IllegalArgumentException("out of range");
        }
        return grid[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > num || col > num) {
            throw new IllegalArgumentException("out of range");
        }
        return fullFind.connected(top, transfer(row, col));
    }


    // number of open sites
    public int numberOfOpenSites() {
        return openSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return wuf.connected(top, bot);
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("main");
    }

}
