package Model;

import java.util.ArrayList;

/**
 *
 * @author Andres Obando Alfaro
 */
public class Solver {
    
    private static KenKen_Board KenKen;
    private static ArrayList<ArrayList<Integer>> solution;
    private static ArrayList<ArrayList<Integer>> transverseSolution;
    
    public Solver(KenKen_Board KK) {
        KenKen = KK;
        InitializeArrays();
    }
    private void InitializeArrays() {
        solution = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < KenKen.getSize(); i++) {
            solution.add(new ArrayList<Integer>());
            for (int j = 0; j < KenKen.getSize(); j++) {
                solution.get(i).add(100);
            }
        }
    }
    public static void Solve() {
        for (int i = 0; i < KenKen.getSize(); i++) {
            for (int j = 0; j < KenKen.getSize(); j++) {
                if (KenKen.getGroup(i, j).size() == 1) {
                    solution.get(i).add(j, Power(KenKen.getResult(i, j)));
                }
            }
        }
        print();
    }
    
    
    private static int Power(int number) {
        int result;
        ArrayList<Integer> possibilities;
        
        result = (int)Math.sqrt(number);
        possibilities = new ArrayList<Integer>();
        possibilities.add(result);
        possibilities.add(-1 * result);
        return result;
    }
    private static ArrayList<Integer> Module(int number) {
        return null;
    }
    
    private static void print() {
        System.out.println();
        for (int i = 0; i < solution.size(); i++) {
            for (int j = 0; j < solution.size(); j++) {
                System.out.print(solution.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }
}
