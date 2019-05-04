import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] openSites;
    private double mean;
    private double stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials can not less or equals to 0");
        }
        openSites = new double[trials];
        mean = 0;
        stddev = 0;
        int count = 0;
        while (count < trials) {

            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int[] rowAndCol = transfer(StdRandom.uniform(0, n * n), n);
                per.open(rowAndCol[0], rowAndCol[1]);
            }

            openSites[count] = (double) per.numberOfOpenSites() / (n * n);
            count++;
        }

    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]),
                                                  Integer.parseInt(args[1]));
        System.out.println("mean = " + p.mean());
        System.out.println("stddev = " + p.stddev());
        System.out.println("95% interval = " + p.confidenceLo() + "," + p.confidenceHi());
    }

    private int[] transfer(int index, int n) {
        int[] ret = new int[2];
        int row = (index / n) + 1;
        int col = (index % n) + 1;
        ret[0] = row;
        ret[1] = col;
        return ret;

    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean != 0) {
            return mean;
        }
        mean = StdStats.mean(openSites);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev != 0) {
            return stddev;
        }
        stddev = StdStats.stddev(openSites);
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {

        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(openSites.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(openSites.length);
    }
}
