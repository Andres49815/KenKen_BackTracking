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
    public int cantSolutions = 0;
    public int cantSolutionsTested = 0;
    private static Random random = new Random();
    // Individual Values
    public int id;
    public int quantity = 0;
    public int result;
    public ArrayList<ArrayList<Integer>> coordinates;
    public boolean[][] cage;
    public String operation = "";
    public boolean isLinear = false;
    
    
    public static void Reset() {
        actual = 1;
    }
    
    public Cage(int q, String op) {
        this.quantity = q;
        this.operation = op;
    }
    public Cage(int i, int j) {
        boolean[][] c;
        boolean doSomething;
        
        c = getCage(this);
        cage = new boolean[c.length][c[0].length];
        coordinates = new ArrayList<ArrayList<Integer>>();
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
        for (ArrayList<Integer> coord : coordinates)
            if (KenKen_Board.get(coord.get(0), coord.get(1)) == 0)
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
        
        divider = KenKen_Board.get(coordinates.get(0).get(0), coordinates.get(0).get(1));
        divident = KenKen_Board.get(coordinates.get(1).get(0), coordinates.get(1).get(1));
        return divider % divident;
    }
    // size_3: Multiplication, Sum
    private void size_3() {
        operation = this.contaninsZero() ? "+" : random.nextInt() % 3 == 0 ? "+" : "*";
    }
    
    // Possibilities
    public ArrayList<ArrayList<Integer>> Possibilities(ArrayList<ArrayList<Integer>> possibilities) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (ArrayList<Integer> pos : possibilities)
            if (IsPossible(pos) && !res.contains(pos))
                res.add(pos);
        return res;
    }
    // Possible
    public boolean IsPossible(ArrayList<Integer> possibility) {
        int value;
        
        for (int i = 0; i < possibility.size(); i++) {
            value = possibility.get(i);
            if (!KenKen_Board.isPossible(coordinates.get(i).get(0),coordinates.get(i).get(1), value))
                return false;
        }
        return true;
    }
    
    // Add
    private double Operation(int ... numbers) {
        int operationResult;
        
        switch (operation) {
            case "%":
                try {
                    return numbers[0] > numbers[1] ? numbers[0] % numbers[1] : numbers[1] % numbers[0];
                }
                catch (ArithmeticException ae) {
                    return -1;
                }
            case "/":
                try {
                return numbers[0] / numbers[1];
                }
                catch (ArithmeticException ae) {
                    return -1;
                }
            case "-":
                return Math.abs(numbers[0] - numbers[1]);
            case "*":
                operationResult = 1;
                for (int n : numbers)
                    operationResult *= n;
                return operationResult;
            case "+":
                operationResult = 0;
                for (int n : numbers)
                    operationResult += n;
                return operationResult;
            default:
                System.out.println("Hola");
                return 0;
        }
    }
    private ArrayList<Integer> addAll(int ... list) {
        ArrayList<Integer> l;
        
        l = new ArrayList<Integer>();
        for (int actual : list)
            l.add(actual);
        return l;
    }
    
    // Possibilities
    public static ArrayList<ArrayList<Integer>> Permutations(ArrayList<Integer> elements) {
        return Permutations(elements, new ArrayList<Integer>(), elements.size(), 
                elements.size(), new ArrayList<ArrayList<Integer>>());
    }
    private static ArrayList<ArrayList<Integer>> Permutations(ArrayList<Integer> elements, 
           ArrayList<Integer> act, int n, int r,  ArrayList<ArrayList<Integer>> result) {
        if (n == 0) {
            result.add(act);
        }
        else {
            for (int i = 0; i < r; i++) {
                if (!act.contains(elements.get(i))) {
                    ArrayList<Integer> newAct = ( ArrayList<Integer>)act.clone();
                    newAct.add(elements.get(i));
                    Permutations(elements, newAct, n - 1, r, result);
                }
            }
        }
        return result;
    }
    
    // Obtein the cage
    public static boolean[][] getCage(Cage c) {
        switch(random.nextInt(14)) {
            case 0:
                c.isLinear = true;
                return Line();
            case 1:
                c.isLinear = true;
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
            case 11:
                return Z();
            case 12:
                return Dot();
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
    /**
     * XX XX //
     * // XX XX
     */
    private static boolean[][] Z() {
        boolean[][] cage = {{true, true, false},
            {false, true, true}};
        return cage;
    }

}
