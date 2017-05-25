import java.io.*;
import java.util.Scanner;

/**
 * Created by razao on 2/2/17.
 */
public class Percolation {
    private static int[][] grid;
    private static int size;
    private WeightedQuickUnionUF qf;

    public Percolation(int n) {
        size = n;
        grid = new int[n][n];
        qf = new WeightedQuickUnionUF(n*n);
    }

    public void open(int x, int y) throws IOException {
        int regx = size - x - 1;
        grid[regx][y] = 1;
        int arrayX = (size * (regx)) + y;
        if (regx >= 0 && regx < size && arrayX >=0 && arrayX < size*size && y >= 0 && y < size )
            openSurroundings(regx,arrayX,y);
        else return;
    }
    public void openSurroundings (int regx, int arrayX, int y) {
        if (regx != 0 && isOpen(regx - 1,y)) // check above
            qf.union(arrayX, arrayX - size);
        if (regx != size - 1 && isOpen(regx + 1,y)) // check bellow
            qf.union(arrayX, arrayX + size);
        if (y != 0 && isOpen(regx,y - 1)) // check to the right
            qf.union(arrayX, arrayX  -1);
        if (y != size - 1 && isOpen(regx,y + 1)) // check to the left
            qf.union(arrayX, arrayX + 1);
    }
    public boolean isOpen(int x, int y){
        if (grid[x][y] == 1)
            return true;
        else return false;
    }

    public boolean isFull(int x, int y) {
        int regx = size - x - 1;
        int arrayX = ((size *regx))+y;
        for (int i = 0; i < size; i++) {
            if (qf.connected(i,arrayX))
                return true;
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

/*
fileX = sc.nextInt();
            fileY = sc.nextInt();
            System.out.print(fileX + "edf" + fileY);
            per.open(fileX,fileY);
            System.out.println("end");



 fileX = fileRead.charAt(0) - 48;
            fileY = fileRead.charAt(2) -48;
            System.out.println(fileX + " " + fileY);
 *
 *
 * DemoYes.txt
 *
 *
 *
 *         int fileX, fileY;
        Scanner bf = new Scanner( new FileReader("DemoYes.txt"));
        String fileRead = "";
        int fileInt = Integer.parseInt(bf.nextLine());
        Percolation per = new Percolation(fileInt);
        while (bf.hasNextLine()){
            fileRead = bf.nextLine();
            fileX = fileRead.charAt(0) - 48;
            fileY = fileRead.charAt(2) - 48;
            per.open(fileX,fileY);
        }
        boolean ans = per.percolates();
        if (ans) {
            System.out.println("Yes");
        }
        else {
            System.out.println("No");
        }
 *
 *
 *
 *
 *
 *         Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
 *
 *
 *
 * boolean ans = per.percolates();
        if (ans) {
            System.out.println("y");
        }
        else {
            System.out.println("n");
        }
 *
 */