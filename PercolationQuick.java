import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by razao on 2/3/17.
 */
public class PercolationQuick {
    /**
     * Created by razao on 2/2/17.
     */

    private static int[] grid;
    private static int size;
    private QuickUnionUF qf;

    public PercolationQuick(int n) {
        size = n;
        grid = new int[n*n];
        qf = new QuickUnionUF(n * n);
    }

    public void open(int x, int y) throws IOException {
        int arrayX = (size * (size - x - 1)) + y;
        if (arrayX >= 0 && arrayX < size*size) {
            grid[arrayX] = 1;
            openSurroundings(arrayX, y);
        }
        else return;
    }

    public void openSurroundings (int arrayX, int y) {

        if (arrayX  > size && isOpen(arrayX-size,y)) // check above
            qf.union(arrayX, arrayX - size);
        if (arrayX < (size-1)*size && isOpen(arrayX+size,y)) // check bellow
            qf.union(arrayX, arrayX + size);
        if (arrayX > 0 && isOpen(arrayX - 1,y)) // check to the right
            qf.union(arrayX, arrayX  -1);
        if (arrayX < size - 1 && isOpen(arrayX + 1,y)) // check to the left
            qf.union(arrayX, arrayX + 1);
    }

    public boolean isOpen(int x, int y){
        if (grid[x] == 1)
            return true;
        else return false;
    }

    public boolean isFull(int x, int y) {
        for (int i = 0; i < size; i++) {
            if (qf.connected(i,x)) {
                return true;
            }
        }
        return false;
    }

    public boolean percolates(){
        int bottom = (size-1) * size;
        for (int i = 0; i < size; i++) {
            for (int j = bottom; j < size*size; j++) {
                if (qf.connected(i,j))
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        int fileX, fileY;
        String str = StdIn.readLine();
        Scanner bf = new Scanner( new FileReader(str));

        int fileInt = Integer.parseInt(bf.nextLine());
        Percolation per = new Percolation(fileInt);
        while (bf.hasNextLine() && bf.hasNextInt()){
            fileX = bf.nextInt();
            fileY = bf.nextInt();
            per.open(fileX,fileY);
        }
        boolean ans = per.percolates();
        if (ans) {
            System.out.println("Yes");
        }
        else {
            System.out.println("No");
        }
    }


}
