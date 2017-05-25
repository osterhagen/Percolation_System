import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by osterhagen on 2/4/17.
 */
public class PercolationVisualizer {
    private static int[] grid;
    private static int size;
    private WeightedQuickUnionUF qf;
    private static PrintWriter file;


    public PercolationVisualizer(int n) throws IOException {
        size = n;
        grid = new int[n*n];
        qf = new WeightedQuickUnionUF(n*n + 3);
        file = new PrintWriter("visualMatrix.txt");

    }
    public void open(int x, int y) throws IOException {
        int arrayX = (size * (size - x - 1)) + y;
        if (arrayX >= 0 && arrayX < size*size) {
            grid[arrayX] = 1;
            openSurroundings(arrayX, y);
        }
        else return;
        graph(x, y);
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

    public void graph(int x, int y) throws IOException {
        x = (size * (size - x - 1)) + y;
        if (isFull(x, y)) {
            for (int w = 0; w < size * size; w++) {
                if ((qf.connected(w%size, w) && percolates()) && grid[w] == 1)
                    grid[w] = 2;
            }
        }
            for (int i = 0; i < size * size; i++) {
                file.print(grid[i] + " ");
                System.out.print(grid[i] + " ");
                if (i % size == size - 1) {
                    file.println();
                    System.out.println();
                }
            }
            file.println();
            System.out.println();
        }


    public static void main(String[] args) throws IOException {
        PercolationVisualizer p = new PercolationVisualizer(5);
        int randoX;
        int randoY;
        int i = 0;
        while (!(p.percolates())){
            System.out.println(i +'\n');
            randoX = (int) (Math.random() * 5);
            randoY = (int) (Math.random() * 5);
            System.out.println(randoX + " " + randoY);
            p.open(randoX,randoY);
            i++;
        }

    }
}

/*
int fileX, fileY;
        String str = StdIn.readLine();
        Scanner bf = new Scanner(new FileReader(str));
        int fileInt = Integer.parseInt(bf.nextLine());
        PercolationVisualizer per = new PercolationVisualizer(fileInt);
        System.out.println(fileInt + " " + '\n');
        file.println(fileInt + " " + '\n');
        while (bf.hasNextLine() && bf.hasNextInt()) {
            fileX = bf.nextInt();
            fileY = bf.nextInt();
            per.open(fileX, fileY);
        }

PercolationVisualizer p = new PercolationVisualizer(3);
        int randoX;
        int randoY;
        int i = 0;
        while (!(p.percolates())){
            System.out.println(i +'\n');
            randoX = (int) (Math.random() * 3);
            randoY = (int) (Math.random() * 3);
            System.out.println(randoX + " " + randoY);
            p.open(randoX,randoY);
            i++;
        }



int fileX, fileY;  String str = StdIn.readLine(); Scanner bf = new Scanner( new FileReader(str)); int fileInt = Integer.parseInt(bf.nextLine());  PercolationVisualizer per = new PercolationVisualizer(fileInt); System.out.println(fileInt + " " + '\n'); file.println(fileInt + " " + '\n'); while (bf.hasNextLine() && bf.hasNextInt()){     fileX = bf.nextInt();     fileY = bf.nextInt();     per.open(fileX,fileY); } file.close();

 */