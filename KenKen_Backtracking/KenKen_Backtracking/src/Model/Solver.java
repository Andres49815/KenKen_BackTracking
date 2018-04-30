package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Solver implements Runnable {

    private static ArrayList<ArrayList<Integer>> solution;
    public static boolean solutionFound;
    public static int cantThreads = 0;
    public static Queue<Integer> cola = new LinkedList();
    public static long cantRecursion = 0;
    public static long cantPossibilities = 0;

    public static void DoPossibilitiesQueue() {
        KenKen_Board.groupsArray.forEach((n) -> {
            cola.add(n);
        });
        while (!cola.isEmpty()) {
            if (cantThreads < KenKen_Board.threadCount) {
                new Thread(new Solver()).start();
                cantThreads++;
            }
        }
    }

    @Override
    public void run() {
        if (!(cola.peek() == null)) {
            DoPossibilitiesAux(cola.poll());
            cantThreads--;
        }
    }

    public static void DoPossibilities() {
        for (int i = 0; i < KenKen_Board.groupsArray.size(); i++) {
            DoPossibilitiesAux(KenKen_Board.groupsArray.get(i));
        }
    }

    public static void DoPossibilitiesAux(int groupID) {
        Cage cage = KenKen_Board.getCage(groupID);
        switch (cage.operation) {
            case "%":
                cage.setSolutions(modulsPossibilities(cage.result));
                break;
            case "-":
                cage.setSolutions(substractionPossibilities(cage.result));
                break;
            case "/":
                cage.setSolutions(cage.solutions =  divisionPossibilities(cage.result));
                break;
            case "*":
                if (cage.quantity == 3) {
                    cage.setSolutions(multiplicationPossibilities3(cage.result));
                    break;
                } else {
                    cage.setSolutions(  multiplicationPossibilities4(cage.result));
                    break;
                }
            case "+":
                if (cage.quantity == 3) {
                    cage.setSolutions(  addPossibilities3(cage.result));
                    break;
                } else {
                    cage.setSolutions( addPossibilities4(cage.result));
                    break;
                }
            default:
                cage.setSolutions( new ArrayList<>());
                break;
        }
    }

    public static long Solve() {
        long startTime = System.currentTimeMillis();
        SortGroup();
        SolvePowers();
        SolveOperations();
        long finishTime = System.currentTimeMillis();
        return finishTime - startTime;
    }

    // Powers
    //Solve Powers O(size^2)
    private static void SolvePowers() {
        int number, root;
        for (int i = 0; i < KenKen_Board.size; i++) {                                   //size
            for (int j = 0; j < KenKen_Board.size; j++) {                               //size^2
                if (KenKen_Board.cages[i][j].operation.equals("^")) {   //BAROMETER     //size^2
                    number = KenKen_Board.cages[i][j].result;                           //c
                    root = (int) Math.round(Math.pow(Math.E, Math.log(number) / 3));    //c
                    KenKen_Board.set(i, j, root);                                       //c
                }
            }
        }
    }

    // Cosas de Ruben...
    private static void SolveOperations() {

        if (KenKen_Board.isComplete()) {
            solution = (ArrayList<ArrayList<Integer>>) KenKen_Board.board.clone();
            solutionFound = true;
        } else {
            for (int i = 0; i < KenKen_Board.groupsArray.size(); i++) {
                int groupID = KenKen_Board.groupsArray.get(i);
                if (!KenKen_Board.groupIsComplete(groupID)) {
                    Cage cage = KenKen_Board.getCage(groupID);
                    cage.cantSolutionsTested = 0;
                    ArrayList<ArrayList<Integer>> possibilities = cage.solutions;
                    for (ArrayList<Integer> possibility : possibilities) {
                        KenKen_Board.set100(groupID);
                        cantPossibilities++;
                        cage.cantSolutionsTested++;
                        if (cage.IsPossible(possibility)) {
                            cantRecursion++;
                            KenKen_Board.printCage();
                            KenKen_Board.SetPossibility(possibility, cage.coordinates);
                            SolveOperations();
                            if (KenKen_Board.isComplete()) {
                                return;
                            }
                        }
                    }
                    KenKen_Board.set100(groupID);
                    return;
                }
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> modulsPossibilities(int number) {

        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        ArrayList<Integer> possibility = new ArrayList<>();
        for (int x = 0; x < KenKen_Board.size; x++) {
            for (int y = 1; y < KenKen_Board.size; y++) {
                if (x % y == number) {
                    possibility = new ArrayList<>();
                    possibility.add(x);
                    possibility.add(y);
                    if (!solutions.contains(possibility)) {
                        solutions.add(possibility);
                    }
                    possibility = new ArrayList<>();
                    possibility.add(y);
                    possibility.add(x);
                    if (!solutions.contains(possibility)) {
                        solutions.add(possibility);
                    }
                }
            }
        }
        return solutions;
    }

    private static ArrayList<ArrayList<Integer>> substractionPossibilities(int number) {

        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        ArrayList<Integer> possibility = new ArrayList<>();
        for (int x = 0; x < KenKen_Board.size; x++) {
            for (int y = 0; y < KenKen_Board.size; y++) {
                if (x - y == number && x > y) {
                    possibility = new ArrayList<>();
                    possibility.add(x);
                    possibility.add(y);
                    if (!solutions.contains(possibility)) {
                        solutions.add(possibility);
                    }
                    possibility = new ArrayList<>();
                    possibility.add(y);
                    possibility.add(x);
                    if (!solutions.contains(possibility)) {
                        solutions.add(possibility);
                    }
                }
            }
        }
        return solutions;
    }

    private static ArrayList<ArrayList<Integer>> divisionPossibilities(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        ArrayList<Integer> possibility = new ArrayList<>();
        for (int x = 0; x < KenKen_Board.size; x++) {
            for (int y = 1; y < KenKen_Board.size; y++) {
                if (x / y == number && x % y == 0) {
                    possibility = new ArrayList<>();
                    possibility.add(x);
                    possibility.add(y);
                    if (!solutions.contains(possibility)) {
                        solutions.add(possibility);
                    }
                    possibility = new ArrayList<>();
                    possibility.add(y);
                    possibility.add(x);
                    if (!solutions.contains(possibility)) {
                        solutions.add(possibility);
                    }
                }
            }
        }
        return solutions;
    }

    private static ArrayList<ArrayList<Integer>> multiplicationPossibilities3(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        ArrayList<Integer> possibility = new ArrayList<>();
        for (int a = 0; a < KenKen_Board.size; a++) {
            for (int b = 0; b < KenKen_Board.size; b++) {
                for (int c = 0; c < KenKen_Board.size; c++) {
                    if (a * b * c == number) {
                        possibility = new ArrayList<>();
                        possibility.add(a);
                        possibility.add(b);
                        possibility.add(c);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(a);
                        possibility.add(c);
                        possibility.add(b);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(b);
                        possibility.add(a);
                        possibility.add(c);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(b);
                        possibility.add(c);
                        possibility.add(a);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(c);
                        possibility.add(a);
                        possibility.add(b);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(c);
                        possibility.add(b);
                        possibility.add(a);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                    }
                }
            }
        }
        return solutions;
    }

    private static ArrayList<ArrayList<Integer>> multiplicationPossibilities4(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        ArrayList<Integer> possibility = new ArrayList<>();
        for (int a = 0; a < KenKen_Board.size; a++) {
            for (int b = 0; b < KenKen_Board.size; b++) {
                for (int c = 0; c < KenKen_Board.size; c++) {
                    for (int d = 0; d < KenKen_Board.size; d++) {
                        if (a * b * c * d == number) {
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(d);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(c);
                            possibility.add(b);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(c);
                            possibility.add(d);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(d);
                            possibility.add(b);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(d);
                            possibility.add(c);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            //
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(a);
                            possibility.add(c);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(a);
                            possibility.add(d);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(a);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(d);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(d);
                            possibility.add(a);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(d);
                            possibility.add(c);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            //
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(a);
                            possibility.add(d);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(b);
                            possibility.add(a);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(b);
                            possibility.add(d);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(d);
                            possibility.add(a);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(d);
                            possibility.add(b);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            //
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(a);
                            possibility.add(c);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(b);
                            possibility.add(a);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(c);
                            possibility.add(a);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(c);
                            possibility.add(b);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                        }
                    }
                }
            }
        }
        return solutions;
    }

    private static ArrayList<ArrayList<Integer>> addPossibilities3(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        ArrayList<Integer> possibility = new ArrayList<>();
        for (int a = 0; a < KenKen_Board.size; a++) {
            for (int b = 0; b < KenKen_Board.size; b++) {
                for (int c = 0; c < KenKen_Board.size; c++) {
                    if (a + b + c == number) {
                        possibility = new ArrayList<>();
                        possibility.add(a);
                        possibility.add(b);
                        possibility.add(c);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(a);
                        possibility.add(c);
                        possibility.add(b);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(b);
                        possibility.add(a);
                        possibility.add(c);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(b);
                        possibility.add(c);
                        possibility.add(a);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(c);
                        possibility.add(a);
                        possibility.add(b);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                        possibility = new ArrayList<>();
                        possibility.add(c);
                        possibility.add(b);
                        possibility.add(a);
                        if (!solutions.contains(possibility)) {
                            solutions.add(possibility);
                        }
                    }
                }
            }
        }
        return solutions;
    }

    private static ArrayList<ArrayList<Integer>> addPossibilities4(int number) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
        ArrayList<Integer> possibility = new ArrayList<>();
        for (int a = 0; a < KenKen_Board.size; a++) {
            for (int b = 0; b < KenKen_Board.size; b++) {
                for (int c = 0; c < KenKen_Board.size; c++) {
                    for (int d = 0; d < KenKen_Board.size; d++) {
                        if (a + b + c + d == number) {
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(d);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(c);
                            possibility.add(b);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(c);
                            possibility.add(d);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(d);
                            possibility.add(b);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(a);
                            possibility.add(d);
                            possibility.add(c);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            //
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(a);
                            possibility.add(c);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(a);
                            possibility.add(d);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(a);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(d);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(d);
                            possibility.add(a);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(b);
                            possibility.add(d);
                            possibility.add(c);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            //
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(a);
                            possibility.add(d);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(b);
                            possibility.add(a);
                            possibility.add(d);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(b);
                            possibility.add(d);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(d);
                            possibility.add(a);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(c);
                            possibility.add(d);
                            possibility.add(b);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            //
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(a);
                            possibility.add(b);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(a);
                            possibility.add(c);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(b);
                            possibility.add(a);
                            possibility.add(c);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(b);
                            possibility.add(c);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(c);
                            possibility.add(a);
                            possibility.add(b);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                            possibility = new ArrayList<>();
                            possibility.add(d);
                            possibility.add(c);
                            possibility.add(b);
                            possibility.add(a);
                            if (!solutions.contains(possibility)) {
                                solutions.add(possibility);
                            }
                        }
                    }
                }
            }
        }
        return solutions;
    }


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

    static void SortGroup() {

        ArrayList<Cage> cages = new ArrayList<>();
        KenKen_Board.groupsArray.forEach((n) -> {
            cages.add(KenKen_Board.getCage(n));
        });

        Collections.sort(cages, new Comparator<Cage>() {
            @Override
            public int compare(Cage o, Cage u) {
                if (o.solutions.size() < u.solutions.size()) {
                    return -1;
                }
                if (o.solutions.size() > u.solutions.size()) {
                    return 1;
                }
                return 0;
            }
        });
        ArrayList<Integer> groups = new ArrayList<>();
        cages.forEach((n) -> {
            groups.add(n.id);
        });
        KenKen_Board.groupsArray = groups;
    }
    
//    public static ArrayList<ArrayList<Integer>> Permutations(ArrayList<Integer> elements) {
//        return Permutations(elements, new ArrayList<Integer>(), elements.size(),
//                elements.size(), new ArrayList<ArrayList<Integer>>());
//    }
//
//    private static ArrayList<ArrayList<Integer>> Permutations(ArrayList<Integer> elements,
//            ArrayList<Integer> act, int n, int r, ArrayList<ArrayList<Integer>> result) {
//        if (n == 0) {
//            result.add(act);
//        } else {
//            for (int i = 0; i < r; i++) {
//                if (!act.contains(elements.get(i))) {
//                    ArrayList<Integer> newAct = (ArrayList<Integer>) act.clone();
//                    newAct.add(elements.get(i));
//                    Permutations(elements, newAct, n - 1, r, result);
//                }
//            }
//        }
//        return result;
//    }

}
