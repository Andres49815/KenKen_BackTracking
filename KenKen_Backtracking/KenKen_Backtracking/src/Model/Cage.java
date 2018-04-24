package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Andres Obando Alfaro
 */
public class Cage {
    public static int actual = 1;
    private static Random random = new Random();
    public int id;
    public boolean[][] cage;
    public int result;
    public String operation = "";
    public int quantity = 0;
    public ArrayList<int[]> coord;
    
    public Cage() {
        actual = 1;
    }
    
    public Cage(int i, int j) {
        boolean[][] c;
        boolean doSomething = false;
        coord = new ArrayList<int[]>();
        c = getCage();
        cage = new boolean[c.length][c[0].length];
        
        for (int y = 0; y < c.length && y + i < KenKen_Board.size + 3; y++) {
            doSomething = false;
            if (KenKen_Board.group[i + y][j] == 0) {
                for (int x = 0; x  < c[0].length && x + j < KenKen_Board.size + 3; x++) {
                    if (c[y][x]) {
                        if (KenKen_Board.group[i + y][j + x] == 0) {
                            KenKen_Board.group[i + y][j + x] = actual;
                            KenKen_Board.cages[i + y][j + x] = this;
                            id = actual;
                            cage[y][x] = true;
                            quantity++;
                            doSomething = true;
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            else {
                break;
            }
            if (doSomething)
                actual++;
        }
    }
    
    /* Decide wich operation is convenient for each group */
    public void setOperation() {
        int r;
        
        if (operation.equals(""))
            switch (quantity) {
                case 1:
                    exponent();
                    break;
                case 2:
                    len2();
                    break;
                case 3:
                case 4:
                    len3();
                    break;
            }
    }
    private boolean hasZero() {
        for (int[] coordinates : coord)
            if (KenKen_Board.get(coordinates[1], coordinates[0]) == 0)
                return true;
        return false;
    }
    private int Division() {
        int n1, n2;
        
        n1 = KenKen_Board.get(coord.get(0)[1], coord.get(0)[0]);
        n2 = KenKen_Board.get(coord.get(1)[1], coord.get(1)[0]);
        return n1 % n2;
    }
    // len 1
    private void exponent() {
        operation = "^";
    }
    // len 2
    private void len2() {
        if (this.hasZero())
            difference();
        else
            if (Division() != 0)
                module();
            else
                division();
    }
    private void difference() {
        operation = "-";
    }
    private void division() {
        operation = "/";
    }
    private void module() {
        operation = "%";
    }
    // len 3
    private void len3() {
        if (this.hasZero())
            sum();
        else
            multiplication();
    }
    private void sum() {
        operation = "+";
    }
    private void multiplication() {
        operation = "*";
    }
    
    // Obtein the cage
    public static boolean[][] getCage() {
        switch(random.nextInt(11)) {
            case 0:
                return Line();
            case 1:
                return UpsideLine();
            case 2:
                return T();
            case 3:
                return InvertedT();
            case 4:
                return SideT();
            case 5:
                return InvertedSideT();
            case 6:
                return L();
            case 7:
                return InvertedL();
            case 8:
                return UpsideL();
            case 9:
                return LineDot();
            case 10:
                return Square();
            default:
                return Dot();
        }
    }
    /**
     * XX // // //
     * XX // // //
     * XX // // //
     * XX // // //
     */
    private static boolean[][] Line() {
        boolean[][] cage = {{true},
            {true},
            {true},
            {true}};
        return cage;
    }
    
    /**
     * XX XX XX XX
     */
    private static boolean[][] UpsideLine() {
        boolean[][] cage = {{true, true, true, true}};
        return cage;
    }
    /**
     * XX XX XX
     * // XX //
     */
    private static boolean[][] T() {
        boolean[][] cage = {{true, true, true},
            {false, true, false}};
        return cage;
    }
    /**
     * // XX //
     * XX XX XX
     */
    private static boolean[][] InvertedT() {
        boolean[][] cage = {{false, true, false},
            {true, true, true}};
        return cage;
    }
    /**
     * XX //
     * XX XX
     * XX //
     */
    private static boolean[][] SideT() {
        boolean[][] cage = {{true, false},
            {true, true},
            {true, false}};
        return cage;
    }
    /**
     * // XX
     * XX XX
     * // XX
     */
    private static boolean[][] InvertedSideT() {
        boolean[][] cage = {{false, true},
            {true, true},
            {false, true}};
        return cage;
    }
    /**
     * XX //
     * XX //
     * XX XX
     */
    private static boolean[][] L() {
        boolean[][] cage = {{true, false},
            {true, false},
            {true, true}};
        return cage;
    }
    /**
     * // XX
     * // XX
     * XX XX
     */
    private static boolean[][] InvertedL() {
        boolean[][] cage = {{false, true},
            {false, true},
            {true, true}};
        return cage;
    }
    /**
     * XX // //
     * XX XX XX
     */
    private static boolean[][] UpsideL() {
        boolean[][] cage = {{true, false, false},
            {true, true, true}};
        return cage;
    }
    /**
     * XX // // //
     * XX // // //
     */
    private static boolean[][] LineDot() {
        boolean[][] cage = {{true},
            {true}};
        return cage;
    }
    /**
     * XX
     */
    private static boolean[][] Dot() {
        boolean[][] cage = {{true}};
        
        return cage;
    }
    /**
     * XX XX
     * XX XX
     */
    private static boolean[][] Square() {
        boolean[][] cage = {{true, true},
            {true, true}};
        return cage;
    }
    
    public void print() {
        for (int[] ar : coord)
            System.out.println("x: " + ar[0] + ", y: " + ar[1]);
    }
}
