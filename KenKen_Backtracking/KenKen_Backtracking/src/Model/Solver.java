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
                case "%":
                    possibilitiesMap.put(groupID, modulsPossibilities(cage.result));
                    break;
                case "-":
                    possibilitiesMap.put(groupID, substractionPossibilities(cage.result));
                    break;
                case "/":
                    possibilitiesMap.put(groupID, divisionPossibilities(cage.result));
                    break;
                case "*":
                    if(cage.quantity==3)
                    {
                        possibilitiesMap.put(groupID, multiplicationPossibilities3(cage.result));
                        break;
                    }
                    else
                    {
                        possibilitiesMap.put(groupID, multiplicationPossibilities4(cage.result));
                        break;
                    }
                case "+":
                    if(cage.quantity==3)
                    {
                        possibilitiesMap.put(groupID, addPossibilities3(cage.result));
                        break;
                    }
                    else
                    {
                        possibilitiesMap.put(groupID, addPossibilities4(cage.result));
                        break;
                    }
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
        SolvePowers();
        SolveOperations();
    }
    private static Integer Power(int number) {
        int result;
        if(number == 0)
        {
            result = 0;
        }
        else
        {
            result = (int)Math.round(Math.pow(Math.E, Math.log(number) / 3));
        }
        return result;
    }
    
    private static void SolvePowers() {
        for (int i = 0; i < KenKen_Board.size; i++) {
            for (int j = 0; j < KenKen_Board.size; j++) {
                if (KenKen_Board.cages[i][j].operation.equals("^"))
                    KenKen_Board.set(new Place(i, j), (int)Math.round(Math.pow(Math.E, Math.log(KenKen_Board.cages[i][j].result) / 3)));
            }
        }
//        int i, j;
//        
//        if (KenKen_Board.isCompletePowers()) {
//            solution = (ArrayList<ArrayList<Integer>>)KenKen_Board.getBoard().clone();
//            return;
//        }
//        i = j = 0;
//        for (int a = 0; a < KenKen_Board.getSize(); a++) {
//            for (int b = 0; b < KenKen_Board.getSize(); b++) {
//                // Si es potencia y esta vacio
//                if (KenKen_Board.cages[a][b].operation.equals("^") && KenKen_Board.get(a, b) == 100) {
//                    i = a;
//                    j = b;
//                    break;
//                }
//            }
//        }
//        int result = KenKen_Board.cages[i][j].result;
//        int n = Power(result);
//        KenKen_Board.set(new Place(i, j), n);
//        SolvePowers();
    }
    private static void SolveOperations() {
        
        if (KenKen_Board.isComplete()) {
            solution = (ArrayList<ArrayList<Integer>>)KenKen_Board.getBoard().clone();
            return;
        }
        for (int i = 0; i < KenKen_Board.groupsArray.size();i++)
        {
            int groupID = KenKen_Board.groupsArray.get(i);
            ArrayList<ArrayList<Integer>> possibilities = possibilitiesMap.get(groupID);
            if(!KenKen_Board.groupIsComplete(groupID) && !possibilities.isEmpty())
            {
                ArrayList<Place> people = KenKen_Board.getPeople(groupID);
                for (ArrayList<Integer> possibility : possibilities) 
                {
                    if (people.size() == 2)
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
                    if (people.size() == 3)
                    {
                        KenKen_Board.set100(groupID);
                        Place place1 = people.get(0);
                        Place place2 = people.get(1);
                        Place place3 = people.get(2);
                        KenKen_Board.set(place1, possibility.get(0));
                        KenKen_Board.set(place2, possibility.get(1));
                        KenKen_Board.set(place3, possibility.get(2));  
                        if(KenKen_Board.isPossible2(people))
                        {
                            SolveOperations();
                        }
                        else
                        {
                            KenKen_Board.set100(groupID);
                        }
                    }
                    if (people.size() == 4)
                    {
                        KenKen_Board.set100(groupID);
                        Place place1 = people.get(0);
                        Place place2 = people.get(1);
                        Place place3 = people.get(2);
                        Place place4 = people.get(3);
                        KenKen_Board.set(place1, possibility.get(0));
                        KenKen_Board.set(place2, possibility.get(1));
                        KenKen_Board.set(place3, possibility.get(2));  
                        KenKen_Board.set(place4, possibility.get(3)); 
                        if(KenKen_Board.isPossible2(people))
                        {
                            SolveOperations();
                        }
                        else
                        {
                            KenKen_Board.set100(groupID);
                        }
                    }
                }
                KenKen_Board.set100(groupID);
            }
        }
    }
        
        
        
    
    private static ArrayList<ArrayList<Integer>> modulsPossibilities(int number) {
        
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int x = 1; x < KenKen_Board.size; x++)
        {
            for (int y = 1; y < KenKen_Board.size; y++)
            {
                ArrayList<Integer> possibility = new ArrayList<>();
                    if (x % y == number && x != y)
                    {
                        possibility.add(x);
                        possibility.add(y);
                        solutions.add(possibility);
                    }    
            }
        }
        return solutions;  
    }
    
    private static ArrayList<ArrayList<Integer>> substractionPossibilities(int number) {
        
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int x = 0;x<KenKen_Board.size;x++)
        {
            for (int y = 0; y < KenKen_Board.size; y++)
            {
                ArrayList<Integer> possibility = new ArrayList<>();
                    if (x - y == number && x != y)
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
        for (int x = 0; x < KenKen_Board.size; x++)
        {
            for (int y = 1; y < KenKen_Board.size; y++)
            {
                if (x / y == number && x != y && x % y == 0)
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
    
    private static ArrayList<ArrayList<Integer>> multiplicationPossibilities3(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int a = 0;a<KenKen_Board.size;a++)
        {
            for (int b = 0;b<KenKen_Board.size;b++)
            {
                for (int c = 0; c < KenKen_Board.size;c++)
                {
                    if (a*b*c == number)
                    {
                        ArrayList<Integer> possibility = new ArrayList<>();
                        possibility.add(a);
                        possibility.add(b);
                        possibility.add(c);
                        solutions.add(possibility);
                    }
                }
            }
        }
        return solutions;  
    }
    
    private static ArrayList<ArrayList<Integer>> multiplicationPossibilities4(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int a = 0; a < KenKen_Board.size; a++)
        {
            for (int b = 0; b < KenKen_Board.size; b++)
            {
                for (int c = 0; c < KenKen_Board.size; c++)
                {
                    for (int d = 0; d < KenKen_Board.size; d++)
                    {
                        if (a*b*c*d == number)
                        {
                            ArrayList<Integer> possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(d);
                            solutions.add(possibility);
                        }
                    }
                }
            }
        }
        return solutions;  
    }
    
   private static ArrayList<ArrayList<Integer>> addPossibilities3(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int a = 0;a<KenKen_Board.size;a++)
        {
            for (int b = 0;b<KenKen_Board.size;b++)
            {
                for (int c = 0; c < KenKen_Board.size;c++)
                {
                    if (a+b+c == number)
                    {
                        ArrayList<Integer> possibility = new ArrayList<>();
                        possibility.add(a);
                        possibility.add(b);
                        possibility.add(c);
                        solutions.add(possibility);
                    }
                }
            }
        }
        return solutions;  
    }
    
    private static ArrayList<ArrayList<Integer>> addPossibilities4(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        for (int a = 0; a < KenKen_Board.size; a++)
        {
            for (int b = 0; b < KenKen_Board.size; b++)
            {
                for (int c = 0; c < KenKen_Board.size; c++)
                {
                    for (int d = 0; d < KenKen_Board.size; d++)
                    {
                        if (a+b+c+d == number)
                        {
                            ArrayList<Integer> possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(d);
                            solutions.add(possibility);
                        }
                    }
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
