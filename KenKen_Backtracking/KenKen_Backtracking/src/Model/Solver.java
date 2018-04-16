package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Andres Obando Alfaro
 */
public class Solver {
    
    private static ArrayList<ArrayList<Integer>> solution;
    private static ArrayList<ArrayList<Integer>> transverseSolution;
    private static int powSolved = 0;
    private static int moduleSolver = 0;
    
    public Solver() {
        
    }
    public static void Solve() {
        int i, j;
        
        if (KenKen_Board.isComplete()) {
            KenKen_Board.print();
            solution = (ArrayList<ArrayList<Integer>>)KenKen_Board.getBoard().clone();
            return;
        }
        i = j = 0;
        for (int a = 0; a < KenKen_Board.getSize(); a++) {
            for (int b = 0; b < KenKen_Board.getSize(); b++) {
                // Si es potencia y esta vacio
                if (KenKen_Board.getOperation(a, b).equals("^") && KenKen_Board.get(a, b) == 100) {
                    i = a;
                    j = b;
                    break;
                }
            }
        }
        
        ArrayList<Integer> possibilities = possibilities(i, j);
        for (int n : possibilities) {
            KenKen_Board.set(i, j, n);
            Solve();
        }
        KenKen_Board.set(i, j, 100);
    }
    private static ArrayList<Integer> possibilities(int i, int j) {
        ArrayList<Integer> results, roots;
        
        int number = KenKen_Board.getResult(i, j);
        roots = Power(number);
        results = new ArrayList<Integer>();
        for (int possible : roots)
            if (KenKen_Board.isPossible(i, j, possible))
                results.add(possible);
        
        return results;
    }
    private static ArrayList<Integer> Power(int number) {
        int result;
        ArrayList<Integer> possibilities;
        
        result = (int)Math.sqrt(number);
        possibilities = new ArrayList<Integer>();
        possibilities.add(result);
        possibilities.add(-1 * result);
        return possibilities;
    }
    private static ArrayList<Integer> Module(int number) {
        return null;
    }
    
    // Other methods
    
    // Print
    public static void print() {
        System.out.println();
        for (int i = 0; i < solution.size(); i++) {
            for (int j = 0; j < solution.size(); j++) {
                System.out.print(solution.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }
}
