package Model;

import java.util.Random;

/**
 *
 * @author Andres Obando Alfaro
 */
public class Cage {
    private static Random random = new Random();
    
    public Cage() {
        
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
