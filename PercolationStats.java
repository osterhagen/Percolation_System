import java.io.IOException;

/**
 * Created by osterhagen on 2/4/17.
 */
public class PercolationStats {
    private static int[] grid;
    private static int size = 0;
    private WeightedQuickUnionUF wf;
    private QuickUnionUF qf;
    private static double count;
    private static int runner;
    private static String type;
    private static double time;
    private static double[] countArr;
    private static double[] timeArr;


    public PercolationStats() {
        grid = new int[size*size];
        wf = new WeightedQuickUnionUF(size* size);
        qf = new QuickUnionUF(size*size);
        countArr = new double[runner];
        timeArr = new double[runner];
    }
    public void randomPutter() throws IOException {
        int randoX, randoY;
        for (int i = 0; i < runner; i++) {
            count=0;
            PercolationStats p = new PercolationStats();
            double start = System.currentTimeMillis() ;
            start /= 1000.0;
            while ((count < ((size * size) * 0.58)) || !(p.percolates())) {
                randoX = (int) (Math.random() * size);
                randoY = (int) (Math.random() * size);
                //System.out.println("end");
                if (grid[(size * (size - randoX - 1)) + randoY] == 1)
                    continue;
                p.open(randoX, randoY);
                count++;

            }
            double stop = System.currentTimeMillis();
            stop /= 1000.0;
            time = stop - start;
            countArr[i] = count;
            timeArr[i] = time;
        }

    }


    public void open(int x, int y) throws IOException {

        int arrayX = (size * (size - x - 1)) + y;
        arrayX = arrayX +0;
        if (type.equals("fast")) {
            grid[arrayX] = 1;
            if (arrayX  > size && isOpen(arrayX-size,y)) // check above
                wf.union(arrayX, arrayX - size);
            if (arrayX < (size-1)*size && isOpen(arrayX+size,y)) // check bellow
                wf.union(arrayX, arrayX + size);
            if (arrayX > 0 && isOpen(arrayX - 1,y)) // check to the right
                wf.union(arrayX, arrayX  -1);
            if (arrayX < size - 1 && isOpen(arrayX + 1,y)) // check to the left
                wf.union(arrayX, arrayX + 1);
        }
        if (type.equals("slow")) {
            grid[arrayX] = 1;
            if (arrayX  > size && isOpen(arrayX-size,y)) // check above
                qf.union(arrayX, arrayX - size);
            if (arrayX < (size-1)*size && isOpen(arrayX+size,y)) // check bellow
                qf.union(arrayX, arrayX + size);
            if (arrayX > 0 && isOpen(arrayX - 1,y)) // check to the right
                qf.union(arrayX, arrayX  -1);
            if (arrayX < size - 1 && isOpen(arrayX + 1,y)) // check to the left
                qf.union(arrayX, arrayX + 1);
        }


        else return;
    }

    public boolean isOpen(int x, int y){
        if (grid[x] == 1)
            return true;
        else return false;
    }

    public boolean percolates(){
        for (int i = 0; i < size; i++) {
            if (type.equals("fast"))
            if (wf.connected(((size * (size - 1)) + i),(size * (size - (size-1) - 1)) + i))
                return true;
            if (type.equals("slow"))
            if (qf.connected(((size * (size - 0 - 1)) + i),(size * (size - (size-1) - 1)) + i))
                return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        size = Integer.parseInt(args[0]);
        PercolationStats ps = new PercolationStats();
        runner = Integer.parseInt(args[1]);
        type = args[2];
        ps.randomPutter();
        double meanCount = StdStats.mean(countArr);
        double countStd = StdStats.stddev(countArr) / 10;
        double totalTime = StdStats.sum(timeArr) * runner;
        double meanTime = StdStats.mean(timeArr);
        double timeStd = StdStats.stddev(timeArr);

        StdOut.println( "mean threshold=" + meanCount + '\n' +
                            "std dev=" + countStd + '\n' +
                            "time=" + totalTime + '\n' +
                            "mean time=" + meanTime + '\n' +
                            "stddev time=" + timeStd + '\n');

    }
}
