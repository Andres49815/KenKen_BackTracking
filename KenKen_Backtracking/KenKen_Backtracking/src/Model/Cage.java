package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Andres Obando Alfaro
 */
public class Cage {
    // Static Values
    public static int actual = 1;
    private static Random random = new Random();
    // Individual Values
    public int id;
    public int quantity = 0;
    public int result;
    public ArrayList<int[]> coordinates;
    public boolean[][] cage;
    public String operation = "";
    
    
    public static void Reset() {
        actual = 1;
    }
    
    public Cage(int i, int j) {
        boolean[][] c;
        boolean doSomething;
        
        c = getCage();
        cage = new boolean[c.length][c[0].length];
        coordinates = new ArrayList<int[]>();
        for (int y = 0; y < c.length && y + i < KenKen_Board.size + 3; y++) {
            doSomething = false;
            if (KenKen_Board.group[i + y][j] == 0) {
                for (int x = 0; x  < c[0].length && x + j < KenKen_Board.size + 3; x++) {
                    if (c[y][x]) {
                        if (KenKen_Board.group[i + y][j + x] == 0) {
                            KenKen_Board.group[i + y][j + x] = actual;
                            KenKen_Board.cages[i + y][j + x] = this;
                            id = actual;
                            quantity++;
                            cage[y][x] = true;
                            doSomething = true;
                        }
                        else
                            break;
                    }
                }
            }
            else
                break;
            if (doSomething)
                actual++;
        }
    }
    
    public void setOperation() {
        if (operation.equals(""))
            switch (quantity) {
                case 1:
                    size_1();
                    break;
                case 2:
                    size_2();
                    break;
                case 3:
                case 4:
                    size_3();
                    break;
            }
    }
    private boolean contaninsZero() {
        for (int[] coord : coordinates)
            if (KenKen_Board.get(coord[1], coord[0]) == 0)
                return true;
        return false;
    }
    // size_1: Powers
    private void size_1() {
        operation = "^";
    }
    // size_2: Difference, Division, Module
    private void size_2() {
        if (this.contaninsZero())
            operation = "-";
        else
            if (Division() == 0)
                operation = "/";
            else
                operation = "%";
    }
    private int Division() {
        int divider, divident;
        
        divider = KenKen_Board.get(coordinates.get(0)[1], coordinates.get(0)[0]);
        divident = KenKen_Board.get(coordinates.get(1)[1], coordinates.get(1)[0]);
        return divider % divident;
    }
    // size_3: Sum, Multiplication
    private void size_3() {
        operation = this.contaninsZero() ? "+" : "*";
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
}
