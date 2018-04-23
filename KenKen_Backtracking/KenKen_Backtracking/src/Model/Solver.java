package Model;

import static Model.KenKen_Board.cages;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Andres Obando Alfaro
 */
public class Solver {
    
    private static ArrayList<ArrayList<Integer>> solution;
    private static HashMap<Integer, ArrayList<ArrayList<Integer>>> possibilitiesMap = new HashMap<>();
    
    public static void DoPossibilities() {
        for (int i = 0; i < KenKen_Board.groupsArray.size();i++)
        {
            int groupID = KenKen_Board.groupsArray.get(i);
            Cage cage = KenKen_Board.getCage(groupID);
            switch(cage.operation)
            {
                case " ":
                    ArrayList<ArrayList<Integer>> statics = new  ArrayList<>();
                    ArrayList<Integer> staticNumber = new ArrayList<>();
                    staticNumber.add(cage.result);
                    statics.add(staticNumber);
                    possibilitiesMap.put(groupID,statics);
                    break;
                case "^":
                    ArrayList<ArrayList<Integer>> potencias = new  ArrayList<>();
                    potencias.add(possibilities(cage.result));
                    possibilitiesMap.put(groupID,potencias);
                    break;
                case "%":
                    possibilitiesMap.put(groupID, modulsPossibilities(cage.result));
                    break;
                case "*":
                    possibilitiesMap.put(groupID, multiplicationPossibilities(cage.result));
                    break;
                default:
                    possibilitiesMap.put(groupID, new ArrayList<>());
                    break;
                    
            }
        }
    }
    public Solver() {
        
    }
    public static void Solve() {
        DoPossibilities();
        SolveStatics();
        SolvePowers();
        SolveOperations();
    }
    private static ArrayList<Integer> possibilities(int number) {
        ArrayList<Integer> results, roots;
        
        roots = Power(number);
        results = new ArrayList<>();
        for (int possible : roots)
            results.add(possible);
        return results;
    }
    private static ArrayList<Integer> Power(int number) {
        int result;
        ArrayList<Integer> possibilities;
        
        result = (int)Math.round(Math.pow(Math.E, Math.log(number) / 3));
        possibilities = new ArrayList<>();
        possibilities.add(result);
        return possibilities;
    }
    
    
    private static void SolveStatics() {
        for (int i = 0; i < KenKen_Board.getSize(); i++)
            for (int j = 0; j < KenKen_Board.getSize(); j++)
                if (KenKen_Board.cages[i][j].operation.equals(" "))
                    KenKen_Board.set(new Place(i, j), cages[i][j].result);
    }
    private static void SolvePowers() {
        int i, j;
        
        if (KenKen_Board.isCompletePowers()) {
            KenKen_Board.print();
            solution = (ArrayList<ArrayList<Integer>>)KenKen_Board.getBoard().clone();
            return;
        }
        i = j = 0;
        for (int a = 0; a < KenKen_Board.getSize(); a++) {
            for (int b = 0; b < KenKen_Board.getSize(); b++) {
                // Si es potencia y esta vacio
                if (KenKen_Board.cages[i][j].operation.equals("^") && KenKen_Board.get(a, b) == 100) {
                    i = a;
                    j = b;
                    break;
                }
            }
        }
        int number = KenKen_Board.cages[i][j].result;
        ArrayList<Integer> possibilities = possibilities(number);
        for (int n : possibilities) {    
            KenKen_Board.set(new Place(i, j), n);
            SolvePowers();
        }
    }
    private static void SolveOperations() {
        
        if (KenKen_Board.isComplete()) {
            KenKen_Board.print();
            solution = (ArrayList<ArrayList<Integer>>)KenKen_Board.getBoard().clone();
            return;
        }
        for (int i = 0; i < KenKen_Board.groupsArray.size();i++)
        {
            int groupID = KenKen_Board.groupsArray.get(i);
            ArrayList<Place> people = KenKen_Board.getPeople(groupID);
            ArrayList<ArrayList<Integer>> possibilities = possibilitiesMap.get(groupID);
            int sizeGroup = KenKen_Board.cantOfGroup(groupID);
            if(!KenKen_Board.groupIsComplete(groupID))
            {
                if(!possibilities.isEmpty())
                {
                    for (ArrayList<Integer> possibility : possibilities) 
                    {
                        if (sizeGroup == 2 && people.size() == 2)
                        {
                            KenKen_Board.set100(groupID);
                            Place place1 = people.get(0);
                            Place place2 = people.get(1);
                            if(KenKen_Board.isPossible(place1, possibility.get(0)) && KenKen_Board.isPossible(place2, possibility.get(1)))
                            {
                                KenKen_Board.set(place1, possibility.get(0));
                                KenKen_Board.set(place2, possibility.get(1));
                                SolveOperations();
                            }
                        }
                    }
                    KenKen_Board.set100(groupID);
                }
            }
        }
    }
        
        
        
    
    private static ArrayList<ArrayList<Integer>> modulsPossibilities(int number) {
        
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int x = 1;x<KenKen_Board.size;x++)
        {
            for (int y = 1;y<KenKen_Board.size;y++)
            {
                ArrayList<Integer> possibility = new ArrayList<>();
                    if (x%y==number && x!=y)
                    {
                        possibility.add(x);
                        possibility.add(y);
                        solutions.add(possibility);
                    }    
            }
        }
        return solutions;  
    }
    private static ArrayList<ArrayList<Integer>> divisionPossibilities(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int x = 0;x<KenKen_Board.size;x++)
        {
            for (int y = 1;y<KenKen_Board.size;y++)
            {
                if (x/y==number && x!=y && x%y==0)
                {
                    ArrayList<Integer> possibility = new ArrayList<>();
                    possibility.add(x);
                    possibility.add(y);
                    solutions.add(possibility);
                }    
            }
        }
        return solutions;  
    }
    
    private static ArrayList<ArrayList<Integer>> multiplicationPossibilities(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int x = 0;x<KenKen_Board.size;x++)
        {
            for (int y = 0;y<KenKen_Board.size;y++)
            {
                if (x*y==number && x!=y)
                {
                    ArrayList<Integer> possibility = new ArrayList<>();
                    possibility.add(x);
                    possibility.add(y);
                    solutions.add(possibility);
                }    
            }
        }
        return solutions;  
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
