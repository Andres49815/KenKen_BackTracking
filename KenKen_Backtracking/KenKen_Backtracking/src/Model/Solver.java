package Model;

import Controller.C_MainMenu;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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
        SolveStatics();
        SolvePowers();
        SolveModuls();
    }
    private static ArrayList<Integer> possibilities(int i, int j) {
        ArrayList<Integer> results, roots;
        
        int number = KenKen_Board.getResult(i, j);
        roots = Power(number);
        results = new ArrayList<>();
        for (int possible : roots)
            if (KenKen_Board.isPossible(i, j, possible))
                results.add(possible);
        
        return results;
    }
    private static ArrayList<Integer> Power(int number) {
        int result;
        ArrayList<Integer> possibilities;
        
        result = (int)(Math.log(number) / Math.log(2));
        possibilities = new ArrayList<>();
        possibilities.add(result);
        return possibilities;
    }
    
    
    private static void SolveStatics() {
        for (int i = 0; i < KenKen_Board.getSize(); i++)
            for (int j = 0; j < KenKen_Board.getSize(); j++)
                if (KenKen_Board.getOperation(i, j).equals(" "))
                    KenKen_Board.set(i, j, KenKen_Board.getResult(i, j));
    }
    private static void SolvePowers() {
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
            SolvePowers();
        }
    }
    private static void SolveModuls() {
        
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
                if (KenKen_Board.getOperation(a, b).equals("%") && KenKen_Board.get(a, b) == 100) {
                    i = a;
                    j = b;
                    break;
                }
            }
        }
        
        ArrayList<ArrayList<Integer>> possibilities = modulsPossibilities(KenKen_Board.getSize(), i, j);
        System.out.println("Posibilidades"+"i"+i+"j"+j+possibilities.size());
        for (ArrayList<Integer> possibility : possibilities) 
        {
            ArrayList<Integer> place = searchNear(i,j);
            int x = place.get(0);
            int y = place.get(1);
            KenKen_Board.set(i, j, possibility.get(0));
            KenKen_Board.set(x, y, possibility.get(1));
            SolveModuls();
        }
        
    }
    
    private static ArrayList<ArrayList<Integer>> modulsPossibilities(int size, int i,int j) {
        
        int number = KenKen_Board.getResult(i, j);
        
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int x=1; x<=size; x++)
        {
            for (int y=1; y<=size; y++)
            {
                if (x%y==number && x!=y)
                {
                    ArrayList<Integer> possibility = new ArrayList<>();
                    possibility.add(x);
                    possibility.add(y);
                    solutions.add(possibility);
                    possibility = new ArrayList<>();
                    possibility.add(y);
                    possibility.add(x);
                    solutions.add(possibility);
                }    
            }
        }
        return solutions;  
    }
    
    private static ArrayList<Integer> searchNear(int i, int j) {
        int[][] group = KenKen_Board.getGroup();
        int groupID = group[i][j];
        ArrayList<Integer> place = new ArrayList<>();  
        for (int a = 0; a < KenKen_Board.getSize(); a++) {
            for (int b = 0; b < KenKen_Board.getSize(); b++) {
                if (group[a][b]==groupID)
                {
                    System.out.println("a: "+a+ " b: "+b);
                    if( a!=i || b!=j)
                    {
                        place.add(a);
                        place.add(b);
                    }
                }
            }
        }
        return place;
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
